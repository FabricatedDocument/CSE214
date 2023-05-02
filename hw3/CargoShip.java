/*
 * Name: SukMin Sim
 * ID: 112190914
 * Recitation: 02
 */

package hw3;

import java.util.Stack;
import java.util.EmptyStackException;

public class CargoShip {
	private CargoStack stacks[];
	private int numStacks;
	private int maxHeight;
	private double maxWeight;
	
	/*
	 * Constructor method of CargoShip class
	 * Instantiate the required variables used in CargoShip class.
	 * 
	 * @Param maxStacks
	 * 	The number of stacks CargoShip class has.
	 * 
	 * @Param initMaxHeight
	 * 	The maximum capacity of each stack.
	 * 
	 * @Param initMaxWeight
	 * 	The maximum weight that CargoShip can accept.
	 * 
	 * @Throws IllegalArgumentException
	 * 	If maxStacks less than 1
	 * 	If maximum size of each stack is less than 1
	 * 	If maximum weight is equal or less than 0,
	 * 	this exception will be thrown.
	 */
	public CargoShip(int maxStacks, int initMaxHeight, double initMaxWeight)
	{
		try {
			if(maxStacks <= 0 || initMaxHeight <= 0 || initMaxWeight <= 0.0)
				throw new IllegalArgumentException();
		}
		
		catch(IllegalArgumentException iae)
		{
			System.out.println("Invalid input");
			return;
		}
		
		numStacks = maxStacks;
		stacks = new CargoStack[numStacks];
		for(int i = 0; i < stacks.length; i++)
			stacks[i] = new CargoStack();
		maxHeight = initMaxHeight;
		maxWeight = initMaxWeight;
	}
	
	/*
	 * Add a new Cargo to selected CargoStack.
	 * 
	 * @Param newCargo
	 * 	the new Cargo to be added.
	 * 
	 * @Param stackIndex
	 * 	selected CargoStack. The range of this parameter should be 0 < stackIndex <= numStacks
	 * 
	 * @Throws IllegalArgumentException
	 * 	This exception is thrown
	 * If new Cargo object is null.
	 * If stackIndex is under 0 or greater than the length of stacks
	 * 
	 * @Throws FullStackException
	 * 	If selected stack is at maximum capacity.
	 * 
	 * @Throws ShipOverWeightException
	 * 	If there is no enough weight capacity.
	 * 
	 * @Throws CargoStrengthException
	 * 	If newCargo regulates the Strength rule.
	 * 
	 *	By Strength rule, Moderate Cargo cannot be over Fragile Cargo
	 *					  Sturdy Cargo cannot be over Fragile or Moderate Cargo.
	 * 
	 */
	public void pushCargo(Cargo newCargo, int stackIndex) 
	{
		stackIndex -= 1;
		try {
			if(newCargo == null || stackIndex < 0 || stackIndex >= stacks.length)
				throw new IllegalArgumentException();
				
			else if(stacks[stackIndex].size() >= maxHeight)
				throw new FullStackException();
			
			else if(stacks[stackIndex].totalWeight() + newCargo.getWeight() > maxWeight)
				throw new ShipOverWeightException();
			
			else if(!stacks[stackIndex].isEmpty())
				if(stacks[stackIndex].peek().getNumericStrength() < newCargo.getNumericStrength())
					throw new CargoStrengthException();
		}
		
		catch(FullStackException fse)
		{
			System.out.println("Full cargo.");
			return;
		}
		
		catch(ShipOverWeightException swe)
		{
			System.out.println("Weight over");
			return;
		}
		
		catch(CargoStrengthException cse)
		{
			System.out.println("Operation failed! Cargo at top of stack cannot support weight.");
			return;
		}
		
		catch(IllegalArgumentException iae) 
		{
			System.out.println("Invalid input");
			return;
		}
		
		stacks[stackIndex].push(newCargo);
	}
	
	/*
	 * Remove the topmost Cargo in selected Stack.
	 * 
	 * @Param stackIndex
	 * 	the index of selected stack. 0 < stackIndex <= numStacks
	 * 
	 * @Throws IllegalArgumentException
	 * 	If stackIndex is out of valid range, exception will be thrown.
	 * 
	 * @Throws EmptyStackException
	 * 	If user tries to remove topmost Cargo from empty stack, exception will be thrown.
	 * 
	 * @return
	 * 	removed Cargo from selected stack
	 */
	public Cargo popCargo(int stackIndex) throws IllegalArgumentException, EmptyStackException
	{
		stackIndex -= 1;
		
		try
		{
			if(stackIndex < 0 || stackIndex >= stacks.length)
				throw new IllegalArgumentException();
			
			else if(stacks[stackIndex].isEmpty())
				throw new EmptyStackException();
		}
		
		catch(IllegalArgumentException iae)
		{
			System.out.println("Invalid index");
			return null;
		}
		
		catch(EmptyStackException ese)
		{
			System.out.println("Stack is empty.");
			return null;
		}
		
		Cargo poppedCargo = stacks[stackIndex].pop();
		
		return poppedCargo;
	}
	
