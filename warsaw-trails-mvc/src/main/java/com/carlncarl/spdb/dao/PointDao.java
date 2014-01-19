package com.carlncarl.spdb.dao;

import com.carlncarl.spdb.model.MainPoint;

public interface PointDao {

	public MainPoint getMainPointById(Long id);
	public MainPoint getMainPointByPoi(String poi);
}
