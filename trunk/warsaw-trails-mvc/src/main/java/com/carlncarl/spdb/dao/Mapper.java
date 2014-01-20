package com.carlncarl.spdb.dao;

import java.util.ArrayList;
import java.util.List;

import com.carlncarl.spdb.model.MainPoint;
import com.carlncarl.spdb.model.PointRate;
import com.carlncarl.spdb.model.Trail;
import com.carlncarl.spdb.model.TrailPoint;
import com.carlncarl.spdb.model.TrailRate;
import com.carlncarl.spdb.model.User;
import com.carlncarl.spdb.model.dto.CoordinateDto;
import com.carlncarl.spdb.model.dto.MainPointDto;
import com.carlncarl.spdb.model.dto.PointDto;
import com.carlncarl.spdb.model.dto.PointRateDto;
import com.carlncarl.spdb.model.dto.TrailDto;
import com.carlncarl.spdb.model.dto.TrailRateDto;
import com.carlncarl.spdb.model.dto.UserDto;
import com.vividsolutions.jts.geom.Coordinate;

public class Mapper {
	public static TrailDto toSimpleTrail(Trail model) {

		TrailDto dto = new TrailDto();
		dto.setDescription(model.getDescription());
		dto.setName(model.getName());
		dto.setStartTime(model.getStartTime());
		dto.setEndTime(model.getEndTime());
		dto.setType(model.getType());
		dto.setId(model.getId());
		Double rateAverage = 0.0;
		for (TrailRate rate : model.getRates()) {
			rateAverage += rate.getRate();
		}
		if (model.getRates().size() > 0) {
			dto.setRate(rateAverage / model.getRates().size());
		}
		return dto;
	}

	public static TrailDto toTrailDto(Trail model) {
		TrailDto dto = Mapper.toSimpleTrail(model);

		dto.setCreator(Mapper.getUserFromDao(model.getCreator()));

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
		ArrayList<TrailRateDto> rates = new ArrayList<TrailRateDto>();
		for (TrailRate rate : model.getRates()) {
			rates.add(toTrailRateDto(rate));
		}
		dto.setTrailRates(rates);

		return dto;
	}

	private static PointDto toPointDto(TrailPoint point) {
		PointDto dto = new PointDto();
		dto.setName(point.getPoint().getName());
		dto.setDescription(point.getDescription());
		dto.setPointDescription(point.getPoint().getDescription());
		dto.setEndTime(point.getEndTime());
		dto.setStartTime(point.getStartTime());
		dto.setId(point.getId());
		dto.setLatitude(point.getPoint().getPoint().x);
		dto.setLongitude(point.getPoint().getPoint().y);

		dto.setPointDescription(point.getPoint().getDescription());
		dto.setPoiRef(point.getPoint().getPoiReference());

		dto.setMainPointId(point.getPoint().getId());

		Double rateAverage = 0.0;
		ArrayList<PointRateDto> rates = new ArrayList<PointRateDto>();
		for (PointRate rate : point.getPoint().getRates()) {
			rateAverage += rate.getRate();
			rates.add(toPointRateDto(rate));
		}
		dto.setPointRates(rates);
		if (rates.size() > 0) {
			dto.setRate(rateAverage / rates.size());
		}
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

	public static UserDto getUserFromDao(User register) {
		return new UserDto(register.getId(), register.getLogin());
	}

	public static UserDto toUserDto(User user) {
		UserDto dto = getUserFromDao(user);

		List<PointRateDto> pRates = new ArrayList<PointRateDto>();
		for (PointRate pRate : user.getUserPointRates()) {
			pRates.add(toPointRateDto(pRate));
		}
		dto.setPointRates(pRates);

		List<TrailRateDto> tRates = new ArrayList<TrailRateDto>();
		for (TrailRate pRate : user.getUserTrailRates()) {
			tRates.add(toTrailRateDto(pRate));
		}
		dto.setTrailRates(tRates);

		return dto;
	}

	public static PointRateDto toPointRateDto(PointRate pRate) {
		PointRateDto dto = new PointRateDto();
		dto.setId(pRate.getId());
		dto.setMainPointId(pRate.getPoint().getId());
		dto.setUserId(pRate.getUser().getId());
		dto.setUser(pRate.getUser().getLogin());
		dto.setComment(pRate.getComment());
		dto.setRate(pRate.getRate());
		;
		return dto;
	}

	public static TrailRateDto toTrailRateDto(TrailRate tRate) {
		TrailRateDto dto = new TrailRateDto();
		dto.setId(tRate.getId());
		dto.setTrailId(tRate.getTrail().getId());
		dto.setUserId(tRate.getUser().getId());
		dto.setUser(tRate.getUser().getLogin());
		dto.setComment(tRate.getComment());
		dto.setRate(tRate.getRate());
		;
		return dto;
	}
}
