package com.carlncarl.spdb.dao;

import java.util.ArrayList;

import com.carlncarl.spdb.model.MainPoint;
import com.carlncarl.spdb.model.Trail;
import com.carlncarl.spdb.model.TrailPoint;
import com.carlncarl.spdb.model.dto.CoordinateDto;
import com.carlncarl.spdb.model.dto.MainPointDto;
import com.carlncarl.spdb.model.dto.PointDto;
import com.carlncarl.spdb.model.dto.TrailDto;
import com.carlncarl.spdb.model.dto.UserDto;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;

public class Mapper {
	public static TrailDto toSimpleTrail(Trail model) {

		TrailDto dto = new TrailDto();
		dto.setDescription(model.getDescription());
		dto.setName(model.getName());
		dto.setStartTime(model.getStartTime());
		dto.setEndTime(model.getEndTime());
		dto.setType(model.getType());
		dto.setId(model.getId());

		return dto;
	}

	public static TrailDto toTrailDto(Trail model) {
		TrailDto dto = Mapper.toSimpleTrail(model);

		dto.setCreator(UserDto.getUserFromDao(model.getCreator()));

		ArrayList<CoordinateDto> path = new ArrayList<>();
		for (Coordinate co : model.getRoad().getCoordinates()) {
			path.add(toCoordinateDto(co));
		}

		dto.setPath(path);

		ArrayList<PointDto> points = new ArrayList<>();
		for (TrailPoint point : model.getPoints()) {
			points.add(Mapper.toPointDto(point));
		}
		dto.setPoints(points);
		return dto;
	}

	private static PointDto toPointDto(TrailPoint point) {
		PointDto dto = new  PointDto();
		dto.setDescription(point.getDescription());
		dto.setEndTime(point.getEndTime());
		dto.setStartTime(point.getStartTime());
		dto.setId(point.getId());
		dto.setLatitude(point.getPoint().getPoint().x);
		dto.setLongitude(point.getPoint().getPoint().y);
		
		dto.setPointDescription(point.getPoint().getDescription());
		dto.setPoiRef(point.getPoint().getPoiReference());
		
		dto.setMainPointId(point.getPoint().getId());
		
		
		
		
		return dto;
	}

	public static CoordinateDto toCoordinateDto(Coordinate co) {
		return new CoordinateDto(co.x, co.y);
	}

	public static MainPointDto toSimpleMainPointsDto(MainPoint model) {
		MainPointDto dto = new MainPointDto();
		dto.setId(model.getId());
		dto.setDescription(model.getDescription());
		dto.setCoordinate(Mapper.toCoordinateDto(model.getPoint()));
		dto.setName(model.getName());
		dto.setPoiReference(model.getPoiReference());
		return dto;
	}
}
