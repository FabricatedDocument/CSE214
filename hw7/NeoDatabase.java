package hw7;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class NeoDatabase implements Collection<NearEarthObject>{
	public static final String API_KEY = "UQlFzDfMg4I4Qnun99fesLhLIx3ASPYYAdr0kbug";
	public static final String API_ROOT = "https://api.nasa.gov/neo/rest/v1/neo/browse?";
	private ArrayList<NearEarthObject> planetDB;
	
	/*
	 * Just instantiate database container.
	 */
	public NeoDatabase()
	{
		planetDB = new ArrayList<NearEarthObject>();
	}
	
	/*
	 * Generate a URL to access on JSON file
	 * 
	 * @Param pageNumber
	 * 	Page of the JSON file to access
	 * 	Minimum is 0, Maximum is 715
	 * 
	 * @Return
	 * 	Generated URL address to access to API
	 */
	public String buildQueryURL(int pageNumber)
	{
		try {
			if(pageNumber < 0 || pageNumber > 715)
				throw new IllegalArgumentException();
		}

		catch(IllegalArgumentException iae)
		{System.out.println("Invalid page.");}

		return API_ROOT+ "page=" + pageNumber + "&api_key=" + API_KEY;
	}

	/*
	 * Parse JSON file and save each NearEarthObjects' information to the database.
	 * 
	 * @Param url
	 * 	Address to get JSON file
	 */
	public void addAll(String url)
	{
		try
		{
			URL getRequest = new URL(url);
			JSONTokener tokener = new JSONTokener(getRequest.openStream());
			JSONObject root = new JSONObject(tokener);

			int referenceID;
			String name;
			double absoluteMagnitude;
			double averageDiameter;
			boolean isDangerous;
			String closestApproachDate;
			double missDistance;
			String orbitingBody;

			for(int planetIndex = 0; planetIndex < 20; planetIndex++)
			{
				String info = root.getJSONArray("near_earth_objects").get(planetIndex).toString();

				referenceID = Integer.parseInt(info.substring(info.indexOf("neo_reference_id")+19, 
						info.indexOf("neo_reference_id")+26));

				name = info.substring(info.indexOf("\"name\"")+8, 
						info.indexOf("absolute")-3);

				absoluteMagnitude = Double.parseDouble(info.substring(info.indexOf("magnitude")+13,
						info.indexOf("links")-2));

				String diameterInfo = info.substring(info.indexOf("estimated_diameter"));
				diameterInfo = diameterInfo.substring(diameterInfo.indexOf("kilometers"));
				
				double minimumDia = Double.parseDouble(diameterInfo.substring(
						diameterInfo.indexOf("_min")+6,
						diameterInfo.indexOf("min")+13)
						.replaceAll("}", "").replaceAll("\"", "").replaceAll(",", ""));
				
				double maximumDia = Double.parseDouble(diameterInfo.substring(
						diameterInfo.indexOf("_max")+6,
						diameterInfo.indexOf("_max")+13).replaceAll("}", ""));
				
				averageDiameter = (minimumDia+maximumDia)/2;

				isDangerous = Boolean.parseBoolean(info.substring(info.indexOf("hazardous")+20,
						info.indexOf("is_sentry")-2));

				closestApproachDate = info.substring(info.indexOf("close_approach_date")+22,
						info.indexOf("close_approach_date")+32);
				
				String missDisInfo = info.substring(info.indexOf("miss_distance"));
				
				missDistance = Double.parseDouble(missDisInfo.substring
						(missDisInfo.indexOf("kilometers")+13,
						missDisInfo.indexOf("kilometers")+23));
				
				orbitingBody = info.substring(info.indexOf("orbiting_body")+16,
						info.indexOf("close_approach_date")-3);
				
				NearEarthObject loadedPlanet = 
						new NearEarthObject(referenceID, name, absoluteMagnitude,
								averageDiameter, isDangerous, closestApproachDate,
								missDistance, orbitingBody);
				
				planetDB.add(loadedPlanet);
			}
		}

		catch(MalformedURLException mue)
		{
			System.out.println("MalFormed URL Exception");
		}

		catch(IOException ioe)
		{
			System.out.println("IOException");
		}

		catch(JSONException je)
		{
			System.out.println("Json Exception");
		}

		catch(NoClassDefFoundError ncd)
		{
			System.out.println("No Class Def Found Error");
		}

	}
	
	/*
	 * Sort the NearEarthObjects in some different criteria
	 * 
	 * @Param standard
	 * 	Criterion to sort objects
	 */
	public void sort(String standard)
	{
		if(standard.equals("ref"))
			Collections.sort(planetDB, new ReferenceIDComparator());
		
		else if(standard.equals("mis"))
			Collections.sort(planetDB, new MissDistanceComparator());
		
		else if(standard.equals("dia"))
			Collections.sort(planetDB, new DiameterComparator());
		
		else if(standard.equals("date"))
			Collections.sort(planetDB, new ApproachDateComparator());
	}

	/*
	 * Print out all NearEarthObjects in the database
	 */
	public void printTable()
	{
		System.out.println("  ID   |           Name            | Mag. | Diameter | Danger | Close Date | Miss Dist | Orbits\n"
				+"=".repeat(97));
		for(NearEarthObject planets:planetDB)
		{
			System.out.println(planets.toString());
		}
	}

	/* ***REQUIRED METHODS OVERRIDING BELOW*** */
	@Override
	public int size() {
		return planetDB.size();
	}

	@Override
	public boolean isEmpty() {
		return planetDB.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return planetDB.contains(o);
	}

	@Override
	public Iterator<NearEarthObject> iterator() {
		return planetDB.iterator();
	}

	@Override
	public Object[] toArray() {
		return planetDB.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return planetDB.toArray(a);
	}

	@Override
	public boolean add(NearEarthObject e) {
		return planetDB.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return planetDB.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return planetDB.containsAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return planetDB.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		planetDB.retainAll(c);
		return false;
	}

	@Override
	public void clear() {
		planetDB.clear();
	}

	@Override
	public boolean addAll(Collection<? extends NearEarthObject> c) {
		return planetDB.addAll(c);
	}
}
