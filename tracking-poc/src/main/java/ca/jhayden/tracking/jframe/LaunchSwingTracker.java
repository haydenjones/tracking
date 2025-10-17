package ca.jhayden.tracking.jframe;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class LaunchSwingTracker implements Runnable {
	public static void main(String[] args) {
		final var launcher = new LaunchSwingTracker();
		SwingUtilities.invokeLater(launcher);
	}

	@Override
	public void run() {
		TrackingJFrame tjf = new TrackingJFrame();
		tjf.setTitle("Tracking");
		tjf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tjf.pack();
		tjf.setVisible(true);
	}
}
