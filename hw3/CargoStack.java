package hw3;

import java.util.Stack;

public class CargoStack {
	private Stack<Cargo> cargoRow;
	private double totalWeight;
	private int size;
	private String stackInfo;
	
	/*
	 * Constructor method of CargoStack all variables should be instantiated through this method. 
	 */
	public CargoStack()
	{
		cargoRow = new Stack<Cargo>();
		totalWeight = 0.0;
		size = 0;
		stackInfo = "";
	}

	/*
	 * Add a new Cargo object to the instantiated Stack(CargoLow)
	 * 
	 * @Param newCargo
	 * 	Cargo object to be added. It should be instantiated and refer valid address.
	 */
	public void push(Cargo newCargo)
	{		
		if(size == 0 || newCargo.getNumericStrength() <= cargoRow.peek().getNumericStrength())
		{
			cargoRow.push(newCargo);
			totalWeight += newCargo.getWeight();
			size += 1;
			
			if(size != 1)
				stackInfo += ", ";
			
			stackInfo += newCargo.toString();
		}
			
		else
			System.out.println("Operation failed! Cargo at top of stack cannot support weight.");
	}

	/*
	 * Remove the topmost Cargo from the stack(CargoRow)
	 * 
	 * @return
	 * 	the removed Cargo object
	 * 	If the stack is empty, method returns null
	 */
	public Cargo pop()
	{
		if(!isEmpty())
		{
			Cargo poppedCargo = cargoRow.pop();
			totalWeight -= poppedCargo.getWeight();
			size -= 1;
			
			if(size >= 1)
				stackInfo = stackInfo.substring(0,stackInfo.lastIndexOf(","));
			
			else
				stackInfo = "";
			
			return poppedCargo;
		}
		
		System.out.println("Cargo is empty.");
		return null;
	}

	/*
	 * Refer the topmost Cargo object
	 * 
	 * @return
	 * 	topmost Cargo type object
	 */
	public Cargo peek()
	{return cargoRow.peek();}

	/*
	 * Return the number of Cargo objects in the stack.
	 * 
	 * @return
	 * 	the number of Cargos
	 */
	public int size()
	{return size;}

	/*
	 * Return if the stack is empty.
	 * If it's empty, returns true or returns false
	 * 
	 * @return
	 * 	If stack is empty or not
	 */
	public boolean isEmpty()
	{return size == 0;}
	
	/*
	 * Add the weight of each Cargos in the stack then return.
	 * 
	 * @return
	 * 	Total weight of Cargos in this class
	 */
	public double totalWeight()
	{return totalWeight;}
	
	/*
	 * Each Cargos are expressed with the abbreviation of their Strength.
	 * 
	 * @return
	 * 	Abbreviation of each Cargos that separated with commas.
	 */
	public String toString()
	{return stackInfo;}
}
