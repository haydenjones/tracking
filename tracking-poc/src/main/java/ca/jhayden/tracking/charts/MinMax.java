package ca.jhayden.tracking.charts;

public record MinMax(float min, float max) {
	MinMax with(float f) {
		return new MinMax(Math.min(min, f), Math.max(max, f));
	}
}