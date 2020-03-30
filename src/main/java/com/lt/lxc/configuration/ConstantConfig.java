package com.lt.lxc.configuration;

public class ConstantConfig {

	//随机数组
	public static final Integer RANDOM_GROUP_COUNT  = 1;
	//线程数量
	public static final Integer THREAD_COUNT = 6;
	//第一次开奖期号
	public static final String FIRST_ISSUE_NUMBER  = "20191221001";
	//开奖间隔时间
	public static final Integer INTERVAL_TIME  = 5;
	//第一次开奖时间
	public static final Long FIRST_OPEN_TIME  = 1576900800000l;
	//cron表达式
	public static final String OPNE_CRON = "0 0,5,10,15,20,25,30,35,40,45,50,55 * * * *";
}
