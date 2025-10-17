package ca.jhayden.tracking.tasks;

import ca.jhayden.tracking.entity.Track;

public class TrackingServer {
	private final TrackingServerResponseHandler handler;

	public TrackingServer(TrackingServerResponseHandler handler) {
		super();
		this.handler = handler;
	}

	public void sendRecord(Track info) {
		TrackingServerRequest request = null;

		TrackingServerTask task = new TrackingServerTask(request, handler);
		Thread t = new Thread(task);
		t.start();
	}
}
