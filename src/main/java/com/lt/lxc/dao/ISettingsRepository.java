package com.lt.lxc.dao;

import com.lt.lxc.entity.Settings;
import com.lt.lxc.jpa.BaseRepository;

public interface ISettingsRepository extends BaseRepository<Settings, Long> {
	
	
//	@Query("select Settings from Settings s where s.key = ?1")
//	Settings findByKey(String key);
}
