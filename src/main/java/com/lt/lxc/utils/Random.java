package com.lt.lxc.utils;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 无规则的获取随机数
 * @author Administrator
 *
 */
public class Random {

	public static final Integer RANDOM_NUM = 6;
	
	
	public static List<List<Integer>> getRandom(int num) throws NoSuchAlgorithmException, NoSuchProviderException{
		// 返回的清单
		List<List<Integer>> randomList = new ArrayList<List<Integer>>();
		//设置获取random的算法
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
		// 生成种子
		byte[] seeds = SecureRandom.getSeed(12); //获取随机的byte数组，用来后续作为种子
		// 用于控制项目数量的计数器
		int counter = 0;
		// 记录实际循环的次数
		int realCount = 0;
		// 用于控制生成的组数的计数器
		int tmprows = 0;
		while (num > tmprows) {
			List<Integer> list = new ArrayList<Integer>();
			while (counter < RANDOM_NUM) {
				random.setSeed(seeds); //设置种子
				int cake = random.nextInt(10); //随机生成0-10的数字
				list.add(cake);
				counter++;
				random.nextBytes(seeds); //随机获取新的byte数组用以作为下次的种子，不断循环
				realCount++;
			}
			tmprows++;
			counter = 0;
			randomList.add(list);
		}
		return randomList;
	}
	
	
	
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
		Long start = System.currentTimeMillis();
		Random random = new Random();
		List<List<Integer>> randoms = random.getRandom(1);
		Long end = System.currentTimeMillis();
		for (List<Integer> list : randoms) {
			System.out.println(Arrays.asList(list));
		}
		System.out.println(end-start+"ms");
	}
}
