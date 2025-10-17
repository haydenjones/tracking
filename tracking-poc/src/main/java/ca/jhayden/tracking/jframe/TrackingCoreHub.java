package ca.jhayden.tracking.jframe;

import ca.jhayden.tracking.entity.Track;

public interface TrackingCoreHub {

	public void post(Track t);

	public void initialize();

}
