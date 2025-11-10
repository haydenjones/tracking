package ca.jhayden.tracking.swing.simple;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ca.jhayden.tracking.boundary.TrackFormApi;
import ca.jhayden.tracking.boundary.TrackOrMessage;
import ca.jhayden.tracking.boundary.TrackingTypeInfo;
import ca.jhayden.tracking.entity.Track;
import ca.jhayden.tracking.jframe.TrackingJButton;
import ca.jhayden.tracking.swing.SimpleHub;
import ca.jhayden.tracking.swing.simple.inspire.InspireJPanel;
import ca.jhayden.tracking.swing.simple.reports.ReportsJPanel;

public class SimpleMainJPanel extends JPanel implements ActionListener {

	static DefaultComboBoxModel<TrackingTypeInfo> toArray(Collection<TrackingTypeInfo> list) {
		TrackingTypeInfo[] array = new TrackingTypeInfo[list.size()];
		list.toArray(array);
		DefaultComboBoxModel<TrackingTypeInfo> model = new DefaultComboBoxModel<TrackingTypeInfo>(array);
		return model;
	}

	private static final long serialVersionUID = -1836741894450528822L;

	private final JComboBox<TrackingTypeInfo> cbMenu;
	private final JButton buttonMenu = new JButton("Menu");
	private final ReportsJPanel reportsPanel;
	private final InspireJPanel inspirePanel;
	private final SimpleHub simpleHub;

	public SimpleMainJPanel(SimpleHub hub, SimpleUiSetup setup) {
		super(new GridBagLayout());

		simpleHub = hub;

		cbMenu = new JComboBox<TrackingTypeInfo>(toArray(setup.all()));
		cbMenu.addActionListener(this);

		reportsPanel = new ReportsJPanel(hub);
		inspirePanel = new InspireJPanel();

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridy = 0;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		this.add(cbMenu, gbc);

		gbc.gridx = 2;
		this.add(buttonMenu, gbc);

		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		this.addButton(setup.expecting(), 1, gbc);
		gbc.gridx = 1;
		this.addButton(setup.expecting(), 2, gbc);

		gbc.gridx = 2;
		this.addButton(setup.expecting(), 3, gbc);

		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.addPanel(setup.expecting(), 0, gbc, hub);

		gbc.gridy = 3;
		this.add(reportsPanel, gbc);

		gbc.gridy = 4;
		this.add(inspirePanel, gbc);
	}

	void addButton(List<TrackingTypeInfo> list, int index, GridBagConstraints gbc) {
		assert index >= 0;

		if (list.size() <= index) {
			return;
		}

		final TrackingTypeInfo tti = list.get(index);
		JButton jb = new TrackingJButton(tti);
		this.add(jb, gbc);
		jb.addActionListener(this);
	}

	void addPanel(List<TrackingTypeInfo> list, int index, GridBagConstraints gbc, SimpleHub hub) {
		assert index >= 0;

		if (list.size() <= index) {
			return;
		}

		final TrackingTypeInfo tti = list.get(index);
		Optional<Track> optLatest = simpleHub.getLatest(tti.getCode());
		JPanel jp = new TrackingJPanel(hub, tti, optLatest.orElse(null), true);
		this.add(jp, gbc);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof TrackingJButton tjb) {
			System.out.println(tjb);
			TrackingTypeInfo tti = tjb.getTypeInfo();
			doPopup(tti);
		}
		else if (e.getSource() == this.cbMenu) {
			// Do Something...
			Object o = cbMenu.getSelectedItem();
			if (o instanceof TrackingTypeInfo tti) {
				doPopup(tti);
			}
			else {
				System.out.println(o);
			}
		}
		else {
			System.out.println(e.getSource());
		}
	}

	void doPopup(TrackingTypeInfo tti) {
		Optional<Track> latest = this.simpleHub.getLatest(tti.getCode());
		final TrackingJPanel panel = new TrackingJPanel(simpleHub, tti, latest.orElse(null), false);
		int option = JOptionPane.showConfirmDialog(this, panel, tti.toString(), JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (option == JOptionPane.OK_OPTION) {
			TrackFormApi form = panel.createForm();
			TrackOrMessage tom = tti.getFormatType().parse(form);
			Track track = tom.track();
			if (track != null) {
				simpleHub.submit(track);
				System.out.println("Recorded.");
			}
			else {
				String message = tom.message();
				System.out.println(message);
			}
		}
	}
}
