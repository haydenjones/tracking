package ca.jhayden.tracking.swing.simple;

import java.util.List;
import java.util.Map;

import ca.jhayden.tracking.boundary.ReportInfo;
import ca.jhayden.tracking.boundary.TrackingTypeInfo;
import ca.jhayden.tracking.entity.Track;
import ca.jhayden.tracking.swing.SimpleTrackingAI;

public record SimpleUiSetup( //
		SimpleTrackingAI ai, //
		List<TrackingTypeInfo> expecting, //
		Map<TrackingTypeInfo, Track> all, //
		ReportInfo report, //
		List<ReportInfo> reports) {
}