	/*
	 * Return the topmost Cargo in selected stack
	 * If selected stack is empty, method prints a message noticing it's empty.
	 * 
	 * @Param index
	 * 	The index of 'stack'
	 * 
	 * @return
	 * 	If selected stack is not empty, topmost Cargo object will be returned.
	 * 	If selected stack is empty, returns null.
	 */
	public Cargo peekCargo(int index)
	{
		index -= 1;
		if(!stacks[index].isEmpty())
			return stacks[index].peek();
	
		else
		{
			System.out.println("Cargo stack is empty.");
			return null;
		}
	}
	
	/*
	 * Parse all Cargo objects in each stacks.
	 * If there is a Cargo has same name with parameter, 
	 * 	location and Cargo information is printed out more specifically.
	 * 
	 * @Param name
	 * 	name of the target. Case insensitive.
	 */
	public void findAndPrint(String name)
	{
		name = name.toLowerCase();
		int targetCount = 0;
		int targetWeight = 0;
		Stack<Cargo>reversedCargo = new Stack<Cargo>();
		String searchedInfo = "";
		
		for(int stackIndex = 0; stackIndex < numStacks; stackIndex++)
		{
			int cargoIndex = 0;
			while(true)
			{
				if(stacks[stackIndex].isEmpty())
					break;
				
				else
					reversedCargo.push(stacks[stackIndex].pop());
				
				if(reversedCargo.peek().getName().toLowerCase().equals(name))
				{
					if(targetCount == 0)
					{
						searchedInfo += " Stack   Depth   Weight   Strength\n";
						searchedInfo += ("=".repeat(7)+"+").repeat(2);
						searchedInfo += "=".repeat("Weight".length()+2)+"+";
						searchedInfo += "=".repeat("Strength".length()+2)+"\n";
						System.out.println(searchedInfo);
					}
					
					System.out.println("  "+(stackIndex+1)+"    |  "
										 +cargoIndex+"    |    "
										 +(int)reversedCargo.peek().getWeight()+"    | "
										 +reversedCargo.peek().getStrength()+"");
					targetCount += 1;
					targetWeight += reversedCargo.peek().getWeight();
				}
				cargoIndex += 1;
			}
			
			while(true)
			{
				if(reversedCargo.isEmpty())
					break;
				
				else
					stacks[stackIndex].push(reversedCargo.pop());
			}
		}
		
		if(targetCount == 0)
		{
			System.out.println("Cargo '"+name+"' could not be found on the ship");
			return;
		}
			
		System.out.println("\nTotal Count: "+ targetCount);
		System.out.println("Total Weight: "+ targetWeight+"\n");
	}
	
	/*
	 * Simplify the whole information of each stacks.
	 * 
	 * @return
	 * 	Integrated information of each stacks.
	 */
	public String toString()
	{
		String shipInfo = "";
		
		for(int i = 0; i<stacks.length; i++)
			shipInfo += "Stack "+(i+1)+": "+stacks[i].toString()+"\n";
				
		return shipInfo;
	}
	
	/*
	 * Prints the amount of capacity used.
	 * 
	 * @return
	 * 	String variable that showing used capacity and maximum capacity.
	 */
	public String checkCapacity()
	{
		String capacityStat = "Total Weight = ";
		double totalWeight = (int)getTotalWeight();
		capacityStat += (int)totalWeight+" / "+(int)maxWeight;
		
		return capacityStat;
	}
	
	/*
	 * Returns maximum weight.
	 * 
	 * @return
	 * 	double type variable showing max weight of CargoShip.
	 */
	public double getMaxWeight()
	{return maxWeight;}
	
	/*
	 * Returns used capacity. (Total sum of each stacks)
	 * 
	 * @return
	 *	total weight of CargoStack in CargoShip
	 */
	public double getTotalWeight()
	{
		double totalWeight = 0;
		for(int i = 0; i<stacks.length; i++)
			totalWeight += stacks[i].totalWeight();
		
		return totalWeight;
	}
	
	/*
	 * Judge if new Cargo is able to be added to selected stack.
	 * If all conditions are met, method returns true or returns false.
	 * 
	 * @Param newCargo
	 * 	Cargo that would be added.
	 * 
	 * @Param stackIndex
	 * 	The destination stack of newCargo.
	 * 
	 * @return
	 * 	Is new Cargo able to be added to topmost of the stack.
	 */
	public boolean isCargoArrivable(Cargo newCargo, int stackIndex)
	{
		if(stackIndex > numStacks || stackIndex <= 0)
		{
			System.out.println("Invalid index");
			return false;
		}
			
		stackIndex -= 1;
		
		if(stacks[stackIndex].size() == maxHeight)
		{
			System.out.println("Operation failed! Cargo stack is at maximum height.");
			return false;
		}
		
		else if(!stacks[stackIndex].isEmpty())
			if(stacks[stackIndex].peek().getNumericStrength() < newCargo.getNumericStrength())
			{
				System.out.println("Operation failed! Cargo at top of stack cannot support weight.");
				return false;
			}
			
		return true;
	}
	
	/*
	 * Returns the maximum capacity of stack.
	 * 
	 * @return
	 * 	maximum capacity of a stack.
	 */
	public int getNumStack()
	{return numStacks;}
}
