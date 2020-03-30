package com.lt.lxc.controller.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenResultVo {
	//注单的总输赢true为赢,false为输
	private boolean result;
	//注单的总输赢金额,正为赢,负为输
	private Double money;
	//注单可以兑奖的金额
	private Double wonMoney;
	//开奖号码
	private List<Integer> lotteryCode = new ArrayList<Integer>();
	//用户号码
	private List<Integer> betsCode = new ArrayList<>();
	//用户选择位置
	private List<Integer> betsLocation=new ArrayList<Integer>();
	//用户每注的结果
	private Map<String,Double> betResultMap=new HashMap<String,Double>();
	//开奖结果
	private List<String> lotteryResult=new ArrayList<String>();
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public List<Integer> getLotteryCode() {
		return lotteryCode;
	}
	public void setLotteryCode(List<Integer> lotteryCode) {
		this.lotteryCode = lotteryCode;
	}
	public List<Integer> getBetsLocation() {
		return betsLocation;
	}
	public void setBetsLocation(List<Integer> betsLocation) {
		this.betsLocation = betsLocation;
	}
	public Map<String, Double> getBetResultMap() {
		return betResultMap;
	}
	public void setBetResultMap(Map<String, Double> betResultMap) {
		this.betResultMap = betResultMap;
	}
	public List<String> getLotteryResult() {
		return lotteryResult;
	}
	public void setLotteryResult(List<String> lotteryResult) {
		this.lotteryResult = lotteryResult;
	}
	public List<Integer> getBetsCode() {
		return betsCode;
	}
	public void setBetsCode(List<Integer> betsCode) {
		this.betsCode = betsCode;
	}
	public Double getWonMoney() {
		return wonMoney;
	}
	public void setWonMoney(Double wonMoney) {
		this.wonMoney = wonMoney;
	}
	
}
