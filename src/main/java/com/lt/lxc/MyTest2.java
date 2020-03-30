package com.lt.lxc;

import java.util.ArrayList;
import java.util.List;

import com.lt.lxc.controller.req.TestReq;
import com.lt.lxc.controller.vo.TestVo;
import com.lt.lxc.game.CombineAndArrangement;

public class MyTest2 {

	public static void main(String[] args) {
		
//		Long differTime = (System.currentTimeMillis()/1000) - (ConstantConfig.FIRST_OPEN_TIME/1000);
//		Long openTime = differTime%(ConstantConfig.INTERVAL_TIME*60);
//		int a = 445615;
//		int b = 60;
//		int  c = a%b;
//		System.out.println(openTime);
//		
//		System.out.println("".length());
		
		TestReq req = new TestReq();
		req.setCode("123456");
		req.setNumber(3);
		 List<String> combined = extracted(req).getCombined();
		 Integer count = extracted(req).getCount();
		 System.out.println("共有"+count+"种组合");
		 for (String string : combined) {
			 System.out.println(string);
		}
	}

	private static TestVo extracted(TestReq req) {
		int[] arr = new int[req.getCode().length()];
		for (int i = 0; i < req.getCode().length(); i++) {
			arr[i] = Integer.parseInt(req.getCode().substring(i, i + 1));//substring是找出包含起始位置，不包含结束位置，到结束位置的前一位的子串
		}
		int index = 0;
		ArrayList<Integer> tmpArr  = new ArrayList<Integer>();
		List<String> result = new ArrayList<String>();
		List<String> list = CombineAndArrangement.combine(index,req.getNumber(),arr,tmpArr,result);
		TestVo vo = new TestVo();
		vo.setCombined(list);
		vo.setCount(list.size());
		return vo;
	}
}
