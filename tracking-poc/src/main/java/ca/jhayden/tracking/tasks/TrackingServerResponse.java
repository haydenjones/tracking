package ca.jhayden.tracking.tasks;

import ca.jhayden.tracking.boundary.TrackException;

public class TrackingServerResponse {

	public static final TrackingServerResponse ITEM_ADDED = new TrackingServerResponse();

	public static TrackingServerResponse of(TrackingServerRequest request, TrackException te) {
		return new TrackingServerResponse();
	}

	private TrackingServerResponse() {
		super();
	}
}
