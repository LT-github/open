package com.lt.lxc.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.lt.lxc.controller.req.OpenDataFindReq;
import com.lt.lxc.controller.resp.PageResp;
import com.lt.lxc.controller.resp.PageResp.PageGenerator;
import com.lt.lxc.controller.vo.GameResultVo;
import com.lt.lxc.controller.vo.LastOpenDataVo;
import com.lt.lxc.controller.vo.OpenDataVo;
import com.lt.lxc.controller.vo.OpenResultVo;
import com.lt.lxc.dao.IOpenDataRepository;
import com.lt.lxc.entity.OpenData;
import com.lt.lxc.game.teamplate.IOrderService;
import com.lt.lxc.service.IOpenDataService;
import com.lt.lxc.utils.GlobalConstant.NextIssueNumber;
import com.lt.lxc.utils.IdWorker;
import com.lt.lxc.utils.IssueUtil;

@Service
public class OpenDataServiceImpl implements IOpenDataService {
	
	@Autowired
	private IOpenDataRepository openDataRepository;
	
	@Autowired
	private IdWorker idWorker;
	
	@Autowired
	private Map<String,IOrderService> orderServiceMap;
	
	
	/**
	 * 开奖结果存入数据库
	 */
	@Override
	public String add(List<Integer> lottery,Long openTime) {
		OpenData od = new OpenData();
		od.setId(idWorker.nextId());
		od.setOpenCode(StringUtils.join(lottery,","));
		od.setOpenTime(openTime);
		//获取数据中最后一次流水号
		String issue = NextIssueNumber.ISSUE_NUMBER.getData();
		
		//设置期号
		od.setIssueNumber(issue);
		od.setType("六星彩");
		openDataRepository.save(od);
		
		System.err.println("存入开奖数据成功!");
		NextIssueNumber.ISSUE_NUMBER.setData(IssueUtil.getThisIssueNumber());
		return NextIssueNumber.ISSUE_NUMBER.getData();
	}
	
	/**
	 * 查询最新一期开奖结果
	 */
	@Override
	public LastOpenDataVo findOne() {
		OpenData od = openDataRepository.findByMaxTime();
		LastOpenDataVo lodvo = new LastOpenDataVo(od);
		//将开奖号码有string转为list
		List<Integer> code = getCodeToList(od.getOpenCode());
		String result = getResultBykey(code, "ThreeCombined");
		String[] arr = result.split("");
		//String数组转List
		lodvo.setResult(Arrays.asList(arr));
		return lodvo;
	}

	/**
	 * 开奖记录
	 */
	@Override
	public PageResp<OpenDataVo, OpenData> findAll(OpenDataFindReq req) {
		Page<OpenData> page = openDataRepository.findAll(req);
		return new PageResp<OpenDataVo,OpenData>(page).getPageVo(new PageGenerator<OpenDataVo,OpenData>(){
			
			@Override
			public List<OpenDataVo> generator(List<OpenData> content) {
				List<OpenDataVo> odvos = new ArrayList<OpenDataVo>();
				for (OpenData od : content) {
					OpenDataVo odvo = new OpenDataVo(od);
					//将开奖号码有string转为list
					List<Integer> code = getCodeToList(od.getOpenCode());
					//龙虎和的结果
					odvo.setLongHuHe(getResultBykey(code,"LongHuHeTemplate"));
					odvo.setSanChong(getResultBykey(code, "SanChongTemplate"));
					odvo.setFrontForm(getThreeForm(code,"前"));
					odvo.setBehindForm(getThreeForm(code,"后"));
					odvo.setCount(getcount(code));
					odvo.setZongHeDaXiaoDanShuang(getResultBykey(code, "ZongHeDaXiaoTemplate"));
					odvo.setDingWeiDaXiao(getDingWeiDaXiao(code, 0));
					odvo.setDingWeiDanShuang(getDingWeiDaXiao(code, 1));
					odvo.setFrontCow(getCowResultMap(code).get("前"));
					odvo.setBehindCow(getCowResultMap(code).get("后"));
					odvo.setSingleCow(getCowResultMap(code).get("单"));
					odvo.setDoubleCow(getCowResultMap(code).get("双"));
					odvos.add(odvo);
				}
				return odvos;
			}
		});
	}

