package ca.jhayden.tracking.swing;

import java.util.List;

import ca.jhayden.tracking.ai.MostLikelyTask;
import ca.jhayden.tracking.boundary.TrackApi;
import ca.jhayden.tracking.boundary.TrackException;
import ca.jhayden.tracking.boundary.TrackingTypeInfo;
import ca.jhayden.tracking.entity.Track;

public class SimpleTrackingAI {

	private final TrackApi trackApi;

	public SimpleTrackingAI(TrackApi api) {
		super();
		trackApi = api;
	}

	public List<TrackingTypeInfo> getMostLikely() {
		List<TrackingTypeInfo> list = TrackingTypeInfo.EMPTY_LIST;
		List<Track> history = Track.EMPTY_LIST;

		try {
			list = trackApi.getTrackingTypes();
			history = trackApi.getHistory();
		}
		catch (TrackException e) {
			e.printStackTrace();
		}

		MostLikelyTask task = new MostLikelyTask(list, history);
		return task.call();
	}
}
