package ca.jhayden.tracking.swing.simple;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import ca.jhayden.tracking.boundary.TrackFormApi;
import ca.jhayden.tracking.boundary.TrackOrMessage;
import ca.jhayden.tracking.boundary.TrackingFormatType;
import ca.jhayden.tracking.boundary.TrackingTypeInfo;
import ca.jhayden.tracking.entity.Track;
import ca.jhayden.tracking.swing.SimpleHub;

/**
 * 
 */
public class TrackingJPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 5508182128605030434L;

	private final SimpleHub simpleHub;
	private final TrackingTypeInfo typeInfo;
	private final boolean submitOnEnter;

	private final JTextField tf = new JTextField("");
	private final JTextField tf2 = new JTextField("");
	private final JTextField tf3 = new JTextField("");

	public TrackingJPanel(SimpleHub hub, TrackingTypeInfo tti, Track latest, boolean submitOnEnterPressed) {
		super(new GridBagLayout());

		this.submitOnEnter = submitOnEnterPressed;
		this.setBorder(new LineBorder(Color.WHITE, 2, true));
		this.simpleHub = Objects.requireNonNull(hub);
		this.typeInfo = Objects.requireNonNull(tti);

		final Dimension d = new Dimension(120, 20);
		this.tf.setPreferredSize(d);
		this.tf2.setPreferredSize(d);
		this.tf3.setPreferredSize(d);

		setup(this.typeInfo, latest);
	}

	void setup(TrackingTypeInfo tti, Track latest) {
		final TrackingFormatType fmt = tti.getFormatType();

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(new JLabel(tti.toString()), gbc);

		switch (fmt) {
			case DURATION_DISTANCT_CALORIES -> {
				gbc.gridy = 1;
				gbc.gridx = 0;
				gbc.anchor = GridBagConstraints.EAST;
				this.add(new JLabel("Duration:"), gbc);
				gbc.gridx = 1;
				gbc.anchor = GridBagConstraints.WEST;
				this.add(tf, gbc);

				gbc.gridy = 2;
				gbc.gridx = 0;
				gbc.anchor = GridBagConstraints.EAST;
				this.add(new JLabel("Distance:"), gbc);
				gbc.gridx = 1;
				gbc.anchor = GridBagConstraints.WEST;
				this.add(tf2, gbc);

				gbc.gridy = 3;
				gbc.gridx = 0;
				gbc.anchor = GridBagConstraints.EAST;
				this.add(new JLabel("Calories:"), gbc);
				gbc.gridx = 1;
				gbc.anchor = GridBagConstraints.WEST;
				this.add(tf3, gbc);
			}
			case SINGLE_DATE_VALUE, TEXT_VALUE -> {
				gbc.gridy = 1;
				gbc.fill = GridBagConstraints.HORIZONTAL;

				this.add(tf, gbc);

				// Set up Listeners
				if (this.submitOnEnter) {
					tf.addActionListener(this);
				}
			}
			case SINGLE_FLOAT_VALUE -> {
				gbc.gridy = 1;
				gbc.fill = GridBagConstraints.HORIZONTAL;

				if (latest != null) {
					tf.setText("" + latest.getValue1());
				}
				this.add(tf, gbc);

				// Set up Listeners
				if (this.submitOnEnter) {
					tf.addActionListener(this);
				}
			}
			case SINGLE_WHOLE_VALUE -> {
				gbc.gridy = 1;
				gbc.fill = GridBagConstraints.HORIZONTAL;

				if (latest != null) {
					tf.setText("" + latest.getValue1().intValue());
				}
				this.add(tf, gbc);

				// Set up Listeners
				if (this.submitOnEnter) {
					tf.addActionListener(this);
				}
			}
		}

		// Update with latest values
		if (latest != null) {
			loadValues(tti, latest);
		}
	}

	void loadValues(TrackingTypeInfo tti, Track latest) {
		switch (tti.getFormatType()) {
			default:
				break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e);
		TrackingFormatType fmt = typeInfo.getFormatType();

		TrackFormApi tfa = createForm();
		TrackOrMessage tom = fmt.parse(tfa);

		final Track track = tom.track();
		if (track != null) {
			simpleHub.submit(track);
		}
		else {
			System.out.println(tom.message());
		}
	}

	public TrackFormApi createForm() {
		return new TrackFormInfo(this.typeInfo, tf.getText(), tf2.getText(), tf3.getText());
	}
}

class TrackFormInfo implements TrackFormApi {
	private final LocalDateTime when = LocalDateTime.now();
	private final TrackingFormatType formatType;
	private final String trackingTypeCode;
	private final String text;
	private final String text2;
	private final String text3;

	TrackFormInfo(TrackingTypeInfo tti, String raw, String raw2, String raw3) {
		super();
		formatType = tti.getFormatType();
		trackingTypeCode = tti.getCode();
		text = raw;
		text2 = raw2;
		text3 = raw3;
	}

	@Override
	public LocalDateTime getWhen() {
		return when;
	}

	@Override
	public String getTrackingTypeCode() {
		return trackingTypeCode;
	}

	@Override
	public String getText1() {
		return text;
	}

	@Override
	public String getText2() {
		return text2;
	}

	@Override
	public String getText3() {
		return text3;
	}

	@Override
	public TrackingFormatType getFormatType() {
		return formatType;
	}
}
