package com.lt.lxc.game.teamplate.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lt.lxc.controller.vo.OpenResultVo;
import com.lt.lxc.game.GameEnum;
import com.lt.lxc.game.teamplate.IOrderService;
import com.lt.lxc.pojo.OrderDTO;

/**
 * 非组合总和大小单双
 * @author Administrator
 *
 */
@Service("ZongHeDaXiaoTemplate")
public class OrderBySummationServiceImpl extends IOrderService{

	@Override
	public OpenResultVo countLotteryResult(OrderDTO order, List<Integer> lotteryCode) {
		//计算出该组号码所对应玩法的结果
		List<String> lotteryResult = new ArrayList<>();
		int count = sumLotteryResult(lotteryCode);
		//首先判断是否为和值,如果是的直接返回不用做后续判断
		if(isAnd(count)) {
			lotteryResult.add(GameEnum.GameContent.AND.getMessage());
		}else {
			//之后做大小单双的判断
			lotteryResult.add(bigAndSmall(count,MUTIL_MEDIANT));
			lotteryResult.add(singleAndDouble(count));
		}
		return tovo(order, lotteryCode, lotteryResult);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		OrderBySummationServiceImpl s=new OrderBySummationServiceImpl();
		OrderDTO order=new OrderDTO();

		order.setBetsContent("大100");//
		order.setOdds(2.0);


		List<Integer> lotteryCode= new ArrayList<Integer>();
		lotteryCode.add(3);
		lotteryCode.add(4);
		lotteryCode.add(3);
		lotteryCode.add(4);
		lotteryCode.add(9);
		lotteryCode.add(9);
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
