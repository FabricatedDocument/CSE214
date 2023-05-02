/*
 * Name: SukMin Sim
 * ID: 112190914
 * Recitation: 02
 */
package hw4;

import java.util.InputMismatchException;
import java.util.Scanner;

public class IntersectionSimulator {

	private static BooleanSourceHW4 isCarArrived;

	public static void main(String[] args) {
		final int MAX_ROADS = 4;
		Scanner userInput = new Scanner(System.in);
		int simulationTime;
		double arrivalProb;
		int streetNum;
		String streetName[];
		int maxGreenTime[];
		

		printWelcomeMessage();

		while(true)
		{
			try {

				//Simulation time initialization
				System.out.print("Input the simulation time: ");
				simulationTime = userInput.nextInt();
				userInput.nextLine();

				if(simulationTime < 1)
				{
					System.out.println("Invalid simulation time.");
					throw new IllegalArgumentException();
				}

				//Arrival probability initialization
				System.out.print("Input the arrival probability: ");
				arrivalProb = userInput.nextDouble();
				userInput.nextLine();

				if(arrivalProb <= 0.0 || arrivalProb > 1)
				{
					System.out.println("Invalid arrival probability.");
					throw new IllegalArgumentException();
				}

				//Street num initialization
				System.out.print("Input the number of street: ");
				streetNum = userInput.nextInt();
				userInput.nextLine();

				if(streetNum < 1 || streetNum > MAX_ROADS)
				{
					System.out.println("Invalid the number of street.");
					System.out.println("Value should 1~"+MAX_ROADS+"\n");
					throw new IllegalArgumentException();
				}

				streetName = new String[streetNum];
				maxGreenTime = new int[streetNum];

				//Street name Initialization
				for(int i = 0; i<streetNum; i++)
				{
					System.out.print("Input the name of street "+ (i+1) +": ");
					streetName[i] = userInput.nextLine();

					if(streetName[i].length() <= 0)
					{
						System.out.println("The length of name should be over 0.");
						throw new IllegalArgumentException();
					}

					boolean isDuplicated = false;
					for(int j = 0; j<i; j++)
					{
						if(streetName[j].toLowerCase().equals(streetName[i].toLowerCase()))
						{
							System.out.println("Duplicate detected.");
							isDuplicated = true;
							break;
						}
					}

					if(isDuplicated)
					{
						i -= 1;
						continue;
					}
				}

				//Green time initialization
				for(int i = 0; i<streetNum; i++)
				{
					System.out.print("Input max green time for "+ streetName[i] +": ");
					maxGreenTime[i] = userInput.nextInt();
					userInput.nextLine();

					if(maxGreenTime[i] < 1)
					{
						System.out.println("Invalid green Time.");
						i -= 1;
						continue;
					}
				}
			}

			catch(IllegalArgumentException iae)
			{
				continue;
			}

			catch(InputMismatchException ime)
			{
				System.out.println("Invalid type.");
				userInput.nextLine();
				continue;
			}

			simulate(simulationTime, arrivalProb, streetName, maxGreenTime);
			break;
		}


	}
	
