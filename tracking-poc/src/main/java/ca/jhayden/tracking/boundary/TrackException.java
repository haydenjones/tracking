package ca.jhayden.tracking.boundary;

public class TrackException extends Exception {
	private static final long serialVersionUID = -9050038478694463093L;

	public TrackException(Exception e) {
		super(e);
	}
}
