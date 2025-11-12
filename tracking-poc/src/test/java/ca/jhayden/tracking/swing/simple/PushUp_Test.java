package ca.jhayden.tracking.swing.simple;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ca.jhayden.tracking.boundary.RecordFrequency;
import ca.jhayden.tracking.boundary.TrackFormApi;
import ca.jhayden.tracking.boundary.TrackingFormatType;
import ca.jhayden.tracking.boundary.TrackingTypeInfo;
import ca.jhayden.tracking.entity.Track;
import ca.jhayden.tracking.swing.SimpleHub;

/**
 * When we create a Push Ups panel, we want the panel to be pre-populated with
 * the last number of push ups done.
 */
public class PushUp_Test implements SimpleHub, TrackingTypeInfo {
	static final String EXPECTING = "30";

	@Test
	public void works() {
		TrackingTypeInfo tti = this;
		Track latest = this.getLatest(tti.getCode()).orElseThrow();
		TrackingJPanel panel = new TrackingJPanel(this, tti, latest, false);

		TrackFormApi actual = panel.createForm();
		Assertions.assertEquals(EXPECTING, actual.getText1(), "# of push ups");
	}

	@Override
	public void submit(Track track) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<Track> getLatest(String code) {
		Track t = new Track();
		t.setValue1(Float.parseFloat(EXPECTING));
		return Optional.of(t);
	}

	@Override
	public String getCode() {
		return "PUSH_UPS";
	}

	@Override
	public TrackingFormatType getFormatType() {
		return TrackingFormatType.SINGLE_WHOLE_VALUE;
	}

	@Override
	public RecordFrequency getRecordFrequency() {
		return RecordFrequency.FREQUENT;
	}
}
