package hw7;

import java.util.Comparator;

public class DiameterComparator implements Comparator<NearEarthObject>{

	public int compare(NearEarthObject o1, NearEarthObject o2) {
		if(o1.getAverageDiameter() - o2.getAverageDiameter() > 0)
			return 1;
		
		else if(o1.getAverageDiameter() - o2.getAverageDiameter() == 0)
			return 0;
		
		else
			return -1;
	}
	
}
