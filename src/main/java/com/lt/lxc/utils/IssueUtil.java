package com.lt.lxc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.lt.lxc.configuration.ConstantConfig;

/**
 * 获取期号
 * @author Administrator
 *
 */
public class IssueUtil {

	/**
	 * 取得这次流水号
	 * @param maxIssue 数据库中上期流水号
	 * @return
	 */
	public static String getThisIssueNumber() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		String newTime = sf.format(new Date());
		//新流水号
		long currentTimestamps=System.currentTimeMillis();
        long oneDayTimestamps= Long.valueOf(60*60*24*1000);
        long weeHour = currentTimestamps-(currentTimestamps+60*60*8*1000)%oneDayTimestamps;
        String newSubIssue = (currentTimestamps-weeHour)/(ConstantConfig.INTERVAL_TIME*60*1000)+1+"";
//		String oldTime = maxIssue.substring(0,maxIssue.length()-ISSUELENGTH);//时间串
//		String oldSubIssue = maxIssue.substring(maxIssue.length()-ISSUELENGTH);//流水号
//		
//		//如果不是当前日期
//		if(!newTime.equals(oldTime)) {
			//不够位数补零
			newSubIssue = String.format("%0"+3+"d",Integer.parseInt(newSubIssue));
//			return newTime+newSubIssue;
//		}else {
//			//如果是
//			//不够位数补零
//			newSubIssue = String.format("%0"+ISSUELENGTH+"d",Integer.parseInt(oldSubIssue)+1);
//		}
		return newTime+newSubIssue;
	}
	
//	/**
//	 * 获取两个期号之间的总期数
//	 * @param beforeIssue
//	 * @param afterIssue
//	 * @return
//	 */
//	public static Double getDistanceOfTowIssueNumber(String beforeIssue,String afterIssue) {
////		Date afterTime = DateUtil.parseDate(afterIssue.substring(0,8), DateUtil.DEFAULT_FORMATS);
////		Date beforeTime = DateUtil.parseDate(beforeIssue.substring(0,8), DateUtil.DEFAULT_FORMATS);
////		Double differTime = DateUtil.getDistanceOfTwoDate(beforeTime, afterTime);
//		return null;
//	}
	
	public static void main(String[] args) {
		System.out.println(getThisIssueNumber());
	}
}
