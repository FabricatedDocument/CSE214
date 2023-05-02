package hw7;

import java.util.Comparator;

public class ApproachDateComparator implements Comparator<NearEarthObject>{

	public int compare(NearEarthObject o1, NearEarthObject o2) {
		return (int)(o1.getClosestApproachDate().compareTo(o2.getClosestApproachDate()));
	}
}
