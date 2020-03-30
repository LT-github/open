package com.lt.lxc.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lt.lxc.controller.req.UpdateAddSettingReq;
import com.lt.lxc.dao.ISettingsRepository;
import com.lt.lxc.entity.Settings;
import com.lt.lxc.service.ISettingService;
import com.lt.lxc.utils.IdWorker;
import com.lt.lxc.utils.MyBeanUtils;

@Service
public class SettingServiceImpl implements ISettingService {

	@Autowired
	private ISettingsRepository iSettingsRep;
	@Autowired
	private IdWorker idWorker;
	
	@Override
	public void updateById(UpdateAddSettingReq req,Long id) {
		Optional<Settings> opt = iSettingsRep.findById(id);
		if(!opt.isPresent())
			throw new RuntimeException(id+"设置选项并未找到");
		opt.get().setDataValue(req.getValue());
		iSettingsRep.flush();
	}

	@Override
	public Settings findById(Long id) {
		Optional<Settings> opt = iSettingsRep.findById(id);
		if(!opt.isPresent())
			throw new RuntimeException("设置选项并未找到");
		return opt.get();
	}

	@Override
	public void add(UpdateAddSettingReq req) {
		Settings set  = new Settings();
		MyBeanUtils.copyProperties(req, set);
		set.setId(idWorker.nextId());
		iSettingsRep.save(set);
	}
	
}
