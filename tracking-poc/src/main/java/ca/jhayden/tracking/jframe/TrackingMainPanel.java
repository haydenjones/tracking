package ca.jhayden.tracking.jframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import ca.jhayden.tracking.boundary.TrackingTypeInfo;

public class TrackingMainPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 556362795633302945L;

	private final List<TrackingJButton> buttons = new ArrayList<>();

	private final TrackingCoreHub hub;

	public TrackingMainPanel(TrackingCoreHub trackingHub) {
		super();
		hub = trackingHub;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source instanceof TrackingJButton tjb) {
			TrackingTask task = new TrackingTask(tjb.getTypeInfo(), "");
			Thread t = new Thread(task);
			t.start();
		}
	}
}

class TrackingTask implements Runnable {
	public TrackingTask(TrackingTypeInfo typeInfo, String defaultValue) {
		super();
	}

	@Override
	public void run() {

	}
}