package com.lt.lxc.event;

import org.springframework.context.ApplicationEvent;

public class TimeOutEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long nowTime;

	public TimeOutEvent(Object source, Long nowTime) {
		super(source);
		this.nowTime = nowTime;
	}

	public Long getNowTime() {
		return nowTime;
	}

	public void setNowTime(Long nowTime) {
		this.nowTime = nowTime;
	}
	
	
	
	
}
