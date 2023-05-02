package hw4;

public class Intersection {
	private TwoWayRoad[] roads;
	private int lightIndex;
	private int countdownTimer;
	
	/*
	 * Constructor of Intersection object one or more roads should be gotten.
	 * Private variables are also instantiated from here.
	 * 
	 * @throw IllegalArgumentException
	 * 	If initRoads is referring null, exception will be thrown
	 * 	If any TwoWayRoad in initRoads is referring null, exception will be thrown also.
	 * 
	 * @Param initRoads
	 * 	TwoWayRoads in this array will be used in the Intersection object.
	 * 
	 * 
	 */
	public Intersection(TwoWayRoad[] initRoads)
	{
		try {
			if(initRoads == null)
				throw new IllegalArgumentException();

			for(TwoWayRoad unverified : initRoads)
				if(unverified == null)
					throw new IllegalArgumentException();
		}

		catch(IllegalArgumentException iae)
		{
			System.out.println("Invalid intersection info.");
			return;
		}

		lightIndex = 0;
		roads = initRoads;
		countdownTimer = initRoads[0].getGreenTime();
	}

	/*
	 * Returns the number of TwoWayRoad in roads[]
	 * 
	 * @return
	 * 	the length of array roads[].
	 */
	public int getNumRoads()
	{return roads.length;}

	/*
	 * Returns the index of TwoWayRoad which is operating now.
	 * 
	 * @return
	 * 	the index of TwoWayRoad which is in Green light or Left Green light
	 */
	public int getLightIndex()
	{return lightIndex;}
	
	/*
	 * Returns the left Maximum Green time of TwoWayRoad operating now.
	 * 
	 * @return
	 * 	left Maximum Green time.
	 */
	public int getCountdownTimer()
	{return countdownTimer;}
	
	/*
	 * Returns the lightValue of TwoWayRoad which is operating now.
	 * 
	 * @return
	 * 	LightValue of TwoWayRoad currently available
	 */
	public LightValue getCurrentLightValue()
	{return roads[lightIndex].getLightValue();}

	/*
	 * Currently available TwoWayRoad will passes Vehicle object in appropriate lanes.
	 * 
	 * @return
	 * 	Array form of passed Vehicles
	 */
	public Vehicle[] timeStep()
	{
		Vehicle[] passedVehicles;
			
		passedVehicles = roads[lightIndex].proceed(countdownTimer);
		
		if(countdownTimer == roads[lightIndex].getGreenTime() && roads.length > 1)
		{
			if(lightIndex != 0)
				roads[(lightIndex-1) % roads.length].proceed(0);
			
			else
				roads[roads.length-1].proceed(0);
		}
	
		countdownTimer -= 1;
		
		if(countdownTimer == 0 || passedVehicles == null)
		{
			if(roads.length == 1 && countdownTimer > 0)
				return passedVehicles;
			
			lightIndex = (lightIndex+1) % roads.length;
			countdownTimer = roads[lightIndex].getGreenTime();
		}

		return passedVehicles;
	}

	/*
	 * Add a new Vehicle to appropriate road.
	 * 
	 * @Param roadIndex
	 * 	Index of road to get new Vehicle.
	 * 
	 * @Param wayIndex
	 * 	Index of way in road to get new Vehicle.
	 * 
	 * @Param laneIndex
	 * 	Index of lane in way of road to get new Vehicle.
	 * 
	 * @Param newVehicle
	 * 	Valid Vehicle object that should be added.
	 */
	public void enqueueVehicle(int roadIndex, int wayIndex, int laneIndex, Vehicle newVehicle)
	{
		roads[roadIndex].enqueueVehicle(wayIndex, laneIndex, newVehicle);
	}

	/*
	 * This method prints out the clear situation of Intersection to console.
	 */
	public void display()
	{
		for(TwoWayRoad printedRoad : roads)
			System.out.print(printedRoad.toString());
	}
	
	/*
	 * Visualized Intersection object information in String type format.
	 * 
	 * @return
	 * 	String variable which is containing the visualized information of Intersection object
	 */
	public String toString()
	{
		String intersectionInfo = "";
		
		for(TwoWayRoad allRoad : roads)
			intersectionInfo += allRoad.toString();
		
		return intersectionInfo;
	}
}
