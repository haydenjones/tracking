package ca.jhayden.tracking.swing.simple;

import java.util.Collection;
import java.util.List;

import ca.jhayden.tracking.boundary.TrackingTypeInfo;
import ca.jhayden.tracking.swing.SimpleTrackingAI;

public record SimpleUiSetup(SimpleTrackingAI ai, List<TrackingTypeInfo> expecting, Collection<TrackingTypeInfo> all) {
}
