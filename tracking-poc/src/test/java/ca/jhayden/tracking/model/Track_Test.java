package ca.jhayden.tracking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ca.jhayden.tracking.entity.Track;

public class Track_Test {
	@Test
	public void works() {
		Track track = Track.of("NAME", "Hayden");

		assertEquals("NAME", track.getTrackingTypeCode());
	}
}