	/**
	 * 玩法对应的结果
	 */
	@Override
	public GameResultVo findGameResult(Long id) {
		Optional<OpenData> opt = openDataRepository.findById(id);
		if(!opt.isPresent())
			throw new RuntimeException("不存在"+id+"实体");
		OpenData od = opt.get();
		List<Integer> code = getCodeToList(od.getOpenCode());
		GameResultVo vo = new GameResultVo();
		vo.setIssueNumber(od.getIssueNumber());
		vo.setLongHuHe(getResultBykey(code,"LongHuHeTemplate"));
		vo.setSanChong(getResultBykey(code, "SanChongTemplate"));
		vo.setZongHeDaXiaoDanShuang(getResultBykey(code, "ZongHeDaXiaoTemplate"));
		vo.setDingWeiDaXiao(getDingWeiDaXiao(code, 0));
		vo.setDingWeiDanShuang(getDingWeiDaXiao(code, 1));
		vo.setFrontForm(getThreeForm(code,"前"));
		vo.setBehindForm(getThreeForm(code,"后"));
		vo.setCount(getcount(code));
		vo.setDingWeiCombined(getResultBykey(code, "DinWeiDaXiaoTemplate"));
		vo.setNiuNiu(getCowResultMap(code));
		vo.setTwoCombined(getResultBykey(code, "TwoCombined"));
		vo.setThreeCombined(getResultBykey(code, "ThreeCombined"));
		return vo;
	}

	/**
	 * 依赖查找,找到每个玩法的结果
	 * @param code
	 * @param key
	 * @return
	 */
	private String getResultBykey(List<Integer> code,String key) {
		IOrderService iOrderService = orderServiceMap.get(key);
		OpenResultVo orvo = iOrderService.countLotteryResult(null, code);
		return StringUtils.join(orvo.getLotteryResult(),",");
	}
	
	/**
	 * 定位大小单双
	 * @param code
	 * @param index
	 * @return
	 */
	private String getDingWeiDaXiao(List<Integer> code,int index) {
		IOrderService iOrderService = orderServiceMap.get("DinWeiDaXiaoTemplate");
		OpenResultVo orvo = iOrderService.countLotteryResult(null, code);
		List<String> list = orvo.getLotteryResult();
		String result = "";
		for (String str : list) {
			result += str.charAt(index);
			if(list.indexOf(str)!=list.size()-1) {
				result +=",";
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	private List<Integer> getCodeToList(String code){
		List<Integer> list  = new ArrayList<Integer>();
		String[] codeArr= code.split(",");
		for (String str : codeArr) {
			list.add(Integer.parseInt(str));
		}
		return list;
	}
	
	/**
	 * 三区形态
	 * @param code
	 * @param index
	 * @return
	 */
	private String getThreeForm(List<Integer> code,String local) {
		IOrderService iOrderService = orderServiceMap.get("SanQuTemplate");
		OpenResultVo orvo = iOrderService.countLotteryResult(null, code);
		List<String> list = orvo.getLotteryResult();
		List<String>  result = new ArrayList<String>();
		
		for (String str : list) {
			if(str.contains(local)) {
				result.add(str.substring(1,str.length()));
			}
		}
		return StringUtils.join(result,",");
	}

	private int getcount(List<Integer> list) {
		int count = 0;
		for (Integer item : list) {
			count += item;
		}
		return count;
	}
	
	private Map<String,String> getCowResultMap(List<Integer> code){
		IOrderService iOrderService = orderServiceMap.get("NiuNiuTemplate");
		OpenResultVo orvo = iOrderService.countLotteryResult(null, code);
		List<String> list = orvo.getLotteryResult();
		Map<String,String> map = new HashMap<>();
		for (String item: list) {
			String[] arr = item.split(":");
			map.put(arr[0], arr[1]);
		}
		return map;
	}
}
