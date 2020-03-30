package com.lt.lxc.service;

import com.lt.lxc.controller.req.UpdateAddSettingReq;
import com.lt.lxc.entity.Settings;

public interface ISettingService {

	void add(UpdateAddSettingReq req);
	
	void updateById(UpdateAddSettingReq req,Long id);
	
	Settings findById(Long id);
	
	
}
