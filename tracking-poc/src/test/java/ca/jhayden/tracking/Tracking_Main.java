package ca.jhayden.tracking;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import ca.jhayden.tracking.boundary.TrackApi;
import ca.jhayden.tracking.boundary.TrackFactory;
import ca.jhayden.tracking.entity.Track;

public class Tracking_Main {

	public static void main(String[] args) throws IOException {
		var main = new Tracking_Main();
		main.perform();
	}

	private final TrackFactory factory = new TrackFactory();
	private final TrackApi api = new TrackApi();

	public void perform() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("src/test/resources/data.txt"));

		for (String s : lines) {
			System.out.println("");
			System.out.println(s);
			Track t = factory.parse(s);
			System.out.println(t);

			// api.record(t);
		}
	}
}
