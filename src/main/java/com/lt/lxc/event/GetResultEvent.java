package com.lt.lxc.event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationEvent;

public class GetResultEvent extends ApplicationEvent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Integer> lottery = new ArrayList<>();
	
	
	
	public GetResultEvent(Object source, List<Integer> lottery) {
		super(source);
		this.lottery = lottery;
	}

	public List<Integer> getLottery() {
		return lottery;
	}

	public void setLottery(List<Integer> lottery) {
		this.lottery = lottery;
	}

	
}
