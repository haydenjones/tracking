package ca.jhayden.tracking.swing.simple;

import java.util.List;

import ca.jhayden.tracking.boundary.TrackApi;
import ca.jhayden.tracking.boundary.TrackingTypeInfo;
import ca.jhayden.tracking.swing.SimpleTrackingAI;

public record SimpleUiSetup(TrackApi api, SimpleTrackingAI ai, List<TrackingTypeInfo> expecting) {
}
