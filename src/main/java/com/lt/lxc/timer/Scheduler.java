package com.lt.lxc.timer;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lt.lxc.configuration.ConstantConfig;
import com.lt.lxc.event.TimeOutEvent;

@Component
public class Scheduler {


	@Autowired
	private ApplicationContext applicationContext;

	@Scheduled(cron = ConstantConfig.OPNE_CRON)
	public void isTimeout() throws Exception {
		applicationContext.publishEvent(new TimeOutEvent(this,System.currentTimeMillis()));
	}
}
