package ca.jhayden.tracking.swing;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ca.jhayden.tracking.boundary.TrackApi;
import ca.jhayden.tracking.boundary.TrackingTypeInfo;
import ca.jhayden.tracking.swing.simple.SimpleTrackingJFrame;
import ca.jhayden.tracking.swing.simple.SimpleUiSetup;

public class LaunchTrackingSimpleUI implements Runnable {
	public static void main(String[] args) {
		final LaunchTrackingSimpleUI launcher = new LaunchTrackingSimpleUI();
		SwingUtilities.invokeLater(launcher);
	}

	private final TrackApi api;
	private final SimpleTrackingAI ai;
	private final List<TrackingTypeInfo> mostLikely;

	private LaunchTrackingSimpleUI() {
		super();
		api = new TrackApi();
		ai = new SimpleTrackingAI(api);
		mostLikely = ai.getMostLikely();
	}

	@Override
	public void run() {
		SimpleUiSetup setup = new SimpleUiSetup(api, ai, mostLikely);
		SimpleTrackingJFrame jframe = new SimpleTrackingJFrame(setup);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.pack();
		jframe.setVisible(true);
	}
}
