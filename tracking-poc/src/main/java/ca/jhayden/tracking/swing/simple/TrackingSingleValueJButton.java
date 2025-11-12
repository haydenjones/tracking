package ca.jhayden.tracking.swing.simple;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.swing.JButton;

import ca.jhayden.tracking.boundary.TrackFormApi;
import ca.jhayden.tracking.boundary.TrackingFormatType;
import ca.jhayden.tracking.boundary.TrackingTypeInfo;
import ca.jhayden.tracking.entity.Track;
import ca.jhayden.util.Strings;

public class TrackingSingleValueJButton extends JButton implements TrackFormApi {
	private static final long serialVersionUID = 5444825527771826125L;

	static String compute(TrackingTypeInfo info, Track track) {
		return "" + track.getValue1().intValue();
	}

	private final TrackingTypeInfo typeInfo;
	private final Track track;
	private final String text;

	public TrackingSingleValueJButton(TrackingTypeInfo info, Track track) {
		super(info.getCode() + ": " + compute(info, track));
		this.typeInfo = Objects.requireNonNull(info);
		this.track = Objects.requireNonNull(track);
		text = Strings.notEmpty(compute(info, track), "TrackingSingleValueJButton.value");
	}

	@Override
	public LocalDateTime getWhen() {
		return LocalDateTime.now();
	}

	public TrackingTypeInfo getTrackingTypeInfo() {
		return typeInfo;
	}

	@Override
	public String getTrackingTypeCode() {
		return typeInfo.getCode();
	}

	@Override
	public String getText1() {
		return text;
	}

	@Override
	public String getText2() {
		return "";
	}

	@Override
	public String getText3() {
		return "";
	}

	@Override
	public TrackingFormatType getFormatType() {
		return typeInfo.getFormatType();
	}
}
