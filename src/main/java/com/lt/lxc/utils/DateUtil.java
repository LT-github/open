package com.lt.lxc.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.util.StringUtils;

/**
 * 时间工具类
 */
public class DateUtil {

	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";// 时间格式
	public static final String DEFAULT_FORMAT1 = "yyyy/MM/dd HH:mm:ss";// 时间格式1
	public static final String DEFAULT_FORMATS = "yyyy-MM-dd";
	public static final String DATE_FOMATE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String Message_TIME = "yyyy年MM月dd日HH点mm分";

	public static final long ONE_DAY_TIME = 86400000;


	/**
	 * 格式化时间(Date 转换成String)
	 *
	 * @param date   时间
	 * @param format 时间格式 如： DEFAULT_FORMAT= "yyyy-MM-dd HH:mm:ss"
	 * @return 字符串
	 */
	public static String format(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 字符串格式化为时间
	 *
	 * @param dateStr  时间字符串
	 * @param format 时间格式 如：DEFAULT_FORMAT1 = "yyyy/MM/dd HH:mm:ss";// 时间格式1
	 * @return
	 */
	public static Date parseDate(String dateStr, String format) {
		Date date = null;
		if (!StringUtils.isEmpty(dateStr)) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			try {
				date = sdf.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * 获取当天的开始时间,格式：yyyy-MM-dd 00:00:00
	 * @return
	 */
	public static Date getCurrentStartTime() {
		return getSomeDayStartTime(new Date(),DEFAULT_FORMAT);
	}

	/**
	 * 获取当天的结束时间,格式：yyyy-MM-dd 23:59:59
	 * @return
	 */
	public static Date getCurrentEndTime() {
		return getSomeDayEndTime(new Date(),DEFAULT_FORMAT);
	}

	public static Date getSomeDayStartTime(Date date,String format) {
		return getSomeDayStartAndEndTime(date, format, " 00:00:00");
	}

	public static Date getSomeDayEndTime(Date date,String format) {
		return getSomeDayStartAndEndTime(date, format, " 23:59:59");

	}

	public static Date getSomeDayStartAndEndTime(Date date,String format,String format2) {
		String d = format(date, DEFAULT_FORMAT);
		String[] split = d.split("");
		String startTimeStr = split[0] + format2;
		return parseDate(startTimeStr,format);

	}

	/**
	 * 获取某个时间之后多少天的时间
	 * @param date 某时间
	 * @param day 几天
	 * @return
	 */
	public static Date getTimeToAfter(Date date,int day) {
		return new Date(date.getTime() + ONE_DAY_TIME * day);
	}

	/**
	 * 获取某个时间之前多少天的时间
	 * @param date 某时间
	 * @param day 几天
	 * @return
	 */
	public static Date getTimeToBeffre(Date date,int day) {
		return new Date(date.getTime() - ONE_DAY_TIME * day);
	}

	/**
	 * 获取某个时间的下几分钟的时间戳
	 * @param time 某个时间
	 * @param m 分钟数
	 * @return
	 */
	public static Long getTimeToMin(long time,long m) {
		long min =  time/(1000*60);
		if(min*1000*60<time) {
			min++;
		}
		for (int i = 0; i < m+1; i++) {
			if(min%m==0) {
				return min*1000*60; 
			}
			min++;
		}
		return null;
	}
	/**
	 * 时间戳转换为时间格式
	 * @param lt
	 * @return
	 */
	public static String stampToDate(long lt){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_FORMAT1);
		Date date = new Date(lt);
		return simpleDateFormat.format(date);
	}


	/**
	 * 获取两个时间戳范围内有多少天
	 */

	//计算间隔日，比较两个时间是否同一天，如果两个时间都是同一天的话，返回0。
	// 两个比较的时间都不是同一天的话，根据传参位置 可返回正数/负数。两个比较的时间都是同一天的话，返回0。返回时间区间
	public static List<Long> daysBetween(Long start, Long end)
	{





		List<Long> list=new ArrayList<Long>();

		Long n=end-start;
		int days=(int) (n/(60*60*24*1000));

		Long areEnd=0l;
		//如果是同一天
		if(days==0) {
			list.add(start);
			list.add(start+24*60*60*1000);
		}else {

			for(int i=0;i<days+2;i++) {

				list.add(start);
				areEnd=start+24*60*60*1000;

				start=areEnd;    	  
			}
		}

		return list;

	}


	/**
	 * 获取指定某一天的开始时间戳
	 *
	 * @param timeStamp 毫秒级时间戳
	 * @param timeZone  如 GMT+8:00
	 * @return
	 */
	public static Long getDailyStartTime(Long timeStamp, String timeZone) {
		System.out.println("timeStamp:"+timeStamp);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
		calendar.setTimeInMillis(timeStamp);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取指定某一天的结束时间戳
	 *
	 * @param timeStamp 毫秒级时间戳
	 * @param timeZone  如 GMT+8:00
	 * @return
	 */
	public static Long getDailyEndTime(Long timeStamp, String timeZone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
		calendar.setTimeInMillis(timeStamp);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTimeInMillis();
	}

}
