/*
 * Name: SukMin Sim
 * ID: 112190914
 * Recitation: 02
 */
package hw6;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.Scanner;


public class StorageManager {
	private static Scanner userInput = new Scanner(System.in);
	private static StorageTable storages = new StorageTable();
	
	public static void main(String[] args) {
		try
		{	
			FileInputStream filein = new FileInputStream("storage.obj");
			ObjectInputStream StorageObjIn = new ObjectInputStream(filein);
			storages = (StorageTable)StorageObjIn.readObject();
			StorageObjIn.close();
		}
		
		catch(IOException ioe)
		{
			System.out.println("Something happened.");
		}
		
		catch(ClassNotFoundException cnfe)
		{
			System.out.println("No class found.");
		}
	
		System.out.println("Hello, and welcome to Rocky Stream Storage Manager\n");
		
		while(true)
		{
			printOptions();
			String optionSelected = userInput.nextLine().replaceAll(" ","").toLowerCase();
			
			if(optionSelected.equals("a"))
				goOptionA();
			
			else if(optionSelected.equals("p"))
				goOptionP();
			
			else if(optionSelected.equals("r"))
				goOptionR();
			
			else if(optionSelected.equals("c"))
				goOptionC();
			
			else if(optionSelected.equals("f"))
				goOptionF();
			
			else if(optionSelected.equals("q"))
			{
				goOptionQ();
				break;
			}
			
			else if(optionSelected.equals("x"))
			{
				goOptionX();
				break;
			}
			
			System.out.println();
		}
	}


	private static void printOptions()
	{
		System.out.print( "P - Print all storage boxes\n"
				+ "A - Insert into storage box\n"
				+ "R - Remove contents from a storage box\n"
				+ "C - Select all boxes owned by a particular client\n"
				+ "F - Find a box by ID and display its owner and contents\n"
				+ "Q - Quit and save workspace\n"
				+ "X - Quit and delete workspace\n\n"
				+ "Please select an option: ");
	}
	
	/*
	 * Perform Storage registration
	 * 
	 * @throws InputMismatchException
	 * 	If given id is non-numeric value, this error is thrown.
	 * 	
	 * @throws IllegalArgumentException
	 * 	If the length of given name or the contents is 0, this error is thrown. 
	 */
	private static void goOptionA()
	{
		int newStorageId;
		String newClientName;
		String newContents;
		
		try {
		System.out.print("Please enter id: ");
		newStorageId = userInput.nextInt();
		userInput.nextLine();
		
		System.out.print("Please enter client: ");
		newClientName = userInput.nextLine();
		
		System.out.print("Please Enter Contents: ");
		newContents = userInput.nextLine();
		
		if(newClientName.length() < 1 || newContents.length() < 1)
			throw new IllegalArgumentException();
		
		storages.put(newStorageId, new Storage(newStorageId, newClientName,newContents));
		System.out.println("Storage "+ newStorageId +" set");
		}
		
		catch(InputMismatchException ime)
		{
			userInput.nextLine();
			System.out.println("Invalid id entered, id should be an integer.");
			return;
		}
		
		catch(IllegalArgumentException iae)
		{
			System.out.println("Invalid input, Name and Contents should not be empty.");
			return;
		}
	}
	
	/*
	 * Remove a Storage on the table.
	 * 
	 * @throw IllegalArgumentExcpetion
	 * 	If given key does not match a value, this error is thrown.
	 * 
	 * @throw InputMismatchException
	 *  If given key is non-numeric value, this error is thrown.
	 */
	private static void goOptionR()
	{
		int targetId;
		try {
			System.out.print("Please enter ID: ");
			targetId = userInput.nextInt();
			
			if(storages.getStorage(targetId) == null)
				throw new IllegalArgumentException();
			
			storages.remove(targetId);
		}
		
		catch(InputMismatchException ime)
		{
			System.out.println("Invalid id.");
			return;
		}
		
		catch(IllegalArgumentException iae)
		{
			System.out.println("No storage found.");
			return;
		}
		
		finally
		{userInput.nextLine();}
		
		System.out.println("Box "+targetId+" is now removed");
	}
	
	/*
	 * Print out all boxes owned by a particular client.
	 */
	private static void goOptionC()
	{
		System.out.print("Please enter the name of the client: ");
		String clientName = userInput.nextLine();
		System.out.print(storages.getStorageInfoFrame());
		
		for(int keys:storages.getSortedKeySet())
			if(storages.getStorage(keys).getClientName().equals(clientName))
				System.out.println(storages.getStorage(keys).toString());
	}
	
	/*
	 * Print out a box by ID and display its owner and contents
	 */
	private static void goOptionF()
	{
		System.out.print("Please enter ID: ");
		try {
			int boxId = userInput.nextInt();
			System.out.println("Box "+boxId);
			System.out.println("Contents: "+storages.getStorage(boxId).getContents());
			System.out.println("Owner: "+storages.getStorage(boxId).getClientName());
		}
		
		catch(InputMismatchException ime)
		{System.out.println("Invalid ID input.");}
		
		catch(NullPointerException npe)
		{System.out.println("No box found");}
		
		finally
		{userInput.nextLine();}
	}
	
	/*
	 * Print all storage boxes in ascending order.
	 */
	private static void goOptionP()
	{
		System.out.print(storages.toString());
	}
	
	/*
	 * Quit and save workspace
	 */
	private static void goOptionQ()
	{
		try
		{
			FileOutputStream fileout = new FileOutputStream("storage.obj");
			ObjectOutputStream StorageObjOut = new ObjectOutputStream(fileout);
			StorageObjOut.writeObject(storages);
			StorageObjOut.close();
		}
		
		catch(IOException ioe)
		{
			System.out.println("Error occurred during writing object(s).");
		}
		
		System.out.println("Storage Manager is quitting, current storage is saved for next session.");
	}
	
	/*
	 * Quit and delete workspace
	 */
	private static void goOptionX()
	{
		try
		{
			FileOutputStream fileout = new FileOutputStream("storage.obj");
			ObjectOutputStream StorageObjOut = new ObjectOutputStream(fileout);
			StorageObjOut.writeObject(new StorageTable());
			StorageObjOut.close();
		}
		
		catch(IOException ioe)
		{
			System.out.println("Error occurred during saving.");
		}
		
		System.out.println("Storage Manager is quitting, all data is being erased.");
	}
}
