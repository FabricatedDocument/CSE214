package hw7;

import java.util.InputMismatchException;
import java.util.Scanner;

public class NeoViewer {
	public static NeoDatabase neoDB = new NeoDatabase();
	public static Scanner userInput = new Scanner(System.in);

	public static void main(String[] args) {
		printWelcomeMessage();

		String option;
		while(true)
		{
			printOptions();
			option = userInput.nextLine().replaceAll(" " ,"").toLowerCase();

			if(option.equals("a"))
				goOptionA();

			else if(option.equals("p"))
				goOptionP();

			else if(option.equals("s"))
				goOptionS();

			else if(option.equals("q"))
			{
				System.out.println("Program terminating normally...");
				break;
			}

			System.out.println();
		}
	}

	public static void printWelcomeMessage()
	{
		System.out.println("Welcome to NEO Viewer!");
	}

	public static void printOptions()
	{
		System.out.print("Option Menu:\n"
				+ "    A) Add a page to the database\n"
				+ "    S) Sort the database \n"
				+ "    P) Print the database as a table.\n"
				+ "    Q) Quit\n\n"
				+ "Select a menu option: ");
	}

	public static void goOptionA()
	{
		int page;
		System.out.print("Enter the page to load: ");

		try{
			page = userInput.nextInt();

			if(page < 0 || page > 715)
				throw new IllegalArgumentException();
		}

		catch(InputMismatchException ime)
		{
			System.out.println("Invalid Input.");
			return;
		}

		catch(IllegalArgumentException iae)
		{
			System.out.println("Invalid page number.");
			return;
		}

		finally
		{userInput.nextLine();}

		neoDB.addAll(neoDB.buildQueryURL(page));
		System.out.println("Page loaded successfully!");
	}

	public static void goOptionS()
	{
		String standardOption;
		System.out.print(" R) Sort by referenceID\n"
				+ " D) Sort by diameter\n"
				+ " A) Sort by approach date\n"
				+ " M) Sort by miss distance\n"
				+ " Select a menu option: ");

		standardOption = userInput.nextLine().replaceAll(" ","").toLowerCase();

		if(standardOption.equals("r"))
			neoDB.sort("ref");

		else if(standardOption.equals("d"))
			neoDB.sort("dia");

		else if(standardOption.equals("a"))
			neoDB.sort("date");

		else if(standardOption.equals("m"))
			neoDB.sort("mis");

		else
			System.out.println("Invalid option.");
	}

	public static void goOptionP()
	{neoDB.printTable();}
}