	/*
	 * Simulate an intersection
	 * 
	 * @Param simulationTime
	 * 	Only during this time, Vehicle is able to arrive in each roads.
	 * 	This is the least time of simulation operating.
	 * 
	 * @Param arrivalProb
	 * 	Probability of Vehicle arrival
	 * 	If random number which is range of (0.0~1.0] is greater than arrivalProb,
	 * 		Vehicle will be added to appropriate lane.
	 * 
	 * @Param roadNames
	 * 	Names in this array will be used as a TwoWayRoad name.
	 * 
	 * @Param maxGreenTimes
	 * 	Each values in this array will be used as maximum duration of green time.
	 */
	public static void simulate(int simulationTime, double arrivalProb, String[] roadNames, int[] maxGreenTimes)
	{
		isCarArrived = new BooleanSourceHW4(arrivalProb);
		TwoWayRoad lanes[] = new TwoWayRoad[roadNames.length];
		Intersection roads;
		String enqueuedVehicleInfo = "";
		int timeStep = 1;
		Vehicle[] passedVehicles;
		VehicleQueue arrivedVehicles = new VehicleQueue();
		String displayInfo;
		String statInfo;
		
		int totalPassed = 0;
		int totalWaited = 0;
		int vehicleWaiting = 0;
		int longestWaitTime = 0;

		for(int i = 0; i < roadNames.length; i++)
			lanes[i] = new TwoWayRoad(roadNames[i], maxGreenTimes[i]);

		roads = new Intersection(lanes);


		while(timeStep <= simulationTime || vehicleWaiting > 0)
		{	
			System.out.println("\nTime Step: "+timeStep);
			for(int roadNum = 0; roadNum < lanes.length; roadNum++)
				for(int wayNum = 0; wayNum < TwoWayRoad.NUM_WAYS; wayNum++)
					for(int laneNum = 0; laneNum < TwoWayRoad.NUM_LANES; laneNum++)
					{
						if(isCarArrived.occursHW4() && timeStep <= simulationTime)
						{
							Vehicle arrived  = new Vehicle(timeStep);
							
							arrivedVehicles.add(arrived);
							roads.enqueueVehicle(roadNum,wayNum, laneNum, arrived);
							vehicleWaiting += 1;

							enqueuedVehicleInfo += " ".repeat(8)+ "Car"+arrived.toString();
							enqueuedVehicleInfo += " entered "+ lanes[roadNum].getName() +", " ;
							enqueuedVehicleInfo += "going ";

							if(wayNum == TwoWayRoad.FORWARD_WAY)
								enqueuedVehicleInfo += "FORWARD in ";

							else
								enqueuedVehicleInfo += "BACKWARD in ";

							if(laneNum == TwoWayRoad.LEFT_LANE)
								enqueuedVehicleInfo += "LEFT lane.\n";

							else if(laneNum == TwoWayRoad.MIDDLE_LANE)
								enqueuedVehicleInfo += "MIDDLE lane.\n";

							else
								enqueuedVehicleInfo += "RIGHT lane.\n";
						}
					}

			String passedVehicleInfo = " ".repeat(4) + "PASSING CARS:\n";

			String beforeProceed = roads.toString();

			passedVehicles = roads.timeStep();

			displayInfo = roads.toString();

			if(passedVehicles != null)
			{
				for(Vehicle passed : passedVehicles)
				{
					totalPassed += 1;
					totalWaited += timeStep - passed.getTimeArrived();
					if(timeStep - passed.getTimeArrived() > longestWaitTime)
						longestWaitTime = timeStep - passed.getTimeArrived();
					
					passedVehicleInfo += " ".repeat(8)+ 
							"Car[" + String.format("%03d", passed.getSerialID())+ "]";
					passedVehicleInfo += " passes through. Wait time of "
							+(timeStep - passed.getTimeArrived())+"\n";
				}
				vehicleWaiting -= passedVehicles.length;
			}
			
			System.out.println(enqueuedVehicleInfo);
			System.out.println(passedVehicleInfo);

			if(passedVehicles != null)
				for(Vehicle passedThrough : passedVehicles)
				{
					if(passedThrough.getTimeArrived() == timeStep)
					{
						if(beforeProceed.indexOf(String.format("%-30s", passedThrough.toString())) != -1)
						{
							displayInfo = displayInfo.substring(0,
									beforeProceed.indexOf(String.format("%-30s", passedThrough.toString())))+
							
							String.format("%-30s", "// "+ passedThrough.toString()+" passed through.") +
							displayInfo.substring(beforeProceed.indexOf(
									passedThrough.toString())+30);
						}
						
						else
						{
							displayInfo = displayInfo.substring(0,
									beforeProceed.indexOf(String.format("%30s", passedThrough.toString())))+
									
							String.format("%30s", passedThrough.toString()+" passed through. //") +
							displayInfo.substring(beforeProceed.indexOf(
								passedThrough.toString())+5);
									
						}
					}
				}

			System.out.println(displayInfo);
			
			statInfo = "STATISTICS:\n";
			statInfo += " ".repeat(4)+String.format("%-25s", "Cars currently waiting:  ");
			statInfo += vehicleWaiting+" cars\n";
			
			statInfo += " ".repeat(4)+String.format("%-25s", "Total cars passed:  ");
			statInfo += totalPassed+" cars\n";
			
			statInfo += " ".repeat(4)+String.format("%-25s", "Total wait time:  ");
			statInfo += totalWaited + " turns\n";
			
			statInfo += " ".repeat(4)+String.format("%-25s", "Average wait time:  ");
			statInfo += String.format("%.2f",((float)totalWaited/totalPassed)) + " turns\n";
			
			System.out.println(statInfo);
			System.out.println("\n########################################"
							  +"########################################\n");
			
			arrivedVehicles.clear();
			enqueuedVehicleInfo = "";
			timeStep += 1;
		}
		
		System.out.println("\n========================================"
				          +"========================================\n");
		
		System.out.println("SIMULATION SUMAMRY: \n");
		
		System.out.print(" ".repeat(4)+String.format("%-22s", "Total Time:"));
		System.out.println(timeStep-1+" steps");
		
		System.out.print(" ".repeat(4)+String.format("%-22s", "Total vehicles:"));
		System.out.println(totalPassed+" vehicles");
		
		System.out.print(" ".repeat(4)+String.format("%-22s", "Longest wait time:"));
		System.out.println(longestWaitTime+" turn(s)");
		
		System.out.print(" ".repeat(4)+String.format("%-22s", "Total wait time:"));
		System.out.println(totalWaited+" turn(s)");
		
		System.out.print(" ".repeat(4)+String.format("%-22s", "Average wait time:"));
		System.out.println(String.format("%.2f",((float)totalWaited/totalPassed)) +" turn(s)");
		
	}

	private static void printWelcomeMessage()
	{
		System.out.println("Welcome to IntersectionSimulator 2023\n");
	}
}
