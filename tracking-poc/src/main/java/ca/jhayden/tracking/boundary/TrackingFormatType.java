package ca.jhayden.tracking.boundary;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ca.jhayden.tracking.entity.Track;

public enum TrackingFormatType {
	DURATION_DISTANCT_CALORIES {
		@Override
		public String toString(Track track) {
			return null;
		}

		@Override
		public TrackOrMessage parse(TrackFormApi form) {
			try {
				// Duration = text1
				// Distance = text2
				// Calories = text3
				Duration d = Duration.parse(form.getText());
				final Track t = new Track();
				t.setDuration(d);
				t.setFormatType(this);
				t.setMisc(null);
				t.setTrackingTypeCode(form.getTrackingTypeCode());
				t.setValue1(null);
				t.setWhen(form.getWhen());

				return new TrackOrMessage(t, null);
			}
			catch (Exception e) {
				return new TrackOrMessage(null, e.toString());
			}
		}

		@Override
		public Track parseFromHistory(String line) {
			return null;
		}
	},
	SINGLE_DATE_VALUE {
		@Override
		public TrackOrMessage parse(TrackFormApi form) {
			try {
				final LocalDate ld = LocalDate.parse(form.getText());
				final Track t = new Track();

				t.setFormatType(this);
				t.setMisc(ld.toString());
				t.setTrackingTypeCode(form.getTrackingTypeCode());
				t.setValue1(ZERO);
				t.setWhen(form.getWhen());

				return new TrackOrMessage(t, null);
			}
			catch (RuntimeException e) {
				return new TrackOrMessage(null, e.toString());
			}
		}

		@Override
		public String toString(Track track) {
			return String.format("%s [%s] %s", track.getWhen().format(DTF), track.getTrackingTypeCode(),
					track.getMisc());
		}

		@Override
		public Track parseFromHistory(String line) {
			int squareStart = line.indexOf("[");
			int squareEnd = line.indexOf("]");

			String when = line.substring(0, squareStart).trim();
			String type = line.substring(squareStart + 1, squareEnd);
			String misc = line.substring(squareEnd + 1).trim();

			final Track out = new Track();
			out.setWhen(LocalDateTime.parse(when, DTF));
			out.setFormatType(this);
			out.setTrackingTypeCode(type);
			out.setMisc(misc);
			return out;
		}
	}, //
	SINGLE_FLOAT_VALUE {
		@Override
		public TrackOrMessage parse(TrackFormApi form) {
			try {
				final float value = Float.parseFloat(form.getText());
				final Track t = new Track();

				t.setFormatType(this);
				t.setTrackingTypeCode(form.getTrackingTypeCode());
				t.setValue1(value);
				t.setWhen(form.getWhen());

				return new TrackOrMessage(t, null);
			}
			catch (RuntimeException e) {
				return new TrackOrMessage(null, e.toString());
			}
		}

		@Override
		public String toString(Track track) {
			return String.format("%s [%s] %s", track.getWhen().format(DTF), track.getTrackingTypeCode(),
					track.getValue1());
		}

		@Override
		public Track parseFromHistory(String line) {
			int squareStart = line.indexOf("[");
			int squareEnd = line.indexOf("]");

			String when = line.substring(0, squareStart).trim();
			String type = line.substring(squareStart + 1, squareEnd);
			String value = line.substring(squareEnd + 1).trim();

			final Track out = new Track();
			out.setWhen(LocalDateTime.parse(when, DTF));
			out.setFormatType(this);
			out.setTrackingTypeCode(type);
			out.setValue1(Float.parseFloat(value));
			return out;
		}
	}, //
	SINGLE_WHOLE_VALUE {
		@Override
		public TrackOrMessage parse(TrackFormApi form) {
			try {
				final Integer value = Integer.parseInt(form.getText());
				final Track t = new Track();

				t.setFormatType(this);
				t.setTrackingTypeCode(form.getTrackingTypeCode());
				t.setValue1(value.floatValue());
				t.setWhen(form.getWhen());

				return new TrackOrMessage(t, null);
			}
			catch (RuntimeException e) {
				return new TrackOrMessage(null, e.toString());
			}
		}

		@Override
		public String toString(Track track) {
			return String.format("%s [%s] %s", track.getWhen().format(DTF), track.getTrackingTypeCode(),
					track.getValue1().intValue());
		}

		@Override
		public Track parseFromHistory(String line) {
			int squareStart = line.indexOf("[");
			int squareEnd = line.indexOf("]");

			String when = line.substring(0, squareStart).trim();
			String type = line.substring(squareStart + 1, squareEnd);
			String misc = line.substring(squareEnd + 1).trim();

			Integer i = Integer.valueOf(misc);

			final Track out = new Track();
			out.setWhen(LocalDateTime.parse(when, DTF));
			out.setFormatType(this);
			out.setTrackingTypeCode(type);
			out.setValue1(i.floatValue());
			return out;
		}
	},
	TEXT_VALUE {
		@Override
		public TrackOrMessage parse(TrackFormApi form) {
			try {
				final Track t = new Track();
				t.setFormatType(this);
				t.setMisc(form.getText().trim());
				t.setTrackingTypeCode(form.getTrackingTypeCode());
				t.setValue1(ZERO);
				t.setWhen(form.getWhen());

				return new TrackOrMessage(t, null);
			}
			catch (RuntimeException e) {
				return new TrackOrMessage(null, e.toString());
			}
		}

		@Override
		public String toString(Track track) {
			return String.format("%s [%s] %s", track.getWhen().format(DTF), track.getTrackingTypeCode(),
					track.getMisc());
		}

		@Override
		public Track parseFromHistory(String line) {
			int squareStart = line.indexOf("[");
			int squareEnd = line.indexOf("]");

			String when = line.substring(0, squareStart).trim();
			String type = line.substring(squareStart + 1, squareEnd);
			String misc = line.substring(squareEnd + 1).trim();

			final Track out = new Track();
			out.setWhen(LocalDateTime.parse(when, DTF));
			out.setFormatType(this);
			out.setTrackingTypeCode(type);
			out.setMisc(misc);
			return out;
		}
	};

	static final Float ZERO = Float.valueOf(0f);

	static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public abstract String toString(Track track);

	public abstract TrackOrMessage parse(TrackFormApi form);

	public abstract Track parseFromHistory(String line);
}
