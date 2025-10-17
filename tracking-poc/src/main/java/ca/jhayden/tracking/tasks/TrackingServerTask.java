package ca.jhayden.tracking.tasks;

import ca.jhayden.tracking.boundary.TrackApi;
import ca.jhayden.tracking.boundary.TrackException;
import ca.jhayden.tracking.entity.Track;

public class TrackingServerTask implements Runnable {
	private final TrackApi api = new TrackApi();

	private final TrackingServerRequest request;
	private final TrackingServerResponseHandler handler;

	public TrackingServerTask(TrackingServerRequest request, TrackingServerResponseHandler handler) {
		super();
		this.request = request;
		this.handler = handler;
	}

	@Override
	public void run() {
		final Track track = request.getTrack();

		TrackingServerResponse response = null;

		try {
			response = api.record(track);
		}
		catch (TrackException te) {
			response = TrackingServerResponse.of(request, te);
		}

		handler.handle(response);
	}

}
