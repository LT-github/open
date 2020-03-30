package com.lt.lxc.game.teamplate.impl;

import java.util.ArrayList;
import java.util.Collections;
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
 * 单码
 * @author Administrator
 *
 */
@Service("DanMaTemplate")
public class OrderBySingleCodeImpl extends IOrderService{

	@Override
	public OpenResultVo countLotteryResult(OrderDTO order, List<Integer> lotteryCode) {
		//单码有特殊的赔率,1中1的赔率为第一个元素,1中2的赔率为第二个元素,1中3的赔率为第三个元素,
		//1中4的赔率为第4个元素,1中5的赔率为第5个元素,1中6的赔率为第6个元素
		List<Double> odds = new ArrayList<>();
		for (String odd : order.getSpecificOdds().split(",")) {
			odds.add(Double.parseDouble(odd));
		}
		OpenResultVo vo = new OpenResultVo();
		//得出单注
		GameProperty gp = GameUtil.getTotalMoney(order.getBetsContent());
		List<Integer> result = new ArrayList<Integer>();
		//每注的金额
		double betMoney = 0 ;
		//将注单拆分成注,并遍历每注
		String[] stArr = order.getBetsContent().split(",");
		for (int i = 0; i < stArr.length; i++) {
			//每注所选择的号码
			vo.getBetsCode().add(Integer.parseInt(stArr[i].split("/")[0]));
			Integer betNum = Integer.parseInt(stArr[i].split("/")[0]);
			//开奖号码中出现用户所选择号码的次数
			int count = Collections.frequency(lotteryCode,betNum);
			betMoney = Double.parseDouble(stArr[i].split("/")[1]);
			if(0 == count) {
				//0次,则没中
				vo.getBetResultMap().put(stArr[i].split("/")[0],betMoney*(-1));
			}else {
				//中了
				vo.getBetResultMap().put(stArr[i].split("/")[0]+"*"+odds.get(count-1)+"倍",Arith.mul(betMoney,odds.get(count-1)));
			}
		}
		//该注单的输赢的金额.为正则赢,负则输
		isWin(gp,vo);
		return vo;
	}

	
	
	public static void main(String[] args) {
		OrderDTO dto = new OrderDTO();
		dto.setSpecificOdds("2,4,18,20,48,85");
		dto.setBetsContent("1/15");
		List<Integer> lotteryResult = new ArrayList<Integer>();
		lotteryResult.add(1);
		lotteryResult.add(1);
		lotteryResult.add(1);
		lotteryResult.add(1);
		lotteryResult.add(1);
		lotteryResult.add(1);
		OrderBySingleCodeImpl impl = new OrderBySingleCodeImpl();
		OpenResultVo vo = impl.countLotteryResult(dto,lotteryResult);
		System.out.println("开奖号码: "+lotteryResult);
		System.out.println("该注单的总输赢: "+vo.isResult());
		System.out.println("该注单输赢的总金额: "+vo.getMoney());
		System.out.println("该注单可以兑换的金额:"+vo.getWonMoney());
		Map<String,Double> map=vo.getBetResultMap();
		//遍历map
		for(Map.Entry<String,Double> entry : map.entrySet()){  
		    System.out.println("下注的号码 = "+entry.getKey()+",输赢的金额="+entry.getValue());  
		}  
		
	}
}
