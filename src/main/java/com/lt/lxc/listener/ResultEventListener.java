package com.lt.lxc.listener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.lt.lxc.event.GetResultEvent;
import com.lt.lxc.event.TimeOutEvent;
import com.lt.lxc.pojo.OrderDTO;
import com.lt.lxc.service.BetsSwitchISV;
import com.lt.lxc.service.IOpenDataService;
import com.lt.lxc.thread.AsyncMethod;
import com.lt.lxc.thread.CalculateThread;



@Component
public class ResultEventListener {

	public static List<OrderDTO> ORDER_LIST = new ArrayList<>();
	@Autowired
	private AsyncMethod async;

	@Autowired
	private IOpenDataService openDataService;
	
	@Reference(version = "1.0.0", url = "dubbo://localhost:12345",check=false)
    private BetsSwitchISV betsSwitch;
	
	/**
	 * 监听时间到封盘时间时
	 * @param event
	 */
	@EventListener
	public void isTimeOut(TimeOutEvent event) {
		System.err.println(""+LocalDateTime.now()+">>>>> 封盘时间到");
//		//清空内存中的所有线程计算得出的彩票结果队列,用于下一次计算
//		CalculateThread.lotteryQueue.clear();
		CalculateThread.FLAG = true;
		if(null != ORDER_LIST) {
			//清空内存中order_list的order元素,用于进行下一次赋值
			ORDER_LIST.clear();
		}
		//调用后台系统提供的关闭下单服务
		ORDER_LIST = betsSwitch.closeBets();
        //异步启动开始线程
		async.run();
	}
	
	
	/**
	 * 监听到有开奖数据时
	 * @param event
	 */
	@EventListener
	public void isGetResult(GetResultEvent event) {
		System.err.println(""+LocalDateTime.now()+">>>>> 已开奖,开奖号码为"+event.getLottery().toString());
		// 将开奖数据存入数据库
		String issueNumber = openDataService.add(event.getLottery(),System.currentTimeMillis());
		
		//调用后台系统开启下单的服务,并将下期的数据传过去
		betsSwitch.openBets(Long.parseLong(issueNumber));
		
		//开启异步方法调取后台系统的结算订单服务
		async.updateOrderDTO(event.getLottery());
	}
	
	
	
	
}
