package ca.jhayden.tracking.swing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.jhayden.tracking.ai.MostLikelyTask;
import ca.jhayden.tracking.boundary.TrackApi;
import ca.jhayden.tracking.boundary.TrackException;
import ca.jhayden.tracking.boundary.TrackingTypeInfo;
import ca.jhayden.tracking.entity.Track;
import ca.jhayden.tracking.swing.simple.SimpleUiSetup;

public class SimpleTrackingAI {

	private Map<String, TrackingTypeInfo> allTypesMap = new HashMap<>();
	private List<Track> history = Track.EMPTY_LIST;
	private Map<String, Track> latestOfType = new HashMap<>();

	private final TrackApi trackApi;

	public SimpleTrackingAI(TrackApi api) {
		super();
		trackApi = api;
	}

	public void load() throws TrackException {
		List<TrackingTypeInfo> allTypes = trackApi.getTrackingTypes();
		for (TrackingTypeInfo tti : allTypes) {
			allTypesMap.put(tti.getCode(), tti);
		}

		history = trackApi.getHistory();

		history.sort(Track.SortBy.WHEN_DESC);

		for (Track t : history) {
			String code = t.getTrackingTypeCode();
			latestOfType.putIfAbsent(code, t);
		}
	}

	public List<TrackingTypeInfo> getMostLikely() {
		MostLikelyTask task = new MostLikelyTask(allTypesMap.values(), history);
		return task.call();
	}

	public SimpleUiSetup computeSetup() {
		List<TrackingTypeInfo> mostLikely = this.getMostLikely();
		return new SimpleUiSetup(this, mostLikely, allTypesMap.values());
	}

	public void record(Track track) throws TrackException {
		trackApi.record(track);
	}
}
