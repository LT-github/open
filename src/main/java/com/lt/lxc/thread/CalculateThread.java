package com.lt.lxc.thread;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.lt.lxc.calculate.DataCalculate;
import com.lt.lxc.configuration.ConstantConfig;
import com.lt.lxc.event.GetResultEvent;
import com.lt.lxc.utils.Random;

@EnableAsync
@Component
public class CalculateThread {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private DataCalculate dataCalculate;
	
//	//创建一个队列来装计算结果
//	public static final Queue<List<Integer>> lotteryQueue = new LinkedList<List<Integer>>();
	public static Boolean FLAG = true;
	/**
	 * 每个线程计算数据
	 * @throws InterruptedException
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 */
	@Async("resultCalculate")
	public void calculate() throws InterruptedException, NoSuchAlgorithmException, NoSuchProviderException {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "    " + Thread.currentThread().getName() + "    计算启动");
		Here :while(true) {
			//先获取随机数
			List<List<Integer>> randomList = Random.getRandom(ConstantConfig.RANDOM_GROUP_COUNT);
			for (List<Integer> lottery : randomList) {
				if(dataCalculate.calculate(lottery)) {
					synchronized(FLAG) {
						if(FLAG) {
							applicationContext.publishEvent(new GetResultEvent(this, lottery));
							FLAG = false;
						}
					}
					
//					//先添加结果
//					lotteryQueue.add(lottery);
//					synchronized (lotteryQueue) {
//						//队列中如果有元素,则发布事件
//						if(lotteryQueue.size()==1) {
//							applicationContext.publishEvent(new GetResultEvent(this, lottery));
//						}else {
//							System.err.println("并没有得出结果"+"lotteryQueue的size:"+lotteryQueue.size());
//						}
//					}
					System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "    " + Thread.currentThread().getName() + "    计算结束");
					break Here;
				}
			}
		}
	}
}

