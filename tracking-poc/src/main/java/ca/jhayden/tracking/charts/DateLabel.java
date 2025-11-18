package ca.jhayden.tracking.charts;

import java.time.LocalDate;

public record DateLabel(LocalDate when) implements Comparable<DateLabel> {

	@Override
	public int compareTo(DateLabel that) {
		return this.when.compareTo(that.when);
	}

	@Override
	public String toString() {
		return when.getMonthValue() + "/" + when.getDayOfMonth();
	}
}
