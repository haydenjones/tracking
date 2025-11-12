package ca.jhayden.tracking.boundary;

import java.time.LocalDateTime;

public interface TrackFormApi {
	public abstract LocalDateTime getWhen();

	public abstract String getTrackingTypeCode();

	public abstract String getText1();

	public abstract String getText2();

	public abstract String getText3();

	public abstract TrackingFormatType getFormatType();
}
