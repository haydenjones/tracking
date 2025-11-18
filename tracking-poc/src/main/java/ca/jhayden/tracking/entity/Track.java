package ca.jhayden.tracking.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import ca.jhayden.tracking.boundary.TrackingFormatType;
import ca.jhayden.util.Strings;

public class Track {
	public enum SortBy implements Comparator<Track> {
		WHEN_ASC {
			@Override
			public int compare(Track o1, Track o2) {
				return o1.getWhen().compareTo(o2.getWhen());
			}
		},
		WHEN_DESC {
			@Override
			public int compare(Track o1, Track o2) {
				return o2.getWhen().compareTo(o1.getWhen());
			}
		};
	}

	public static final List<Track> EMPTY_LIST = Collections.emptyList();

	static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static Track of(String typeCode, String value) {
		final var track = new Track();
		track.setTrackingTypeCode(typeCode);
		return track;
	}

	private String trackingTypeCode = "";
	private LocalDateTime when = LocalDateTime.now();
	private Float value1 = null;
	private Duration duration = null;
	private String misc = null;
	private TrackingFormatType formatType = TrackingFormatType.SINGLE_WHOLE_VALUE;

	public Track() {
		super();
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration d) {
		duration = d;
	}

	public TrackingFormatType getFormatType() {
		return formatType;
	}

	public void setFormatType(TrackingFormatType fmt) {
		formatType = Objects.requireNonNull(fmt);
	}

	public LocalDateTime getWhen() {
		return when;
	}

	public void setWhen(LocalDateTime dt) {
		when = dt;
	}

	public Float getValue1() {
		return value1;
	}

	public void setValue1(Float f) {
		value1 = f;
	}

	public void setTrackingTypeCode(String s) {
		trackingTypeCode = Strings.trim(s);
	}

	public String getTrackingTypeCode() {
		return trackingTypeCode;
	}

	public String getMisc() {
		return misc;
	}

	public void setMisc(String s) {
		this.misc = Strings.nullIfEmpty(s);
	}

	@Override
	public String toString() {
		return String.format("%s [%s] %s %s", when.format(DTF), trackingTypeCode, misc, value1);
	}
}
