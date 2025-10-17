package ca.jhayden.tracking.jframe;

import javax.swing.JFrame;

import ca.jhayden.tracking.boundary.TrackApi;
import ca.jhayden.tracking.boundary.TrackException;
import ca.jhayden.tracking.entity.Track;

public class TrackingJFrame extends JFrame {
	private static final long serialVersionUID = -5245337258899126508L;

	private final TrackingCore core = new TrackingCore();
	private final TrackingMainPanel mainPanel = new TrackingMainPanel(core);

	public TrackingJFrame() {
		super();
		this.getContentPane().add(mainPanel);

		core.initialize();
	}
}

class TrackingCore implements TrackingCoreHub {
	private final TrackApi api = new TrackApi();

	@Override
	public void post(final Track track) {
		System.out.println("Post: " + track);

		Runnable run = () -> {
			try {
				api.record(track);
				System.out.println("Appended");
			}
			catch (TrackException e) {
				e.printStackTrace();
			}
		};

		Thread t = new Thread(run);
		t.start();
	}

	@Override
	public void initialize() {

	}
}
