/*
 * Name: SukMin Sim
 * ID: 112190914
 * Recitation: 02
 */
package hw2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PresentationManager {
	private static SlideList presentation = new SlideList();
	private static Scanner userInput = new Scanner(System.in);

	public static void main(String[] args) 
	{
		String userMenuInput;

		printWelcomeMessage();

		while(true)
		{
			printMenuOptions();
			userMenuInput = userInput.nextLine();
			userMenuInput = userMenuInput.toUpperCase();
			userMenuInput = userMenuInput.replaceAll(" ","");

			if(userMenuInput.equals("F"))
				presentation.cursorForward();

			else if(userMenuInput.equals("B"))
				presentation.cursorBackward();

			else if(userMenuInput.equals("D"))
				goOptionD();

			else if(userMenuInput.equals("A"))
				goOptionA(true);

			else if(userMenuInput.equals("P"))
				goOptionP();

			else if(userMenuInput.equals("E"))
				goOptionE();
			
			else if(userMenuInput.equals("I"))
				goOptionA(false);

			else if(userMenuInput.equals("R"))
				presentation.removeCursor();
			
			else if(userMenuInput.equals("H"))
				presentation.resetCursorHead();
			
			else if(userMenuInput.equals("Q"))
			{
				System.out.println("\nProgram terminating normally...");
				break;
			}
			
			else
				System.out.println("Invalid option");
				
			System.out.println();
		}


	}

	private static void printWelcomeMessage()
	{System.out.println("Welcome to PresentationManager!\n");}

	private static void printMenuOptions()
	{
		System.out.print("\n"
				+ "Please select an option: \n"
				+ "F) Move cursor forward \n"
				+ "B) Move cursor backward \n"
				+ "D) Display cursor slide \n"
				+ "E) Edit cursor slide \n"
				+ "P) Print presentation summary \n"
				+ "A) Append new slide to tail \n"
				+ "I) Insert new slide before cursor \n"
				+ "R) Remove slide at cursor \n"
				+ "H) Reset cursor to head \n"
				+ "Q) Quit \n\n"
				+ "Select a menu option: ");
	}

	private static void goOptionP()
	{System.out.println(presentation.toString());}

	private static void goOptionA(boolean isOptionA)
	{
		String title;
		double duration;
		String bullets[] = new String[5];
		Slide newSlide;


		System.out.print("Enter the slide title: ");
		title = userInput.nextLine();

		System.out.print("Enter the slide duration: ");

		try 
		{
			duration = Math.round(userInput.nextDouble()*10) / 10.0;
			
			if(duration <= 0.0)
				throw new IllegalArgumentException();
		}

		catch(InputMismatchException ime)
		{
			System.out.println("Duration should be a number.");	
			return;
		}
		
		catch(IllegalArgumentException iae)
		{
			System.out.println("Invalid duration");	
			return;
		}
		
		finally
		{userInput.nextLine();}
		
		for(int bulletIndex = 0; bulletIndex < bullets.length; bulletIndex+=1)
		{
			System.out.print("Bullet "+(bulletIndex+1)+": ");
			bullets[bulletIndex] = userInput.nextLine();

			if(bulletIndex == bullets.length-1)
			{
				System.out.println("No more bullets allowed. Slide is full.\n");
				break;
			}

			System.out.print("Add another bullet point? (Y/N) ");
			String yOrn = userInput.nextLine().toUpperCase();
			if(yOrn.equals("N"))
				break;
			
			else if(!yOrn.equals("Y"))
			{
				System.out.println("Invalid option");
				return;
			}
		}

		newSlide = new Slide(title,duration,bullets);
		
		if(isOptionA)
			presentation.appendToTail(newSlide);
		
		else
			presentation.insertBeforeCursor(newSlide);
		
		System.out.println("Slide \""+ newSlide.getTitle()+"\" added to presentation");

	}

	private static void goOptionD()
	{
		if(presentation.size() > 0)
			System.out.println(presentation.getCursorSlide().bulletInfoToString());

		else
			System.out.println("Empty slideshow");
	}

	private static void goOptionE()
	{
		if(presentation.size() == 0)
		{
			System.out.println("Empty slideshow");
			return;
		}

		System.out.print("Edit title, duration, or bullets? (t/d/b): ");

		String tdbInput = userInput.nextLine().toLowerCase();

		if(tdbInput.equals("t"))
		{
			System.out.print("Enter new title: ");
			tdbInput = userInput.nextLine();
			presentation.getCursorSlide().setTitle(tdbInput);

			System.out.println("Title has been changed successfully.");
		}

		else if(tdbInput.equals("d"))
		{
			double newDuration;
			System.out.print("Enter new duration: ");
			try 
			{
				newDuration = Math.round(userInput.nextDouble()*10) / 10.0;

				if(newDuration <= 0.0)
					throw new IllegalArgumentException();
			}
			
			catch(InputMismatchException ime)
			{
				System.out.println("Invalid duration");
				return;
			}
			
			catch(IllegalArgumentException iae)
			{
				System.out.println("Invalid duration");
				return;
			}
			
			finally {userInput.nextLine();}
			
			presentation.editCursorDuration(presentation.getCursorSlide(), newDuration);
		}

		else if(tdbInput.equals("b"))
		{
			int bulletIndex;
			System.out.print("Bullet index: ");
			try
			{
				bulletIndex = userInput.nextInt();
				userInput.nextLine();
				
				if(bulletIndex < 1 || bulletIndex > presentation.getCursorSlide().getNumBullets()+1
						|| bulletIndex > 5)
					throw new IllegalArgumentException();
			}

			catch(InputMismatchException ime)
			{
				System.out.println("Invalid index");
				return;
			}

			catch(IllegalArgumentException iae)
			{
				System.out.println("Invalid index");
				return;
			}
			
			System.out.print("Delete or Edit? (d/e): ");
			tdbInput = userInput.nextLine().toLowerCase();
			
			if(tdbInput.equals("d"))
			{
				if(presentation.getCursorSlide().getBullet(bulletIndex) == null)
				{
					System.out.println("Bullet "+bulletIndex+" is already empty.");
					return;
				}
				
				presentation.getCursorSlide().setBullet(null, bulletIndex);
				presentation.setNumBullets(presentation.numBullets()-1);
				System.out.println("Bullet "+bulletIndex+" has been deleted.");
			}
			
			else if(tdbInput.equals("e"))
			{
				System.out.print("Bullet "+bulletIndex+": ");
				
				if(presentation.getCursorSlide().getBullet(bulletIndex) == null)
					presentation.setNumBullets(presentation.numBullets()+1);
				
				presentation.getCursorSlide().setBullet(userInput.nextLine(), bulletIndex);
				System.out.println("Bullet "+bulletIndex+" has been editted.");
			}
			
			else
			{
				System.out.println("Invalid option.");
				return;
			}
		}
		
		else
			System.out.println("Invalid option.");
	}
}