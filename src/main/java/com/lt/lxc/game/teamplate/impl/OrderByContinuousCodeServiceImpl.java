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
 * 定位数字"123/123/100"
 * @author Administrator
 *
 */
@Service("DinWeiShuZiTemplate")
public class OrderByContinuousCodeServiceImpl extends IOrderService{
	
	@Override
	public OpenResultVo countLotteryResult(OrderDTO order, List<Integer> lotteryCode) {
		OpenResultVo vo = new OpenResultVo();
		String[] stArr = order.getBetsContent().split("/");
		
		//得出单注
		GameProperty gp = GameUtil.getTotalMoney(order.getBetsContent());
		//遍历该注单的所选择位置
		for (int i = 0; i < stArr[0].length(); i++) {
			int location = Integer.parseInt(stArr[0].substring(i, i+1));
			vo.getBetsLocation().add(location);
			//遍历该注单所选择的号码
			for (int j = 0; j < stArr[1].length(); j++) {
				int code = Integer.parseInt(stArr[1].substring(j,j+1));
				vo.getBetsCode().add(code);
				//对应的位置上的号码是否一致
				if(lotteryCode.get(location-1) == code) {
					//是,则中奖
					vo.getBetResultMap().put(location+"/"+code+"/"+gp.getSingle(), Arith.mul(gp.getSingle(), order.getOdds()));
				}else {
					//否则不中奖
					vo.getBetResultMap().put(location+"/"+code+"/"+gp.getSingle(), gp.getSingle()*(-1));
				}
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
		OrderByContinuousCodeServiceImpl s=new OrderByContinuousCodeServiceImpl();
		OrderDTO order=new OrderDTO();

		order.setBetsContent("1/123456789/200");//
		order.setOdds(1.9);


		List<Integer> lotteryCode= new ArrayList<Integer>();
		lotteryCode.add(3);
		lotteryCode.add(4);
		lotteryCode.add(5);
		lotteryCode.add(4);
		lotteryCode.add(9);
		lotteryCode.add(9);
		OpenResultVo vo = s.countLotteryResult(order, lotteryCode);
		System.out.println("开奖号码: "+lotteryCode);
		System.out.println("开奖结果: "+vo.getLotteryResult());
		System.out.println("该注单所选择的号码:"+vo.getBetsCode().toString());
		System.out.println("该注单所选择的位置:"+vo.getBetsLocation().toString());
		System.out.println("该注单的总输赢: "+vo.isResult());
		System.out.println("该注单可以兑换的金额:"+vo.getWonMoney());
		System.out.println("该注单输赢的总金额: "+vo.getMoney());
		Map<String,Double> map=vo.getBetResultMap();
		//遍历map
		System.out.println("=========每注的输赢结果=========");
		for(Map.Entry<String,Double> entry : map.entrySet()){  
			System.out.println("下注的内容: "+entry.getKey()+",输赢的金额: "+entry.getValue());  
		} 
		
		
	}
}
