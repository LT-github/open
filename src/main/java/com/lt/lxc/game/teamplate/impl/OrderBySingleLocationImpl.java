package com.lt.lxc.game.teamplate.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lt.lxc.controller.vo.OpenResultVo;
import com.lt.lxc.game.GameProperty;
import com.lt.lxc.game.GameUtil;
import com.lt.lxc.game.teamplate.IOrderService;
import com.lt.lxc.pojo.OrderDTO;
import com.lt.lxc.utils.Arith;

/**
 * 定位大小单双丶组合定位大小单双
 * @author Administrator
 *
 */
@Service("DinWeiDaXiaoTemplate")
public class OrderBySingleLocationImpl  extends IOrderService{

	@Override
	public OpenResultVo countLotteryResult(OrderDTO order, List<Integer> lotteryCode) {
		//计算出该组号码所对应玩法的结果
		List<String> lotteryResult = new ArrayList<>();
		OpenResultVo vo = new OpenResultVo();
		for (int code : lotteryCode) {
			lotteryResult.add(bigAndSmall(code,SINGLE_MEDIANT)+singleAndDouble(code));
		}
		vo.setLotteryResult(lotteryResult);
		if(null == order) {
			return vo;
		}
		//得出单注
		GameProperty gp = GameUtil.getTotalMoney(order.getBetsContent());
		//分解注单为多个注
		String[] bets = order.getBetsContent().split(",");
		
		//用户所选择的位置
		int location = 0;
		for (String bet : bets) {
			//解析位置
			location = Integer.parseInt(bet.substring(0,1));
			vo.getBetsLocation().add(location);
			//解析单个下注玩法
			bet = parseContent(bet);
			//根据位置取开奖结果中的字符串是否包含下注内容
			if(lotteryResult.get(location-1).contains(bet)) {
				//是,中奖
				vo.getBetResultMap().put(location+bet, Arith.mul(gp.getSingle(), order.getOdds()));
			}else {
				//不是,则不中
				vo.getBetResultMap().put(location+bet, gp.getSingle()*(-1));
			}
		}
		isWin(gp,vo);
		return vo;
	}
	
	
	
	
	
	
	
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		OrderBySingleLocationImpl s=new OrderBySingleLocationImpl();
		OrderDTO order=new OrderDTO();

		order.setBetsContent("1小双19,2小双19,3小双19,4小双19,5小双19,6小双19");//
		order.setOdds(1.6);


		List<Integer> lotteryCode= new ArrayList<Integer>();
		lotteryCode.add(8);
		lotteryCode.add(0);
		lotteryCode.add(9);
		lotteryCode.add(4);
		lotteryCode.add(7);
		lotteryCode.add(6);
		OpenResultVo vo = s.countLotteryResult(order, lotteryCode);
		System.out.println("开奖号码: "+lotteryCode);
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
