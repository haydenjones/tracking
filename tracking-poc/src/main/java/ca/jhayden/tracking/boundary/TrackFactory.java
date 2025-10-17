package ca.jhayden.tracking.boundary;

import java.util.List;
import java.util.Optional;

import ca.jhayden.tracking.entity.Track;
import ca.jhayden.util.Strings;

public class TrackFactory {

	private TrackApi api = new TrackApi();

	public TrackFactory() {
		super();
	}

	public Track parse(String s) {
		// Let's parse the code first
		int squareBegin = s.indexOf("[");
		if (squareBegin < 0) {
			throw new IllegalArgumentException("Missing a trackingType [ in " + s);
		}
		int squareEnd = s.indexOf("]", squareBegin);
		if (squareEnd <= squareBegin) {
			throw new IllegalArgumentException("Missing a trackingType ] in " + s);
		}

		String trackingTypeCode = Strings.notEmpty(s.substring(squareBegin + 1, squareEnd), "TrackingTypeCode");
		List<TrackingTypeInfo> types = api.getTrackingTypes();

		Optional<TrackingTypeInfo> optionalType = types.stream().filter(tti -> tti.getCode().equals(trackingTypeCode))
				.findFirst();

		TrackingTypeInfo typeInfo = optionalType
				.orElseThrow(() -> new IllegalArgumentException("Unrecognized code: " + trackingTypeCode));

		if ("NAME".equals(typeInfo.getCode())) {
			return parseName(typeInfo, s, squareBegin, squareEnd);
		}
		else if ("DATE_OF_BIRTH".equals(typeInfo.getCode())) {
			return parseName(typeInfo, s, squareBegin, squareEnd);
		}
		else if ("HEIGHT_CM".equals(typeInfo.getCode())) {
			return parseSingleFloat(typeInfo, s, squareBegin, squareEnd);
		}
		else if ("WEIGHT_LBS".equals(typeInfo.getCode())) {
			return parseSingleFloat(typeInfo, s, squareBegin, squareEnd);
		}
		else {
			throw new IllegalArgumentException("Don't know how to simple parse " + s);
		}
	}

	Track parseName(TrackingTypeInfo typeInfo, String s, int squareBegin, int squareEnd) {
		final Track t = new Track();
		t.setTrackingTypeCode(typeInfo.getCode());
		t.setMisc(Strings.nullIfEmpty(s.substring(squareEnd + 1)));
		return t;
	}

	Track parseSingleFloat(TrackingTypeInfo typeInfo, String s, int squareBegin, int squareEnd) {
		final Track t = new Track();
		t.setTrackingTypeCode(typeInfo.getCode());
		String value1 = Strings.nullIfEmpty(s.substring(squareEnd + 1));
		if (value1 != null) {
			t.setValue1(Float.valueOf(value1));
		}
		return t;
	}
}
