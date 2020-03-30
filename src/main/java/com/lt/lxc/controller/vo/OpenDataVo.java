package com.lt.lxc.controller.vo;

import java.util.ArrayList;
import java.util.List;

import com.lt.lxc.entity.OpenData;

public class OpenDataVo {
	private Long id;
	//开奖期号
	private String issueNumber;
	//开奖号码
	private String openCode;
	//彩种
	private String type;
	//开奖时间
	private Long openTime;
	//龙虎和
	private String longHuHe;
	//总和大小单双;
	private String zongHeDaXiaoDanShuang;
	//定位大小
	private String dingWeiDaXiao;
	//定位单双
	private String dingWeiDanShuang;
	//三区形态:前
	private String frontForm;
	//三区形态:后
	private String behindForm;
	//散丶重
	private String sanChong;
	//总和
	private Integer count;
	//前牛
	private String frontCow;
	//后牛
	private String behindCow;
	//单牛
	private String singleCow;
	//双牛
	private String doubleCow;

	public OpenDataVo() {
		super();
	}
	public OpenDataVo(OpenData data) {
		super();
		this.id = data.getId();
		this.issueNumber = data.getIssueNumber();
		this.openCode = data.getOpenCode();
		this.type = data.getType();
		this.openTime = data.getOpenTime();
	}

	public static List<OpenDataVo> tovo(List<OpenData> ods){
		List<OpenDataVo> odvos = new ArrayList<>();
		for (OpenData od : ods) {
			odvos.add(new OpenDataVo(od));
		}
		return odvos;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Long openTime) {
		this.openTime = openTime;
	}
	public String getLongHuHe() {
		return longHuHe;
	}
	public void setLongHuHe(String longHuHe) {
		this.longHuHe = longHuHe;
	}
	public String getDingWeiDaXiao() {
		return dingWeiDaXiao;
	}
	public void setDingWeiDaXiao(String dingWeiDaXiao) {
		this.dingWeiDaXiao = dingWeiDaXiao;
	}
	public String getSanChong() {
		return sanChong;
	}
	public void setSanChong(String sanChong) {
		this.sanChong = sanChong;
	}
	public String getZongHeDaXiaoDanShuang() {
		return zongHeDaXiaoDanShuang;
	}
	public void setZongHeDaXiaoDanShuang(String zongHeDaXiaoDanShuang) {
		this.zongHeDaXiaoDanShuang = zongHeDaXiaoDanShuang;
	}
	public String getDingWeiDanShuang() {
		return dingWeiDanShuang;
	}
	public void setDingWeiDanShuang(String dingWeiDanShuang) {
		this.dingWeiDanShuang = dingWeiDanShuang;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
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
	public String getFrontCow() {
		return frontCow;
	}
	public void setFrontCow(String frontCow) {
		this.frontCow = frontCow;
	}
	public String getBehindCow() {
		return behindCow;
	}
	public void setBehindCow(String behindCow) {
		this.behindCow = behindCow;
	}
	public String getSingleCow() {
		return singleCow;
	}
	public void setSingleCow(String singleCow) {
		this.singleCow = singleCow;
	}
	public String getDoubleCow() {
		return doubleCow;
	}
	public void setDoubleCow(String doubleCow) {
		this.doubleCow = doubleCow;
	}

	

}
