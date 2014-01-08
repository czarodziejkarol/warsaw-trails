package com.carlncarl.spdb.service;

import java.util.List;

import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.dto.TrailDto;

public interface TrailService {
	
	public List<TrailDto> getTrailsByDate();

	public List<TrailDto> getTrailsByRate();

	public TrailDto save(TrailDto Trail) throws WarsawTrailsException;
	
	public TrailDto update(TrailDto trail);
	
	public boolean delete(TrailDto trail);
	
}
