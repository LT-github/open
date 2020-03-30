package com.lt.lxc.controller.vo;

import java.util.ArrayList;
import java.util.List;

import com.lt.lxc.entity.OpenData;
import com.lt.lxc.utils.DateUtil;
import com.lt.lxc.utils.GlobalConstant.NextIssueNumber;

/**
 * 上期玩法结果
 * @author Administrator
 *
 */
public class LastOpenDataVo {
	//上期开奖期号
	private String issueNumber;
	//上期开奖号码
	private String openCode;
	//彩种
	private String type;
	//三组合对应结果集
	private List<String> result = new ArrayList<>();
	//当前期开奖时间
	private Long nextOpenTime;
	//当前期开奖期号
	private String nextIssueNumber;
	//服务器时间
	private Long sysTime;
	//上期开奖时间
	private Long openTime;
	
	
	public LastOpenDataVo() {
		super();
	}
	public LastOpenDataVo(OpenData od) {
		super();
		this.issueNumber = od.getIssueNumber();
		this.openCode = od.getOpenCode();
		this.type = od.getType();
		this.nextOpenTime = DateUtil.getTimeToMin(System.currentTimeMillis(), 5);
		this.nextIssueNumber = NextIssueNumber.ISSUE_NUMBER.getData();
		this.openTime = od.getOpenTime();
		this.sysTime = System.currentTimeMillis();
	}
	public String getIssueNumber() {
		return issueNumber;
	}
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	public String getOpenCode() {
		return openCode;
	}
	public void setOpenCode(String openCode) {
		this.openCode = openCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getResult() {
		return result;
	}
	public void setResult(List<String> result) {
		this.result = result;
	}
	public Long getNextOpenTime() {
		return nextOpenTime;
	}
	public void setNextOpenTime(Long nextOpenTime) {
		this.nextOpenTime = nextOpenTime;
	}
	public String getNextIssueNumber() {
		return nextIssueNumber;
	}
	public void setNextIssueNumber(String nextIssueNumber) {
		this.nextIssueNumber = nextIssueNumber;
	}
	public Long getSysTime() {
		return sysTime;
	}
	public void setSysTime(Long sysTime) {
		this.sysTime = sysTime;
	}
	public Long getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Long openTime) {
		this.openTime = openTime;
	}
	
}
