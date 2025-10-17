package ca.jhayden.tracking.boundary;

import java.time.Duration;
import java.time.LocalDateTime;

import ca.jhayden.tracking.entity.Track;

public class Jog_Test {

	public static void main(String[] args) {
		var main = new Jog_Test();
		main.perform();
	}

	public void perform() {
		Duration d = Duration.ofSeconds(90L);
		System.out.println(d);

		String input = "PT30M";
		Duration x = Duration.parse(input);
		System.out.println(x);

		Track t = new Track();
		t.setFormatType(TrackingFormatType.DURATION_DISTANCT_CALORIES);
		t.setTrackingTypeCode("JOG");
		t.setWhen(LocalDateTime.now());
		t.setDuration(Duration.parse("PT30M"));

		System.out.println(t);
	}
}
