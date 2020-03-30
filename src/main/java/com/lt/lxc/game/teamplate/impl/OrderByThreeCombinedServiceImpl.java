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
 * 大小单双龙虎的三三组合
 * @author Administrator
 *
 */
@Service("ThreeCombined")
public class OrderByThreeCombinedServiceImpl extends IOrderService{

	@Override
	public OpenResultVo countLotteryResult(OrderDTO order, List<Integer> lotteryCode) {
		List<String> lotteryResult = new ArrayList<>();
		//号码的总和
		int count = sumLotteryResult(lotteryCode);
		if(isAnd(count)) {
			//首先判断总和是否为和值
			lotteryResult.add(GameEnum.GameContent.AND.getMessage()+dragonAndTigerResult(lotteryCode));
		}else {
			//大小和单双和龙虎的组合判断
			lotteryResult.add(bigAndSmall(count,MUTIL_MEDIANT)+singleAndDouble(count)+dragonAndTigerResult(lotteryCode));
		}
		return tovo(order, lotteryCode, lotteryResult);
	}


	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		OrderByThreeCombinedServiceImpl s=new OrderByThreeCombinedServiceImpl();
		OrderDTO order=new OrderDTO();

		order.setBetsContent("大双虎100");//
		order.setOdds(2.0);


		List<Integer> lotteryCode= new ArrayList<Integer>();
		lotteryCode.add(5);
		lotteryCode.add(4);
		lotteryCode.add(4);
		lotteryCode.add(4);
		lotteryCode.add(3);
		lotteryCode.add(5);
		OpenResultVo vo = s.countLotteryResult(null, lotteryCode);
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
