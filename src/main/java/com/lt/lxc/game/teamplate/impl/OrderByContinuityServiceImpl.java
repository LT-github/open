package com.lt.lxc.game.teamplate.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lt.lxc.controller.vo.OpenResultVo;
import com.lt.lxc.game.ChineseNumToArabicNumUtil;
import com.lt.lxc.game.CombineAndArrangement;
import com.lt.lxc.game.GameProperty;
import com.lt.lxc.game.GameUtil;
import com.lt.lxc.game.teamplate.IOrderService;
import com.lt.lxc.pojo.OrderDTO;
import com.lt.lxc.utils.Arith;

/**
 * 连码丶复式
 * @author Administrator
 *
 */
@Service("LianMaTemplate")
public class OrderByContinuityServiceImpl extends IOrderService{

	@Override
	public OpenResultVo countLotteryResult(OrderDTO order, List<Integer> lotteryResult) {
		OpenResultVo vo = new OpenResultVo();
		vo.setLotteryCode(lotteryResult);

		//得出单注
		GameProperty gp = GameUtil.getTotalMoney(order.getBetsContent());
		//区分复式玩法和连码玩法
		if(order.getBetsContent().contains("复")) 
			multiple(gp,vo,order, lotteryResult);
		else
			continuity(gp,vo,order, lotteryResult);
		return vo;
	}

	/**
	 * 复式
	 * @param order
	 * @param lotteryResult
	 * @return
	 */
	private OpenResultVo multiple(GameProperty gp,OpenResultVo vo,OrderDTO order, List<Integer> lotteryResult) {

		//截取复中间的汉字数字
		String s = order.getBetsContent().substring(order.getBetsContent().indexOf("复")+1, order.getBetsContent().indexOf("复")+2);
		//截取复前面的数字字符串
		String number = StringUtils.substringBefore(order.getBetsContent(), "复");
		//截取复后面的金额
		double money= GameUtil.check(StringUtils.substringAfterLast(order.getBetsContent(), "复"));
		List<String> bets = getBetList(s, number);
		//每注所选择的号码的集合
		List<Integer> betNum = new ArrayList<Integer>();

		for (String i : bets) {
			//添加用户选择的号码
			//vo.getBetsCode().add(Integer.parseInt(i));
			betNum.clear();
			Pattern pattern = Pattern.compile("\\D");
			Matcher matcher = pattern.matcher(i);
			i = matcher.replaceAll("");
			for (int j = 0; j < i.length(); j++) {
				betNum.add(Integer.parseInt(i.substring(j,j+1)));
			}
			if(lotteryResult.containsAll(betNum)) {
				//存在,则说明该号码中奖
				vo.getBetResultMap().put(i+"/"+gp.getSingle(),Arith.mul(money, order.getOdds()));
			}else {
				//否则不中奖
				vo.getBetResultMap().put(i+"/"+gp.getSingle(), money*(-1.0));
			}
		}
		isWin(gp,vo);
		return vo;
	}

	

	/**
	 * 连码
	 * @param order
	 * @param lotteryResult
	 * @return
	 */
	private OpenResultVo continuity(GameProperty gp,OpenResultVo vo,OrderDTO order, List<Integer> lotteryResult) {
		//解析玩法内容
		String[] split = order.getBetsContent().split("/");
		//用于判断的集合
		List<Integer> betNum = new ArrayList<Integer>();
		for (int j = 0; j < split[0].length(); j++) {
			betNum.add(Integer.parseInt(split[0].substring(j,j+1)));
			//用户下注的号码
			vo.getBetsCode().add(Integer.parseInt(split[0].substring(j,j+1)));
		}
		if(lotteryResult.containsAll(betNum)) {
			//存在,则说明该号码中奖
			vo.getBetResultMap().put(split[0],Arith.mul(Double.parseDouble(split[1]),order.getOdds()));
		}else {
			//否则不中奖
			vo.getBetResultMap().put(split[0], Double.parseDouble(split[1])*(-1));
		}
		isWin(gp,vo);
		return vo;
	}
	
	/**
	 * 数字字符不重复排列组合,返回下注的集合
	 * @param s
	 * @param number
	 * @return
	 */
	private List<String> getBetList (String s ,String number){
		int k = ChineseNumToArabicNumUtil.chineseNumToArabicNum(s);
		int[] arr = new int[number.length()];
		for (int i = 0; i < number.length(); i++) {
			arr[i] = Integer.parseInt(number.substring(i, i + 1));
		}
		int index = 0;
		ArrayList<Integer> tmpArr  = new ArrayList<Integer>();
		List<String> result = new ArrayList<String>();
		List<String> list = CombineAndArrangement.combine(index,k,arr,tmpArr,result);
		return list;
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		OrderByContinuityServiceImpl s=new OrderByContinuityServiceImpl();
		OrderDTO order=new OrderDTO();
		order.setBetsContent("178/2");
		order.setOdds(2.0);
		List<Integer> lotteryResult= new ArrayList<Integer>();
		lotteryResult.add(1);
		lotteryResult.add(3);
		lotteryResult.add(4);
		lotteryResult.add(7);
		lotteryResult.add(8);
		lotteryResult.add(2);
		OpenResultVo vo = s.countLotteryResult(order, lotteryResult);
		System.out.println("开奖号码: "+lotteryResult);
		System.out.println("该注单所选择的号码:"+vo.getBetsCode().toString());
		System.out.println("该注单的总输赢: "+vo.isResult());
		System.out.println("该注单输赢的总金额: "+vo.getMoney());
		System.out.println("该注单可以兑换的金额:"+vo.getWonMoney());
		Map<String,Double> map=vo.getBetResultMap();
		//遍历map
		System.out.println("=========每注的输赢结果=========");
		for(Map.Entry<String,Double> entry : map.entrySet()){  
		    System.out.println("下注的内容: "+entry.getKey()+",输赢的金额: "+entry.getValue());  
		}  
	}
}
