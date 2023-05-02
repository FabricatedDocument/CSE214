/*
 * Name: SukMin Sim
 * ID: 112190914
 * Recitation: 02
 */
package hw4;

import java.util.Queue;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class VehicleQueue implements Queue<Vehicle> {
	
	private Queue<Vehicle> vehicles;
	private int queueSize;
	
	/*
	 * Constructor method of VehicleQueue.
	 * 
	 * All private variable should be instantiated here.
	 */
	public VehicleQueue()
	{
		vehicles = new LinkedList<Vehicle>();
		queueSize = 0;
	}
	
	/*
	 * Add a new valid Vehicle object to the tail of the queue
	 * 
	 * @Param newVehicle
	 * 	newVehicle would be added if it is valid.
	 * 
	 * @throw NullPointerException
	 * 	If newVehicle is referring null, this exception will be thrown.
	 */
	public void enqueue(Vehicle newVehicle)
	{
		try
		{
			if(newVehicle == null)
				throw new NullPointerException();
		}
		
		catch(NullPointerException npe)
		{
			System.out.println("Invalid Vehicle");
			return;
		}
		
		queueSize += 1;
		vehicles.add(newVehicle);
	}
	
	/*
	 * Remove the Vehicle object at the head of the queue
	 * 
	 * throw NullPointerException
	 * 	If the queue is empty, this exception should be thrown.
	 */
	public Vehicle dequeue()
	{
		try
		{
			if(queueSize < 1)
				throw new NullPointerException();
		}
		
		catch(NullPointerException npe)
		{
			System.out.println("Queue is empty.");
			return null;
		}
		
		queueSize -= 1;
		return vehicles.poll();
	}
	
	/*
	 * Return the size of the queue
	 * 
	 * @return
	 * 	the number of Vehicle objects in the queue
	 */
	public int size()
	{return queueSize;}
	
	/*
	 * Return if the queue is empty or not.
	 * 
	 * @return
	 * 	If queue is empty, returns true. or returns false.
	 */
	public boolean isEmpty()
	{return queueSize == 0 ;}
	
	/*
	 * Visualize the queue with Vehicle objects.
	 * Vehicle at the head will be located to leftmost in String.
	 * 
	 * @return
	 * 	Visualized Vehicles in String
	 */
	public String toString()
	{
		String laneInfo = "";
		
		if(vehicles.toArray() == null)
			return "";
		
		for(Object vehicle : vehicles.toArray())
			laneInfo += vehicle.toString();
		
		return laneInfo;
	}
	
	/*
	 * Visualize the queue with Vehicle objects in reversed order.
	 * Vehicle at the head will be located to rightmost in String.
	 * 
	 * @return
	 * 	Reversely visualized Vehicles in String.
	 */
	public String toString_Reversed()
	{
		String reversedLaneInfo = "";
		
		if(vehicles.toArray() == null)
			return "";
		
		Object[] tempArray = vehicles.toArray();
		
		for(int i = tempArray.length-1; i >= 0; i--)
			reversedLaneInfo += tempArray[i].toString();
		
		return reversedLaneInfo;
	}
	
	/*
	 * This method convert the queue to array.
	 * 
	 * @return
	 * 	Converted queue to array which is containing Vehicle
	 */
	public Object[] toArray()
	{return vehicles.toArray();}
	
	/* METHODS FROM HERE ARE NOT USED FOR THIS ASSIGNMENT.
	=====================================================*/
	
	@Override
	public boolean contains(Object o) 
	{	return vehicles.contains(o);}

	@Override
	public Iterator<Vehicle> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends Vehicle> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear()
	{vehicles.clear();}

	@Override
	public boolean add(Vehicle e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean offer(Vehicle e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vehicle remove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vehicle poll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vehicle element() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vehicle peek() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
