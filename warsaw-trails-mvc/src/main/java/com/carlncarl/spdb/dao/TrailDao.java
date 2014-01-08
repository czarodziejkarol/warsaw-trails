package com.carlncarl.spdb.dao;

import com.carlncarl.spdb.exception.WarsawTrailsException;
import com.carlncarl.spdb.model.Trail;

public interface TrailDao {
	public Trail save(Trail trail) throws WarsawTrailsException;
	public boolean delete(Trail trail);
}
