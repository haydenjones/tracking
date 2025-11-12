package ca.jhayden.tracking.boundary;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ca.jhayden.tracking.entity.Track;

public enum TrackingFormatType {
	DURATION_DISTANCT_CALORIES {
		@Override
		public String toString(Track track) {
			return String.format("%s [%s] %s KM=%s CAL=%s", track.getWhen().format(DTF), track.getTrackingTypeCode(),
					track.getDuration(), track.getValue1(), track.getMisc());
		}

		@Override
		public TrackOrMessage parse(TrackFormApi form) {
			try {
				// Duration = text1
				// Distance = text2
				// Calories = text3
				Duration d = Duration.parse(form.getText1());
				final Track t = new Track();
				t.setDuration(d);
				t.setFormatType(this);

				// Distance
				final float kms = Float.parseFloat(form.getText2());
				t.setValue1(kms);

				// Calcories
				final BigDecimal calories = new BigDecimal(form.getText3());
				t.setMisc(calories.toString());

				t.setTrackingTypeCode(form.getTrackingTypeCode());
				t.setWhen(form.getWhen());

				return new TrackOrMessage(t, null);
			}
			catch (Exception e) {
				return new TrackOrMessage(null, e.toString());
			}
		}

		@Override
		public Track parseFromHistory(String line) {
			// "%s [%s] %s KM=%s CAL=%s"
			int squareStart = line.indexOf("[");
			int squareEnd = line.indexOf("]");

			int kmStart = line.indexOf("KM=");
			int calStart = line.indexOf("CAL=");

			String when = line.substring(0, squareStart).trim();
			String type = line.substring(squareStart + 1, squareEnd);

			final Track out = new Track();
			out.setWhen(LocalDateTime.parse(when, DTF));
			out.setFormatType(this);
			out.setTrackingTypeCode(type);

			String value = line.substring(squareEnd + 1, kmStart).trim();
			Duration d = Duration.parse(value);
			out.setDuration(d);

			value = line.substring(kmStart + 3, calStart).trim();
			out.setValue1(Float.parseFloat(value));

			out.setMisc(line.substring(calStart + 4).trim());

			return out;
		}
	},
	SINGLE_DATE_VALUE {
		@Override
		public TrackOrMessage parse(TrackFormApi form) {
			try {
				final LocalDate ld = LocalDate.parse(form.getText1());
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
				final float value = Float.parseFloat(form.getText1());
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
				final Integer value = Integer.parseInt(form.getText1());
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
				t.setMisc(form.getText1().trim());
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
