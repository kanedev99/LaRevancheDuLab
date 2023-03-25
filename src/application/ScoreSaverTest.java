package application;

import java.util.Optional;
import java.util.stream.Stream;

import saving.ScoreDat;
import saving.ScoreSaver;

/**
 * 
 * @author YohannLaou
 *
 */
public class ScoreSaverTest {

	public static void main(String[] args) {
		ScoreSaver scoreSaver = new ScoreSaver();
		ScoreDat dat1 = new ScoreDat("YOHANN", 1088);
		scoreSaver.save(dat1, "NUmero3");
		
		Optional<Stream<ScoreDat>> loading = scoreSaver.loadAll();
		if (loading.isEmpty()) {
			System.out.println("Il n'y a pas de score");
		} else {
			Stream<ScoreDat> scores = loading.get();
			scores.forEach(score -> System.out.println(score));
		}
	}

}
