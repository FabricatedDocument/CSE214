/*
 * Name: SukMin Sim
 * ID: 112190914
 * Recitation: 02
 */
package hw4;

enum LightValue
{GREEN, RED, LEFT_SIGNAL}

public class TwoWayRoad {
	public static int FORWARD_WAY = 0;
	public static int BACKWARD_WAY = 1;
	public static int NUM_WAYS = 2;
	
	public static int LEFT_LANE = 0;
	public static int MIDDLE_LANE = 1;
	public static int RIGHT_LANE = 2;
	public static int NUM_LANES = 3;
	
	private String name;
	private int greenTime;
	private int leftSignalGreenTime;
	private VehicleQueue[][] lanes = new VehicleQueue[NUM_WAYS][NUM_LANES];
	private LightValue lightValue;
	
	/*
	 * The constructor method of TwoWayRoad
	 * Uninstantiated private values will be instantiated here.
	 * 
	 * @Param initName
	 * 	Name of this TwoWayRoad object.
	 * 
	 * @Param initGreenTime
	 * 	'Maximum' value of time allowing Vehicles can go across the Road.
	 * 	
	 * @throw IllegalArgumentException
	 * 	If initGreenTime < 1 , exception will be thrown.
	 * 	If initName is null or it's length is under 1, exception is thrown.
	 */
	public TwoWayRoad(String initName, int initGreenTime)
	{
		try
		{
			if(initGreenTime < 1)
				throw new IllegalArgumentException();
			
			else if(initName == null)
				throw new IllegalArgumentException();
			
			else if(initName.length() < 1)
				throw new IllegalArgumentException();
		}
		
		catch(IllegalArgumentException iae)
		{
			System.out.println("Invalid TwoWayRoad Info");
			return;
		}
		
		lightValue = LightValue.RED;
		name = initName;
		greenTime = initGreenTime;
		leftSignalGreenTime = initGreenTime/NUM_LANES;
		
		for(int wayIndex = 0; wayIndex < TwoWayRoad.NUM_WAYS; wayIndex++)
			for(int laneIndex = 0; laneIndex < TwoWayRoad.NUM_LANES; laneIndex++)
				lanes[wayIndex][laneIndex] = new VehicleQueue();
	}
	
	/*
	 * Returns the maximum duration of Vehicle passes.
	 * 
	 * @return
	 * 	the value of greenTime.
	 */
	public int getGreenTime()
	{return greenTime;}
	
	/*
	 * Returns the light value this TwoWayRoad object has now.
	 * 
	 * @return
	 * 	lightValue of this TwoWayObject
	 */
	public LightValue getLightValue()
	{return lightValue;}
	
	/*
	 * Returns the name of this TwoWayRoad object.
	 * 
	 * @return
	 * 	Name of this TwoWayObject
	 */
	public String getName()
	{return name;}
	
