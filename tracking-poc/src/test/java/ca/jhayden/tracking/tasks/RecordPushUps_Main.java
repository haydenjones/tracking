package ca.jhayden.tracking.tasks;

import ca.jhayden.tracking.entity.Track;

public class RecordPushUps_Main implements TrackingServerResponseHandler {

	public static void main(String[] args) {
		var main = new RecordPushUps_Main();
		main.perform();
	}

	public void perform() {
		System.out.println("I want to record:");
		final Track pu20 = new Track();
		pu20.setTrackingTypeCode("PUSH_UPS");
		pu20.setValue1(20f);
		System.out.println(pu20);

		System.out.println("");

		TrackingServer server = new TrackingServer(this);
		server.sendRecord(pu20);
	}

	@Override
	public void handle(TrackingServerResponse response) {
		System.out.println(response);
	}

}
