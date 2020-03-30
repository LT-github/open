package com.lt.lxc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lt.lxc.excel.ExcelService;
import com.lt.lxc.utils.HttpResult;
@RestController
@RequestMapping("/excel")
public class ExcelController {
	
	@Autowired
	private ExcelService excle;
	@GetMapping("/export")
	public HttpResult FromDBToExcel() {
		excle.FromDBToExcel();
		return HttpResult.success(null,"导出excel表成功!");
	}
	
}