	/*
	 * Let Vehicle depart or passes through if each lanes are being allowed to pass their Vehicles.
	 * In case of empty lanes, lightValue could be changed to LEFT_GREEN or RED.
	 * In case of 0 value of greenTime, lightValue is changed to RED.
	 * Only one Vehicle can depart from each lanes.
	 * 
	 * @Param timerVal
	 * 	timerVal means left green time.
	 * 	If valid timerVal is equal or smaller than leftSignalGreent time,
	 * 	surely Vehicles in left lanes can depart.
	 * 
	 * @throw IllegalArgumentException
	 * 	If timerVal < 1, this exception will be thrown.
	 * 
	 * @return
	 * 	An array that contains departed Vehicles.
	 */
	public Vehicle[] proceed(int timerVal)
	{
		try {
			if(timerVal <= 0)
				throw new IllegalArgumentException();
		}
		
		catch(IllegalArgumentException iae)
		{
			lightValue = LightValue.RED;
			return null;
		}
		
		VehicleQueue passedVehicles = new VehicleQueue();
		
		if(timerVal > leftSignalGreenTime)
		{
			lightValue = LightValue.GREEN;
			if(isLaneEmpty(FORWARD_WAY, MIDDLE_LANE) &&
			   isLaneEmpty(FORWARD_WAY, RIGHT_LANE) &&
			   isLaneEmpty(BACKWARD_WAY, RIGHT_LANE) &&
			   isLaneEmpty(BACKWARD_WAY, MIDDLE_LANE) &&
			   (!isLaneEmpty(FORWARD_WAY, LEFT_LANE) ||
			   !isLaneEmpty(BACKWARD_WAY, LEFT_LANE)) )
					lightValue = LightValue.LEFT_SIGNAL;
				
			
		}
		
		else 
			lightValue = LightValue.LEFT_SIGNAL;
		
		String proceedInfo = "";
		proceedInfo += " ".repeat(4) + lightValue+" for " + name + "\n";
		proceedInfo += " ".repeat(4) + "Timer = "+timerVal+"\n\n";
		proceedInfo += " ".repeat(4) + "ARRIVING CARS:";
		
		System.out.println(proceedInfo);
		
		boolean isRoadEmpty = true;
		
		for(int wayNum = 0; wayNum < NUM_WAYS; wayNum++)
			for(int laneNum = 0; laneNum < NUM_LANES; laneNum++)
				if(!lanes[wayNum][laneNum].isEmpty())
					isRoadEmpty = false;
		
		if(isRoadEmpty)
			return null;
		
		if(lightValue == LightValue.GREEN)
		{
			if(!lanes[FORWARD_WAY][MIDDLE_LANE].isEmpty())
				passedVehicles.enqueue(lanes[FORWARD_WAY][MIDDLE_LANE].dequeue());
			
			if(!lanes[FORWARD_WAY][RIGHT_LANE].isEmpty())
				passedVehicles.enqueue(lanes[FORWARD_WAY][RIGHT_LANE].dequeue());
			
			if(!lanes[BACKWARD_WAY][RIGHT_LANE].isEmpty())
				passedVehicles.enqueue(lanes[BACKWARD_WAY][RIGHT_LANE].dequeue());
			
			if(!lanes[BACKWARD_WAY][MIDDLE_LANE].isEmpty())
				passedVehicles.enqueue(lanes[BACKWARD_WAY][MIDDLE_LANE].dequeue());
		}
		
		else if(lightValue == LightValue.LEFT_SIGNAL)
		{
			if(!lanes[FORWARD_WAY][LEFT_LANE].isEmpty())
				passedVehicles.enqueue(lanes[FORWARD_WAY][LEFT_LANE].dequeue());
			
			if(!lanes[BACKWARD_WAY][LEFT_LANE].isEmpty())
				passedVehicles.enqueue(lanes[BACKWARD_WAY][LEFT_LANE].dequeue());
		}
		
		Vehicle[] arrayedVehicles = new Vehicle[passedVehicles.size()];
		for(int i = 0; i < arrayedVehicles.length; i++)
			arrayedVehicles[i] = passedVehicles.dequeue();
			
		return arrayedVehicles;
	}
	
	/*
	 * If selected Lane has no Vehicle, it returns true. or returns false.
	 * 
	 * @Param wayIndex
	 * 	It decides whether you will check the lanes in forward or backward way.
	 * 
	 * @Param laneIndex
	 * It decides the lanes you will check is right or middle or left.
	 * 
	 * @throw IllegalArgumentException
	 * 	If lanes[wayIndex][laneIndex] refers null, exception is thrown.
	 * 
	 * @return
	 * 	If selected lane is empty, return is true. or return is false.
	 */
	public boolean isLaneEmpty(int wayIndex, int laneIndex)
	{
		try {
			if(wayIndex < 0 || wayIndex > NUM_WAYS)
			{
				System.out.println("Invalid way index.");
				throw new IllegalArgumentException();
			}
			
			else if(laneIndex < 0 || laneIndex > NUM_LANES)
			{
				System.out.println("Invalid lane index.");
				throw new IllegalArgumentException();
			}
		}
		
		catch(IllegalArgumentException iae)
		{
			return false;
		}
		
		return lanes[wayIndex][laneIndex].isEmpty();
	}
	
