package ca.jhayden.tracking.ai;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import ca.jhayden.tracking.boundary.RecordFrequency;
import ca.jhayden.tracking.boundary.TrackingTypeInfo;
import ca.jhayden.tracking.entity.Track;

public record MostLikelyTask(List<TrackingTypeInfo> all, List<Track> history) {
	public List<TrackingTypeInfo> call() {
		this.history.sort(Track.SortBy.WHEN_DESC);

		final Map<String, TrackingTypeInfo> mapTypes = new TreeMap<>();
		for (TrackingTypeInfo tti : all) {
			mapTypes.put(tti.getCode(), tti);
		}

		Comparator<TrackingTypeInfo> sorter = new SortTrackingTypes(history);

		final Set<String> doneEver = new TreeSet<>();
		final Set<String> doneToday = new TreeSet<>();

		final LocalDate today = LocalDate.now();

		for (Track t : this.history) {
			doneEver.add(t.getTrackingTypeCode());
			if (today.isEqual(t.getWhen().toLocalDate())) {
				doneToday.add(t.getTrackingTypeCode());
			}
		}

		Set<String> keys = new TreeSet<>(mapTypes.keySet());

		for (String key : keys) {
			TrackingTypeInfo tti = mapTypes.get(key);
			if (tti == null) {
				continue;
			}
			if (tti.getRecordFrequency() == RecordFrequency.ONCE) {
				if (doneEver.contains(tti.getCode())) {
					mapTypes.remove(key);
					System.out.println("Removed: " + key);
				}
			}
			else if (tti.getRecordFrequency() == RecordFrequency.DAILY) {
				if (doneToday.contains(tti.getCode())) {
					mapTypes.remove(key);
					System.out.println("Removed Daily: " + key);
				}
			}
		}

		final List<TrackingTypeInfo> out = new ArrayList<>();
		out.addAll(mapTypes.values());

		out.sort(sorter);

		return out;
	}
}

class SortTrackingTypes implements Comparator<TrackingTypeInfo> {
	private final List<Track> history;

	public SortTrackingTypes(List<Track> items) {
		super();
		history = items;
	}

	@Override
	public int compare(TrackingTypeInfo o1, TrackingTypeInfo o2) {
		int c = o1.getRecordFrequency().compareTo(o2.getRecordFrequency());
		if (c != 0) {
			return c;
		}

		return o1.getCode().compareTo(o2.getCode());
	}

}