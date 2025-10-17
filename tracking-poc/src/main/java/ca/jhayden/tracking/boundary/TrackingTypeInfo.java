package ca.jhayden.tracking.boundary;

import java.util.Collections;
import java.util.List;

public interface TrackingTypeInfo {
	public static final List<TrackingTypeInfo> EMPTY_LIST = Collections.emptyList();

	public abstract String getCode();

	public abstract TrackingFormatType getFormatType();

	public abstract RecordFrequency getRecordFrequency();
}
