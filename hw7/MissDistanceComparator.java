/*
 * Name: SukMin Sim
 * ID: 112190914
 * Recitation: 02
 */
package hw7;

import java.util.Comparator;

public class MissDistanceComparator implements Comparator<NearEarthObject>{

	public int compare(NearEarthObject o1, NearEarthObject o2) {
		return (int)(o1.getMissDistance() - o2.getMissDistance());
	}
}
