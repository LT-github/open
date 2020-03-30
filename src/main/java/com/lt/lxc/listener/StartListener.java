package com.lt.lxc.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.lt.lxc.thread.AsyncMethod;
import com.lt.lxc.utils.GlobalConstant.NextIssueNumber;
import com.lt.lxc.utils.IssueUtil;

@Component
public class StartListener implements ApplicationListener<ApplicationReadyEvent>{

	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		System.err.println(">>>>>>>开奖系统启动");
		NextIssueNumber.ISSUE_NUMBER.setData(IssueUtil.getThisIssueNumber());
		System.err.println(">>>>>>>当前期号为"+NextIssueNumber.ISSUE_NUMBER.getData());
	}
	
}