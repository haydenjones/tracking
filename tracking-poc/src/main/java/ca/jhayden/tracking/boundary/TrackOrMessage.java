package ca.jhayden.tracking.boundary;

import ca.jhayden.tracking.entity.Track;

public record TrackOrMessage(Track track, String message) {
}
