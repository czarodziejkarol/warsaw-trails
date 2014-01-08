package com.carlncarl.spdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlncarl.spdb.dao.TrailDao;
import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.Trail;
import com.carlncarl.spdb.model.dto.TrailDto;

@Service
public class TrailServiceIImpl implements TrailService{

	@Autowired
	private TrailDao trailDao;

	@Override
	public List<TrailDto> getTrailsByDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TrailDto> getTrailsByRate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrailDto save(TrailDto Trail) throws WarsawTrailsException {
		Trail trail = new Trail();
		
		trailDao.save(trail);
		return null;
	}

	@Override
	public TrailDto update(TrailDto trail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(TrailDto trail) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
