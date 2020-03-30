package com.lt.lxc;

import java.math.BigDecimal;

public class MyTest3 {

	public static void main(String[] args) {
//		long time =System.currentTimeMillis();
//
//		long opentime =DateUtil.getTimeToMin(1581402329223l, 5);
//
//		System.out.println(opentime);
//
//		Double y[] = {1.0,2.0,3.5,4.1,2.33,4.23};
//		List<Double> d=Arrays.asList(y);		
//		Double sum=d.stream()
//				.reduce(0.0,(a,b)->Arith.add(a, b));
//		System.out.println(sum);

		
		BigDecimal b1 = new BigDecimal("-12.6");
		BigDecimal b2 = new BigDecimal("12.4");
		System.err.println(b1.add(b2).doubleValue());
		
	}

}
