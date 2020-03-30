package com.lt.lxc.controller.vo;

import java.util.Map;

/**
 * 某些玩法对应的结果
 * @author Administrator
 *
 */
public class GameResultVo {

	
	//期号
	private String issueNumber;
	
	//散丶重
	private String sanChong;
	//牛牛
	private Map<String,String> niuNiu;
	//定位大小
	private String dingWeiDaXiao;
	//定位单双
	private String dingWeiDanShuang;
	//定位组合
	private String dingWeiCombined;
	//龙虎和
	private String longHuHe;
	//总和大小单双
	private String zongHeDaXiaoDanShuang;
	//总和大小单双龙虎两组合
	private String twoCombined;
	//总和大小单双龙虎三组合
	private String threeCombined;
	//三区形态:前
	private String frontForm;
	//三区形态:后
	private String behindForm;
	//总和
	private Integer count;


	public String getSanChong() {
		return sanChong;
	}
	public void setSanChong(String sanChong) {
		this.sanChong = sanChong;
	}
	public String getDingWeiDaXiao() {
		return dingWeiDaXiao;
	}
	public void setDingWeiDaXiao(String dingWeiDaXiao) {
		this.dingWeiDaXiao = dingWeiDaXiao;
	}
	public String getDingWeiDanShuang() {
		return dingWeiDanShuang;
	}
	public void setDingWeiDanShuang(String dingWeiDanShuang) {
		this.dingWeiDanShuang = dingWeiDanShuang;
	}
	public String getDingWeiCombined() {
		return dingWeiCombined;
	}
	public void setDingWeiCombined(String dingWeiCombined) {
		this.dingWeiCombined = dingWeiCombined;
	}
	public String getLongHuHe() {
		return longHuHe;
	}
	public void setLongHuHe(String longHuHe) {
		this.longHuHe = longHuHe;
	}
	public String getTwoCombined() {
		return twoCombined;
	}
	public void setTwoCombined(String twoCombined) {
		this.twoCombined = twoCombined;
	}
	public String getThreeCombined() {
		return threeCombined;
	}
	public void setThreeCombined(String threeCombined) {
		this.threeCombined = threeCombined;
	}
	public String getZongHeDaXiaoDanShuang() {
		return zongHeDaXiaoDanShuang;
	}
	public void setZongHeDaXiaoDanShuang(String zongHeDaXiaoDanShuang) {
		this.zongHeDaXiaoDanShuang = zongHeDaXiaoDanShuang;
	}
	public String getFrontForm() {
		return frontForm;
	}
	public void setFrontForm(String frontForm) {
		this.frontForm = frontForm;
	}
	public String getBehindForm() {
		return behindForm;
	}
	public void setBehindForm(String behindForm) {
		this.behindForm = behindForm;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Map<String, String> getNiuNiu() {
		return niuNiu;
	}
	public void setNiuNiu(Map<String, String> niuNiu) {
		this.niuNiu = niuNiu;
	}
	public String getIssueNumber() {
		return issueNumber;
	}
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	
}
