package ca.jhayden.tracking.boundary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.jhayden.tracking.entity.Track;
import ca.jhayden.tracking.tasks.TrackingServerResponse;

public class TrackApi {
	static final Path HISTORY_PATH = Paths.get("/Users/hjones/Downloads/history.txt");

	public List<TrackingTypeInfo> getTrackingTypes() {
		return Arrays.asList(SimpleTrackingType.values());
	}

	public TrackingServerResponse record(Track track) throws TrackException {
		try {
			final Path historyPath = HISTORY_PATH;

			if (Files.notExists(historyPath)) {
				System.out.println("Create: " + historyPath);
				Files.createFile(historyPath);
			}
			System.out.println(historyPath + " " + Files.exists(historyPath));

			final String t = track.getFormatType().toString(track);
			Files.write(historyPath, Arrays.asList(t), StandardOpenOption.APPEND);
			System.out.println("Wrote");
			return null;
		}
		catch (Exception e) {
			throw new TrackException(e);
		}
	}

	public List<ReportInfo> getReports() throws TrackException {
		List<ReportInfo> list = new ArrayList<>();

		for (SimpleReport sr : SimpleReport.values()) {
			list.add(sr);
		}

		return list;
	}

	public List<Track> getHistory() throws TrackException {
		final var history = new ArrayList<Track>();

		final Map<String, TrackingTypeInfo> mapTypes = new HashMap<>();
		final List<TrackingTypeInfo> trackingTypes = this.getTrackingTypes();
		for (TrackingTypeInfo tti : trackingTypes) {
			mapTypes.put("[" + tti.getCode() + "]", tti);
		}

		try {
			List<String> lines = Files.readAllLines(HISTORY_PATH);
			for (String line : lines) {
				for (Map.Entry<String, TrackingTypeInfo> entry : mapTypes.entrySet()) {
					if (line.contains(entry.getKey())) {
						final TrackingTypeInfo tti = entry.getValue();
						final TrackingFormatType fmt = tti.getFormatType();
						final Track track = fmt.parseFromHistory(line);
						if (track != null) {
							history.add(track);
						}
					}
				}
			}
		}
		catch (IOException e) {
			throw new TrackException(e);
		}

		return history;
	}
}

enum SimpleReport implements ReportInfo {
	WEIGHT; //
}

enum SimpleTrackingType implements TrackingTypeInfo {
	DATE_OF_BIRTH(TrackingFormatType.SINGLE_DATE_VALUE, RecordFrequency.ONCE), //
	HEIGHT_CM(TrackingFormatType.SINGLE_WHOLE_VALUE, RecordFrequency.ONCE), //
	JOG(TrackingFormatType.DURATION_DISTANCT_CALORIES, RecordFrequency.DAILY), //
	NAME(TrackingFormatType.TEXT_VALUE, RecordFrequency.ONCE), //
	PUSH_UPS(TrackingFormatType.SINGLE_WHOLE_VALUE, RecordFrequency.FREQUENT), //
	WALK(TrackingFormatType.DURATION_DISTANCT_CALORIES, RecordFrequency.DAILY), //
	WEIGHT_LBS(TrackingFormatType.SINGLE_FLOAT_VALUE, RecordFrequency.DAILY), //
	ZRX(TrackingFormatType.DURATION_DISTANCT_CALORIES, RecordFrequency.DAILY); //

	private final TrackingFormatType formatType;
	private final RecordFrequency recordFrequency;

	SimpleTrackingType(TrackingFormatType ft, RecordFrequency rf) {
		formatType = ft;
		recordFrequency = rf;
	}

	@Override
	public String getCode() {
		return name();
	}

	@Override
	public TrackingFormatType getFormatType() {
		return formatType;
	}

	@Override
	public RecordFrequency getRecordFrequency() {
		return recordFrequency;
	}
}