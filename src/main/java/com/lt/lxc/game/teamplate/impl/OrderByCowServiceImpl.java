package com.lt.lxc.game.teamplate.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lt.lxc.utils.Arith;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lt.lxc.controller.vo.OpenResultVo;
import com.lt.lxc.game.ChineseNumToArabicNumUtil;
import com.lt.lxc.game.GameProperty;
import com.lt.lxc.game.GameUtil;
import com.lt.lxc.game.teamplate.IOrderService;
import com.lt.lxc.pojo.OrderDTO;

/**
 * 牛牛丶包含二十门牛牛
 * @author Administrator
 *
 */
@Service("NiuNiuTemplate")
public class OrderByCowServiceImpl extends IOrderService{

	//所有的位置
	public final static  Integer[] allLocal = new Integer[] {1,2,3,4,5,6};
	//翻倍的最低倍率
	public final static int FAN_LOWEST_ODD = 1;
	//翻倍的基础倍率
	public final static int FAN_BASE_ODD = 2;
	//翻倍的最高倍率
	public final static int FAN_HIGHEST_ODD = 3;
	//前后单双对应的位置
	public final static Map<String,Integer[]> localMap = new HashMap<>();
	static {
		localMap.put("前", new Integer[] {1,2,3});
		localMap.put("后", new Integer[] {4,5,6});
		localMap.put("单", new Integer[] {1,3,5});
		localMap.put("双", new Integer[] {2,4,6});
		localMap.put("124", new Integer[] {1,2,4});
		localMap.put("125", new Integer[] {1,2,5});
		localMap.put("126", new Integer[] {1,2,6});
		localMap.put("134", new Integer[] {1,3,4});
		localMap.put("136", new Integer[] {1,3,6});
		localMap.put("145", new Integer[] {1,4,5});
		localMap.put("146", new Integer[] {1,4,6});
		localMap.put("156", new Integer[] {1,5,6});
		localMap.put("234", new Integer[] {2,3,4});
		localMap.put("235", new Integer[] {2,3,5});
		localMap.put("236", new Integer[] {2,3,6});
		localMap.put("245", new Integer[] {2,4,5});
		localMap.put("256", new Integer[] {2,5,6});
		localMap.put("345", new Integer[] {3,4,5});
		localMap.put("346", new Integer[] {3,4,6});
		localMap.put("356", new Integer[] {3,5,6});
	}

