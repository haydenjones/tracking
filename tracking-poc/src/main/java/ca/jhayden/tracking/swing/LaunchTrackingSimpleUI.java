package ca.jhayden.tracking.swing;

import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ca.jhayden.tracking.boundary.TrackApi;
import ca.jhayden.tracking.boundary.TrackException;
import ca.jhayden.tracking.swing.simple.SimpleTrackingJFrame;
import ca.jhayden.tracking.swing.simple.SimpleUiSetup;

public class LaunchTrackingSimpleUI implements Runnable {
	public static void main(String[] args) throws TrackException {
		final LaunchTrackingSimpleUI launcher = new LaunchTrackingSimpleUI();
		launcher.initialize();

		SwingUtilities.invokeLater(launcher);
	}

	private final SimpleTrackingAI ai;
	private SimpleUiSetup setup = null;

	private LaunchTrackingSimpleUI() {
		super();
		ai = new SimpleTrackingAI(new TrackApi());
	}

	public void initialize() throws TrackException {
		setup = makeSetup();
	}

	SimpleUiSetup makeSetup() throws TrackException {
		ai.load();
		return ai.computeSetup();
	}

	@Override
	public void run() {
		Objects.requireNonNull(setup, "Need to call initialize first!");
		SimpleTrackingJFrame jframe = new SimpleTrackingJFrame(setup);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.pack();
		jframe.setVisible(true);
	}
}
