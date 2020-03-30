package com.lt.lxc.dubbo.provider.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.lt.lxc.dao.IOpenDataRepository;
import com.lt.lxc.entity.OpenData;
import com.lt.lxc.service.IOpenDataService;
import com.lt.lxc.service.OpenDataISV;
import com.lt.lxc.utils.GlobalConstant.NextIssueNumber;

@Service(version = "1.0.0")
public class OpenDataSV implements OpenDataISV{

	@Autowired
	private IOpenDataService openDataService;
	
	@Autowired
	private IOpenDataRepository iOpenDataRep;
	
	@Override
	public String getIssueNumber() {
		return NextIssueNumber.ISSUE_NUMBER.getData();
	}

	@Override
	public String getOpenCode(String issueNumber) {
		OpenData od = iOpenDataRep.findByIssueNumber(issueNumber);
		if(null == od) {
			return null;
		}
		return od.getOpenCode();
	}

}