	@Override
	public OpenResultVo countLotteryResult(OrderDTO order, List<Integer> lotteryCode) {
		OpenResultVo vo = new OpenResultVo();
		vo.setLotteryCode(lotteryCode);
		for(Map.Entry<String,Integer[]> entry : localMap.entrySet()){  
			String form = getNiuForm(lotteryCode,entry.getValue())+"";
			if(form.equals("10")) {
				form = "牛";
				vo.getLotteryResult().add(entry.getKey()+":牛"+form);
			}else {
				vo.getLotteryResult().add(entry.getKey()+":牛"+ChineseNumToArabicNumUtil.arabicNumToChineseNum(Integer.parseInt(form)));
			}
		}
		if(order == null) {
			return vo;
		}

		//截取到"牛"前面的字符串
		String local = StringUtils.substringBefore(order.getBetsContent(),"牛");
		//闲家的位置
		Integer[] playerLocal = new  Integer[3];
		//下注内容中是否有前,后,单,双
		playerLocal = localMap.get(local.substring(0,local.length()-1));
		for (Integer integer : playerLocal) {
			//补全Vo的选择位置
			vo.getBetsLocation().add(integer);
		}
		//庄家的位置
		Integer[] bankerLocal = getBankerLocal(playerLocal);
		//庄家的牛
		int bankerNiuForm = getNiuForm(lotteryCode,bankerLocal);
		//闲家的牛
		int playerNiuForm = getNiuForm(lotteryCode,playerLocal);
		//通过解析下注内容,得出该注单的单注金额
		GameProperty gp = GameUtil.getTotalMoney(order.getBetsContent());
		//
		double wonMoney =0;
		//庄家的牛与闲家的牛的比较
		if(playerNiuForm > bankerNiuForm){
			//			//若闲家大于庄家,则闲家赢
			wonMoney = Arith.add(Arith.mul(gp.getSingle(),getOdd(order.getBetsContent(), playerNiuForm)),gp.getMoney());
			//			vo.getBetResultMap().put(Arrays.toString(playerLocal), wonMoney);
		}else {
			wonMoney = gp.getSingle()*getOdd(order.getBetsContent(), bankerNiuForm)*(-1);
		}
		vo.getBetResultMap().put(order.getBetsContent(), wonMoney);
		//补全vo其它信息
		//该注单的赢的金额
		double wonMoeny =0;
		for (Map.Entry<String, Double> entry : vo.getBetResultMap().entrySet()){
			wonMoeny += entry.getValue();
		}
		if(wonMoeny>gp.getMoney()) {
			vo.setResult(true);
			vo.setMoney(wonMoeny-gp.getMoney());
			vo.setWonMoney(wonMoeny);
		}else {
			vo.setResult(false);
			vo.setMoney(wonMoeny);
			vo.setWonMoney(gp.getMoney()+wonMoeny);
		}
		return vo;
	}
	/**
	 * 得到庄家的位置
	 * @param localNum
	 * @return
	 */
	public Integer[] getBankerLocal(Integer[] localNum) {
		//庄家的位置
		Integer[] bankerLocal = new Integer[3];
		int count = 0;
		for (int i = 0; i <  allLocal.length; i++) {
			//用ArrayUtils的contains方法判断localnum中是否存在allLocal中的每个数,
			//若不存在添加到庄家的位置中
			if(!ArrayUtils.contains(localNum, allLocal[i])) {
				bankerLocal[count] = allLocal[i];
				count++;
				if(count>bankerLocal.length) {
					break;
				}
			}
		}
		return bankerLocal;
	}


	/**
	 * 通过位置得到数,并得出为牛几
	 * @param numbers
	 * @return 牛几就为几,牛牛为0
	 */
	public Integer getNiuForm(List<Integer> lotteryResult,Integer[] locals) {
		//对应位置的数的总和
		int count = 0;
		for ( Integer local: locals) {
			count += lotteryResult.get(local-1);
		}
		if(0 == count%10) {
			return 10;
		}
		return count%10;
	}

	/**
	 * 根据闲家所买的号码的出的牛,计算出超牛,翻牛,平牛的倍率
	 * @param betContent
	 * @param value
	 * @return
	 */
	public Integer getOdd(String betContent,int niuForm) {
		if(betContent.contains("超")) {
			return niuForm;
		}else if(betContent.contains("翻")) {
			if(niuForm == 10) {
				return FAN_HIGHEST_ODD;
			}else if(niuForm >6) {
				return FAN_BASE_ODD;
			}
			return FAN_LOWEST_ODD;
		}
		return FAN_LOWEST_ODD;
	}



	public static void main(String[] args) {

		OrderByCowServiceImpl s=new OrderByCowServiceImpl();
		OrderDTO order=new OrderDTO();

		order.setGame("牛牛");
		order.setBetsContent("后超牛100");//
		order.setOdds(1.0);


		List<Integer> lotteryResult= new ArrayList<Integer>();
		lotteryResult.add(0);
		lotteryResult.add(1);
		lotteryResult.add(1);
		lotteryResult.add(2);
		lotteryResult.add(2);
		lotteryResult.add(5);
		OpenResultVo vo = s.countLotteryResult(order, lotteryResult);
		System.out.println("开奖号码: "+lotteryResult);
		System.out.println("开奖结果: "+vo.getLotteryResult());
		System.out.println("该注单所选择的号码:"+vo.getBetsCode().toString());
		System.out.println("该注单所选择的位置:"+vo.getBetsLocation().toString());
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
