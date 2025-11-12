package ca.jhayden.tracking.swing.simple;

import javax.swing.JButton;

import ca.jhayden.tracking.boundary.TrackingTypeInfo;

public class TrackingJButton extends JButton {
	private static final long serialVersionUID = 2085356104331324287L;

	private final TrackingTypeInfo typeInfo;

	public TrackingJButton(TrackingTypeInfo tti) {
		super(tti.getCode());
		typeInfo = tti;
	}

	public TrackingTypeInfo getTypeInfo() {
		return typeInfo;
	}
}
