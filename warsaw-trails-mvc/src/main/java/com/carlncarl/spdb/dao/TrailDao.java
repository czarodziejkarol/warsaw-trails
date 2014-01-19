package com.carlncarl.spdb.dao;

import java.util.List;

import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.Trail;
import com.carlncarl.spdb.model.dto.MainPointDto;
import com.carlncarl.spdb.model.dto.TrailDto;

public interface TrailDao {
	
	public List<Trail> getTrailsByDateAdd();
	public Trail save(Trail trail) throws WarsawTrailsException;
	public boolean delete(Trail trail);
	public TrailDto getTrailById(Long id);
	public List<MainPointDto> getNearPoints(double latitude, double longitude,
			Long distance);
}
