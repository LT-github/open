package com.lt.lxc.thread;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.lt.lxc.configuration.ConstantConfig;
import com.lt.lxc.controller.vo.OpenResultVo;
import com.lt.lxc.game.teamplate.IOrderService;
import com.lt.lxc.listener.ResultEventListener;
import com.lt.lxc.pojo.OrderDTO;
import com.lt.lxc.service.OrderISV;
import com.lt.lxc.utils.GlobalConstant;

@Component
public class AsyncMethod {
	
	@Autowired
	private Map<String,IOrderService> orderServiceMap;

	@Autowired
	private CalculateThread calculateThread;


	@Reference(version = "1.0.0", url = "dubbo://localhost:12345",check=false)
	private OrderISV orderISV;


	/**
	 * 开启多个线程用于计算大数据
	 */
	@Async
	public void run(){
		System.err.println(""+LocalDateTime.now()+">>>>> 开启开始线程");
		try {
			for (int i = 0; i < ConstantConfig.THREAD_COUNT+1; i++) {
				calculateThread.calculate();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public void updateOrderDTO(List<Integer> lottery) {
		Map<Long,OrderDTO> map = new HashMap<>();

		//从静态中拿到订单列表
		List<OrderDTO> dtos = ResultEventListener.ORDER_LIST;
		if(null == dtos || 0 == dtos.size())
			return;
		System.err.println(""+LocalDateTime.now()+">>>>> 正在结算订单");
		for (OrderDTO dto : dtos) {
			//依赖查找,解析每个订单
			IOrderService iOrderService = orderServiceMap.get(dto.getGame());
			OpenResultVo vo = iOrderService.countLotteryResult(dto,lottery);
			String exchangeDetail = "";
			if(vo.isResult()) {
				dto.setLotteryResult(GlobalConstant.LotteryResult.WIN.getCode());
			}else {
				dto.setLotteryResult(GlobalConstant.LotteryResult.LOSE.getCode());
			}
			if(null != vo.getWonMoney() || 0.0 != vo.getWonMoney()) {
				if("NiuNiuTemplate".equals(dto.getGame())&& !vo.isResult()&&0 != vo.getWonMoney() ){
					exchangeDetail += "牛牛押金退还金额: "+vo.getWonMoney();
				}
				for(Map.Entry<String,Double> entry : vo.getBetResultMap().entrySet()){  
					if(entry.getValue()>0) {
						if(exchangeDetail.length() != 0) {
							exchangeDetail += ",";
						}
						exchangeDetail += entry.getKey()+"="+entry.getValue();
					}
				}
			}
			dto.setExchangeDetail(exchangeDetail);
			dto.setBattleResult(vo.getWonMoney());
			map.put(dto.getId(), dto);
		}
		orderISV.orderSettle(map);
		System.err.println(""+LocalDateTime.now()+">>>>> 结算订单成功");
	}
	
}
