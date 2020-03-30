package com.lt.lxc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lt.lxc.controller.req.OpenDataFindReq;
import com.lt.lxc.controller.resp.PageResp;
import com.lt.lxc.controller.vo.GameResultVo;
import com.lt.lxc.controller.vo.LastOpenDataVo;
import com.lt.lxc.controller.vo.OpenDataVo;
import com.lt.lxc.entity.OpenData;
import com.lt.lxc.service.IOpenDataService;
import com.lt.lxc.utils.HttpResult;

@RestController
@RequestMapping("/open")
public class OpenDataController {

	@Autowired
	private IOpenDataService openDataService;
	
	@GetMapping("/one")
	public HttpResult<LastOpenDataVo> findOne(){
		return HttpResult.success(openDataService.findOne());	
	}
	
	@GetMapping("/list")
	public HttpResult<PageResp<OpenDataVo,OpenData>> findList(OpenDataFindReq req){
		return HttpResult.success(openDataService.findAll(req));
	}
	
	@GetMapping("/result/{id}")
	public HttpResult<GameResultVo> gameResult(@PathVariable Long id){
		return HttpResult.success(openDataService.findGameResult(id));
	}
	
}
