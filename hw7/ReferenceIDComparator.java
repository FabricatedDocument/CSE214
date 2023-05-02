/*
 * Name: SukMin Sim
 * ID: 112190914
 * Recitation: 02
 */
package hw7;

import java.util.Comparator;

public class ReferenceIDComparator implements Comparator<NearEarthObject>{

	public int compare(NearEarthObject o1, NearEarthObject o2) {
		return o1.getReferenceID() - o2.getReferenceID();
	}

}
