package ca.jhayden.tracking.swing;

import java.util.Optional;

import ca.jhayden.tracking.entity.Track;

public interface SimpleHub {
	public abstract void submit(Track track);

	public abstract Optional<Track> getLatest(String code);
}
