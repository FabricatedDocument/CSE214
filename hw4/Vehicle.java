package hw4;

public class Vehicle {
	private static int serialCounter = 0;
	private int serialID;
	private int timeArrived;
	
	/*
	 * Constructor method of Vehicle class which is immutable object.
	 * All of the private variables should be instantiated in here.
	 * 
	 * @Param initTimeArrived
	 * 	Vehicle with smaller initTimeArrived value means the Vehicle object arrived earlier.
	 * 	In case of the value initTimeArrived < 1, IllegalArgumentException should be thrown.
	 * 
	 * @throw IllegalArgumentException
	 * 	If initTimeArrived is under 1, this exception is thrown.
	 */
	public Vehicle(int initTimeArrived)
	{
		try {

			if(initTimeArrived < 1)
			{
				throw new IllegalArgumentException();
			}
		}

		catch(IllegalArgumentException iae)
		{
			System.out.println("Invalid time arrived.");
			return;
		}

		serialID = ++serialCounter;
		timeArrived = initTimeArrived;
	
		
	}
	
	/*
	 * Returns serialID of this Vehicle
	 * serialID with 'N' means this Vehicle is arrived in 'N'th order
	 * 
	 * @return
	 * 	serialID of this Vehicle
	 */
	public int getSerialID()
	{return serialID;}

	/*
	 * Returns timeArrived of this Vehicle.
	 * Refer the meaning of timeArrived through the explanation on constructor method.
	 * 
	 * @return
	 * 	timeArrived of this Vehicle.
	 */
	public int getTimeArrived()
	{return timeArrived;}
	
	/*
	 * Visualize this Vehicle object in 3 digit of String.
	 * serialID with N would be visualized to [00N].
	 * 
	 * @return
	 * 	Visualized Vehicle object information.
	 */
	public String toString()
	{return "["+String.format("%03d", serialID)+"]";}
	
}
