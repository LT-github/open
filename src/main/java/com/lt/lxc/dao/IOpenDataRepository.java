package com.lt.lxc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.lt.lxc.entity.OpenData;
import com.lt.lxc.jpa.BaseRepository;

public interface IOpenDataRepository extends BaseRepository<OpenData, Long> {

	@Query(value = "select * from t_open_data order by open_time desc limit 0,1 " ,nativeQuery = true)
	OpenData findByMaxTime();
	
	OpenData findByIssueNumber(String issueNumber);
	
	@Query(value = "select * from t_open_data order by open_time asc limit 0,?1 " ,nativeQuery = true)
	List<OpenData> findByLimit(int count);
	
}