	/*
	 * Add a Vehicle to the tail of the lane
	 * 
	 * @Param WayIndex
	 * 	Index of way to get new Vehicle.
	 * 
	 * @Param laneIndex
	 * 	Index of lane to get new Vehicle.
	 * 
	 * @Param vehicle
	 * 	new Vehicle to be added.
	 * 
	 * @throw IllegalArgumentException
	 * 	If wayIndex or laneIndex is out of valid range, exception is thrown.
	 */
	public void enqueueVehicle(int wayIndex, int laneIndex, Vehicle vehicle)
	{
		try {
			if(wayIndex < 0 || wayIndex > NUM_WAYS)
			{
				System.out.println("Invalid way index.");
				throw new IllegalArgumentException();
			}
			
			else if(laneIndex < 0 || laneIndex > NUM_LANES)
			{
				System.out.println("Invalid lane index.");
				throw new IllegalArgumentException();
			}
		}
		
		catch(IllegalArgumentException iae)
		{return;}
		
		lanes[wayIndex][laneIndex].enqueue(vehicle);
	}
	
	/*
	 * Returns visualized TwoWayRoad object.
	 * 
	 * @return
	 * 	Visualized TwoWayRoad in String format.
	 */
	public String toString()
	{
		String roadInfo = "";
		roadInfo += name+":\n";
		
		roadInfo += String.format("%30s", "FORWARD");
		roadInfo += String.format("%23s", "BACKWARD") + "\n";
		
		roadInfo += drawLine("=");
		
		roadInfo += String.format("%30s", lanes[FORWARD_WAY][LEFT_LANE].toString_Reversed());
		if(lightValue != lightValue.LEFT_SIGNAL)
			roadInfo += " [L] x   ";
		else
			roadInfo += " [L]     ";
		
		if(lightValue == lightValue.GREEN)
			roadInfo += "  [R] ";
		else
			roadInfo += "x [R] ";
		roadInfo += String.format("%-30s", lanes[BACKWARD_WAY][RIGHT_LANE].toString())+"\n";
		
		roadInfo += drawLine("-");
		
		roadInfo += String.format("%30s", lanes[FORWARD_WAY][MIDDLE_LANE].toString_Reversed());
		if(lightValue != lightValue.GREEN)
			roadInfo += " [M] x   ";
		else
			roadInfo += " [M]     ";
		
		if(lightValue == lightValue.GREEN)
			roadInfo += "  [M] ";
		else
			roadInfo += "x [M] ";
		roadInfo += String.format("%-30s", lanes[BACKWARD_WAY][MIDDLE_LANE].toString())+"\n";
		
		roadInfo += drawLine("-");
		
		roadInfo += String.format("%30s", lanes[FORWARD_WAY][RIGHT_LANE].toString_Reversed());
		if(lightValue != lightValue.GREEN)
			roadInfo += " [R] x   ";
		else
			roadInfo += " [R]     ";
		
		if(lightValue == lightValue.LEFT_SIGNAL)
			roadInfo += "  [L] ";
		else
			roadInfo += "x [L] ";
		roadInfo += String.format("%-30s", lanes[BACKWARD_WAY][LEFT_LANE].toString())+"\n";
		
		roadInfo += drawLine("=")+"\n";
		
		return roadInfo;
	}
	
	/*
	 * Private helper method for better output legibility.
	 * 
	 * @Param lineMaterial
	 * 	lineMaterial will be used as a material of line for separation.
	 * 
	 * @return
	 * 	Completed line for separation.
	 */
	private String drawLine(String lineMaterial)
	{
		String line = "";
		line += lineMaterial.repeat(30);
		line += " ".repeat(15);
		line += lineMaterial.repeat(30);
		line += "\n";
		
		return line;
	}
}