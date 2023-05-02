package hw3;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ShipLoader {
	public static CargoShip ship;
	public static CargoStack dock;
	public static Scanner userInput = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		dock = new CargoStack();
		String menuOption;
		
		while(true)
		{
			try {
				System.out.print("Number of Stacks: ");
				int numCargoStack = userInput.nextInt();
				
				System.out.print("Maximum height of stacks: ");
				int cargoShipMaxHeight = userInput.nextInt();
				
				System.out.print("Maximum total cargo weight: ");
				double cargoShipMaxWeight = userInput.nextDouble();	
				
				if(numCargoStack <= 0 || cargoShipMaxHeight <= 0 || cargoShipMaxWeight <= 0)
				{
					System.out.println("Invalid ship info\n");
					continue;
				}
				
				System.out.println("\nCargo ship created.\n"
						+ "Pulling ship in to dock...\n"
						+ "Cargo ship ready to be loaded.");
				
				ship = new CargoShip(numCargoStack, cargoShipMaxHeight, cargoShipMaxWeight);
			}
			
			catch(InputMismatchException ime)
			{
				System.out.println("Invalid input.\n");
				continue;
			}
			
			finally {
				userInput.nextLine();
			}
			
			System.out.println("");
			break;
		}
			
		printWelcomeMessage();
		while(true)
		{
			printMenu();
			menuOption = userInput.nextLine().toUpperCase().replaceAll(" ", "");
			
			if(menuOption.equals("C"))
				goOptionC();
			
			else if(menuOption.equals("L"))
				goOptionL();
			
			else if(menuOption.equals("U"))
				goOptionU();
			
			else if(menuOption.equals("M"))
				goOptionM();
			
			else if(menuOption.equals("K"))
				goOptionK();
			
			else if(menuOption.equals("P"))
			{
				goOptionP();
				continue;
			}
				
			else if(menuOption.equals("S"))
				goOptionS();
			
			else if(menuOption.equals("Q"))
			{
				System.out.println("Program terminating normally...");
				break;
			}
			
			else
			{
				System.out.println("Invalid option");
				continue;
			}
			
			goOptionP();
			System.out.println("");
		}
	}
	
	public static void printWelcomeMessage()
	{System.out.println("Welcome to ShipLoader!");}

	public static void printMenu()
	{
		System.out.print("Please select an option:\n"
				+ "C) Create new cargo\n"
				+ "L) Load cargo from dock\n"
				+ "U) Unload cargo from ship\n"
				+ "M) Move cargo between stacks\n"
				+ "K) Clear dock\n"
				+ "P) Print ship stacks\n"
				+ "S) Search for cargo\n"
				+ "Q) Quit\n\n"
				+ "Select a menu option: ");
	}
	
	public static void goOptionC()
	{
		Cargo cargoAdded;
		System.out.print("Enter the name of the cargo: ");
		String cargoName = userInput.nextLine();
				
		try {
			System.out.print("Enter the weight of the cargo: ");
			double cargoWeight = userInput.nextDouble();
			userInput.nextLine();
			
			System.out.print("Enter the container strength (F/M/S): ");
			String strength = userInput.nextLine();
			strength = strength.toUpperCase();
			strength = strength.replaceAll(" ", "");
			
			CargoStrength cargoStrength;
			
			if(strength.equals("F"))
				cargoStrength = CargoStrength.FRAGILE;
			
			else if(strength.equals("M"))
				cargoStrength = CargoStrength.MODERATE;
			
			else if(strength.equals("S"))
				cargoStrength = CargoStrength.STURDY;
			
			else
				throw new IllegalArgumentException();
			
			cargoAdded = new Cargo(cargoName, cargoWeight, cargoStrength);
			dock.push(cargoAdded);
			
			if(cargoAdded == dock.peek())
				System.out.println("Cargo '"+dock.peek().getName()+"' pushed onto the dock.\n");
		}
		
		catch(InputMismatchException ime)
		{
			System.out.println("Invalid input.");
			userInput.nextLine();
			return;
		}
		
		catch(IllegalArgumentException iae)
		{
			System.out.println("Invalid input.");
			return;
		}
	}
	
	public static void goOptionL()
	{
		int stackIndex = 0;
		System.out.print("Select the load destination stack index: ");
		try{
			stackIndex = userInput.nextInt();
		}
		
		catch(InputMismatchException ime)
		{
			System.out.println("Invalid input");
			return;
		}
		
		finally {userInput.nextLine();}
		
		if(dock.size() == 0)  
		{
			System.out.println("Dock is empty.");
			return;
		}	
		
		else if(!ship.isCargoArrivable(dock.peek(), stackIndex))	
			return;
		
		else if(ship.getTotalWeight()+dock.peek().getWeight() >= ship.getMaxWeight())
		{
			System.out.println("Operation failed! Cargo would put ship overweight.");
			return;
		}
		
		System.out.println("Cargo '"+ dock.peek().getName() +""
					+ "' moved from dock to stack"+ stackIndex +".\n");
			
		ship.pushCargo(dock.pop(), stackIndex);
	}
	
	public static void goOptionU() 
	{
		System.out.print("Select stack Index: ");
		int index;
		try {
			index = userInput.nextInt();
			if(index <= 0 || index > ship.getNumStack())
				throw new IllegalArgumentException();
			
			if(ship.peekCargo(index) == null)
				return;
		}
		
		catch(IllegalArgumentException iae)
		{
			System.out.println("Index out of range.");
			return;
		}
		
		catch(InputMismatchException ime)
		{
			System.out.println("Invalid input");
			return;
		}
		
		finally
		{
			userInput.nextLine();
		}
		
		if(dock.isEmpty() || 
				dock.peek().getNumericStrength() >= ship.peekCargo(index).getNumericStrength())
		{
			dock.push(ship.popCargo(index));
			System.out.println("Cargo '"+dock.peek().getName()+"' moved from stack "
					+index+" to dock.");
		}
		
		else
		{
			System.out.println("Operation failed! Cargo at top of stack cannot support weight.");
		}
	}
	
	public static void goOptionM() 
	{
		int indexFrom;
		int indexTo;
		
		try
		{
			System.out.print("Stack from: ");
			indexFrom = userInput.nextInt();
			
			System.out.print("Stack to: ");
			indexTo = userInput.nextInt();
			
			if(indexFrom < 1 || indexFrom > ship.getNumStack() || 
					indexTo < 1 || indexTo > ship.getNumStack())
					throw new IllegalArgumentException();

			try {
				if(ship.isCargoArrivable(ship.peekCargo(indexFrom), indexTo))
				{
					System.out.println("Cargo '"+ship.peekCargo(indexFrom).getName()+
							"' moved from stack "+indexFrom+" to stack "+indexTo+".");
					
					ship.pushCargo(ship.popCargo(indexFrom), indexTo);
				}
				
			}
			
			catch(NullPointerException npe)
			{
				System.out.println("Stack "+ indexFrom + " is empty.");
				return;
			}
		}
		
		catch(InputMismatchException ime)
		{
			System.out.println("Invalid input.");
			return;
		}
		
		catch(IllegalArgumentException iae)
		{
			System.out.println("Invalid cargo stack index.");
			return;
		}
		
		finally 
		{userInput.nextLine();}
	}
	
	public static void goOptionK()
	{
		dock = new CargoStack();
		System.out.println("Dock cleared.\n");
	}
	
	public static void goOptionP() 
	{
		System.out.print(ship.toString());
		System.out.println("Dock: "+dock.toString()+"\n");
		System.out.println(ship.checkCapacity());
	}
	
	public static void goOptionS() 
	{
		System.out.print("Enter the name of cargo to find: ");
		String targetName = userInput.nextLine();
		ship.findAndPrint(targetName);
	}
}
