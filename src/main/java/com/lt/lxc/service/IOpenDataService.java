package com.lt.lxc.service;

import java.util.List;

import com.lt.lxc.controller.req.OpenDataFindReq;
import com.lt.lxc.controller.resp.PageResp;
import com.lt.lxc.controller.vo.GameResultVo;
import com.lt.lxc.controller.vo.LastOpenDataVo;
import com.lt.lxc.controller.vo.OpenDataVo;
import com.lt.lxc.entity.OpenData;

public interface IOpenDataService {

	String add(List<Integer> lottery,Long openTime);
	
	LastOpenDataVo findOne();
	
	PageResp<OpenDataVo,OpenData> findAll(OpenDataFindReq req);
	
	GameResultVo findGameResult(Long id);
	
	
}
