package com.lt.lxc.game.teamplate.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lt.lxc.controller.vo.OpenResultVo;
import com.lt.lxc.game.teamplate.IOrderService;
import com.lt.lxc.pojo.OrderDTO;


/**
 * 三区形态
 * @author Administrator
 *
 */
@Service("SanQuTemplate")
public class OrderByThreeZoneFormServiceImpl extends IOrderService{


	@Override
	public OpenResultVo countLotteryResult(OrderDTO order, List<Integer> lotteryCode) {
		List<String> lotteryResult = threeFormLotteryJudge(lotteryCode);
		return tovo(order, lotteryCode, lotteryResult);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {

		OrderByThreeZoneFormServiceImpl s=new OrderByThreeZoneFormServiceImpl();
		OrderDTO order=new OrderDTO();

		order.setBetsContent("前对100");//
		order.setOdds(2.0);


		List<Integer> lotteryCode= new ArrayList<Integer>();
		lotteryCode.add(5);
		lotteryCode.add(2);
		lotteryCode.add(2);
		lotteryCode.add(3);
		lotteryCode.add(9);
		lotteryCode.add(1);
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
