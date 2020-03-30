package com.lt.lxc.calculate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lt.lxc.controller.vo.OpenResultVo;
import com.lt.lxc.game.teamplate.IOrderService;
import com.lt.lxc.listener.ResultEventListener;
import com.lt.lxc.pojo.OrderDTO;
import com.lt.lxc.utils.Arith;
import com.lt.lxc.utils.GlobalConstant;

/**
 * 大数据计算
 * @author Administrator
 *
 */
@Component
public class DataCalculate {

	@Autowired
	private Map<String,IOrderService> orderServiceMap;
	
	
	public boolean calculate(List<Integer> lottery) {
		//订单金额
		double betsMoney = 0.0;
		//所杀大数据金额
		double wonMoney = 0.0; 
		//拿出所有的dto
		List<OrderDTO> dtos = ResultEventListener.ORDER_LIST;
		if(null == dtos||10 > dtos.size()) {
			return true;
		}
		for (OrderDTO dto : dtos) {
			betsMoney = Arith.add(betsMoney, dto.getTotalMoney());
			IOrderService iOrderService = orderServiceMap.get(dto.getGame());
			OpenResultVo vo =  iOrderService.countLotteryResult(dto, lottery);
			wonMoney = Arith.add(wonMoney, vo.getMoney());
		}
		if(betsMoney< GlobalConstant.Setting.MIN_BET_MONEY.getValue()) {
			return true;
		}
		double wonRatio = Arith.div(wonMoney, betsMoney, 4)*(-1);
		boolean flag = wonRatio> GlobalConstant.Setting.MIN_RATIO.getValue() && wonRatio<GlobalConstant.Setting.MAX_RATIO.getValue();
		if(flag ==true) {
			System.err.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "    " + Thread.currentThread().getName() + "号码为: "+lottery.toString()+"    计算出的比例为: "+wonRatio+"  , 共盈利"+wonMoney+"元");
		}
		
		return wonRatio> GlobalConstant.Setting.MIN_RATIO.getValue() && wonRatio<GlobalConstant.Setting.MAX_RATIO.getValue();
	}
}
