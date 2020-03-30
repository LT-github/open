package com.lt.lxc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lt.lxc.controller.req.TestReq;
import com.lt.lxc.controller.vo.TestVo;
import com.lt.lxc.game.CombineAndArrangement;

@RestController
@RequestMapping("/open")
public class testController {

	@PostMapping("/test")
	public TestVo test(@RequestBody TestReq req) {
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
