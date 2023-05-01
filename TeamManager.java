/*
 * Name: SukMin Sim
 * ID: 112190914
 * Recitation: 02
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class TeamManager {
	public static final int MAX_TEAMS = 5;
	private static Team[] teams = new Team[MAX_TEAMS];
	private static Scanner userInput = new Scanner(System.in);
	private static int selectedTeamNumber = 0; // The index of the team

	public static void main(String args[])
	{
		printWelcomeMessage(); //Literally prints welcome message.
		String userMenuInput = " "; // User's input would be gotten here
		
		for(int uninstantiated = 0; uninstantiated < MAX_TEAMS; uninstantiated++)
			teams[uninstantiated] = new Team();
	
		
		while(true)
		{
			try {
				
			if(userMenuInput.length() != 0)
				printWholeMenu();
			
			userMenuInput = userInput.nextLine();
			userMenuInput = userMenuInput.toUpperCase().replaceAll(" ", "");

			if(userMenuInput.equals("A"))
				goOptionA();
			
			else if(userMenuInput.equals("G"))
				goOptionG();
			
			else if(userMenuInput.equals("L"))
				goOptionL();
			
			else if(userMenuInput.equals("R"))
				goOptionR();
			
			else if(userMenuInput.equals("P"))
				goOptionP();
			
			else if(userMenuInput.equals("S"))
				goOptionS();
			
			else if(userMenuInput.equals("T"))
			{
				selectedTeamNumber = goOptionT();
				userInput.nextLine();
			}
			
			else if(userMenuInput.equals("C"))
				goOptionC();
				
			else if(userMenuInput.equals("E"))
				goOptionE();
				
			else if(userMenuInput.equals("U"))
				goOptionU();
			
			else if(userMenuInput.equals("Q"))
				break;
			
			else
				System.out.println("'"+userMenuInput.toUpperCase()+"'"+" is invalid menu input.");
			
			}
			
			catch(ArrayIndexOutOfBoundsException aoe)	{
				System.out.println("player position should be 1~40, and "
						+ "team position should be 1~5.");
			}
			
			catch(InputMismatchException ime)	{
				System.out.println("Input should be an 'integer', try again.");
				userInput.nextLine();
			}
			
			finally	{
				System.out.println();
			}
		}
	}
	
	private static void printWelcomeMessage()
	{
		System.out.println("Welcome to TeamManager!\n");
		System.out.println("Team 1 is currently selected.\n");
	}

	private static void printWholeMenu()
	{
		System.out.println("//menu");
		System.out.println("Please select an option:");
		System.out.println("A)  Add Player.");
		System.out.println("G)  Get Player stats.");
		System.out.println("L)  Get leader in a stat.");
		System.out.println("R)  Remove a player.");
		System.out.println("P)  Print all players.");
		System.out.println("S)  Size of team.");
		System.out.println("T)  Select team");
		System.out.println("C)  Clone team");
		System.out.println("E)  Team equals");
		System.out.println("U)  Update stat.");
		System.out.println("Q)  Quit.\n");
		System.out.print("Select a menu option: ");
	}
	
	private static void goOptionA()
	{
		int sizeBefore;
		int sizeAfter;
		
		System.out.print("Enter the player's name: ");
		String playerName = userInput.nextLine();

		System.out.print("Enter the number of hits: ");
		int playerHits = userInput.nextInt();

		System.out.print("Enter the number of errors: ");
		int playerErrors = userInput.nextInt();

		System.out.print("Enter the position: ");
		int playerPosition = userInput.nextInt();

		sizeBefore = teams[selectedTeamNumber].size();
		
		teams[selectedTeamNumber].addPlayer(
				new Player(playerName,playerHits,playerErrors)
				,playerPosition);
		
		sizeAfter = teams[selectedTeamNumber].size();
		
		if(sizeBefore != sizeAfter)
		{
			System.out.println("Player added: "
				+teams[selectedTeamNumber].getPlayer(playerPosition).getName()+" - "
				+teams[selectedTeamNumber].getPlayer(playerPosition).getNumHits()+" hits, "
				+teams[selectedTeamNumber].getPlayer(playerPosition).getNumErrors()+" errors");
		}
		
		userInput.nextLine();
	}

	private static void goOptionG()
	{
		Team selectedTeam = teams[selectedTeamNumber];
		
		System.out.print("Enter the position: ");
		int playerPosition = userInput.nextInt();
		
		try {
		System.out.println(teams[selectedTeamNumber].getPlayer(playerPosition).getName()+" - "
				+teams[selectedTeamNumber].getPlayer(playerPosition).getNumHits()+" hits, "
				+teams[selectedTeamNumber].getPlayer(playerPosition).getNumErrors()+" errors");
		}
		
		catch(NullPointerException npe)	{
			System.out.println("There is no player in the position");
		}
		
		userInput.nextLine();
	}
	
	private static void goOptionL()
	{
		System.out.print("Enter the stat: ");
		String stat = userInput.nextLine();
		Player leader;
		
		try	{
		if(stat.toLowerCase().equals("hits"))
		{
			leader = teams[selectedTeamNumber].getLeader(0);
			
			System.out.println("\nLeader in hits: "+leader.getName()+" - "
					+ leader.getNumHits() + " hits, "
					+ leader.getNumErrors() + " Errors");
			
		}
		
		else if(stat.toLowerCase().equals("errors"))
		{
			leader = teams[selectedTeamNumber].getLeader(1);

			System.out.println("\nLeader in errors: "+leader.getName()+" - "
					+ leader.getNumHits() + " hits, "
					+ leader.getNumErrors() + " Errors");
		}
		
		else
			leader = teams[selectedTeamNumber].getLeader(2);
		
		}
		
		catch(NullPointerException npe)
		{
			System.out.println("There is no player in this team.");
		}
	}
	
	private static void goOptionR()
	{
		System.out.print("Enter the position: ");
		int positionToRemove = userInput.nextInt();
		String deletedName;
		
		try	{
			deletedName = teams[selectedTeamNumber].getPlayer(positionToRemove).getName();
			
			System.out.println("Player Removed at position " + positionToRemove+"\n");
			System.out.println(teams[selectedTeamNumber].getPlayer(positionToRemove).getName()
					+" has been removed from the team.");
			
			teams[selectedTeamNumber].removePlayer(positionToRemove);
		}
		
		catch(NullPointerException npe)	{
			System.out.println("No player at position "+positionToRemove+" to remove.");
		}
		
		userInput.nextLine();
	}
	
	private static void goOptionP()
	{
		System.out.print("Select team index: ");
		int teamNumber = userInput.nextInt()-1;
		teams[teamNumber].printAllPlayers();
		
		userInput.nextLine();
	}
	
	private static void goOptionS()
	{
		Team currentTeam = teams[selectedTeamNumber];
		
		if(currentTeam.size() > 1)
			System.out.println("There are "+currentTeam.size()+" players in the current Team.");
		
		else
			System.out.println("There is "+currentTeam.size()+" player in the current Team.");
	}
	
	private static int goOptionT()
	{
		System.out.print("Enter team index to select: ");
		int newTeamNumber = userInput.nextInt();
		
		try	{
			if(newTeamNumber < 1 || newTeamNumber >MAX_TEAMS)
				throw new IllegalArgumentException();
		}
		
		catch(IllegalArgumentException iae)	{
			System.out.println("Invalid index for team.");
			return selectedTeamNumber;
		}
		
		System.out.println("Team "+newTeamNumber+" has been selected.");
		
		return newTeamNumber-1;
	}
	
	private static void goOptionC()
	{
		System.out.print("Select team to clone from: ");
		int copied = userInput.nextInt();
		
		System.out.print("Select team to clone to: ");
		int pasted = userInput.nextInt();
		
		teams[pasted-1] = (Team)(teams[copied-1].clone());
		
		System.out.println("Team "+copied+" has been copied to Team "+pasted);
		userInput.nextLine();
	}
	
	private static void goOptionE()
	{
		System.out.print("Select first team index: ");
		int firstIndex = userInput.nextInt()-1;
		
		System.out.print("Select second team index: ");
		int secondIndex = userInput.nextInt()-1;
		
		if(teams[firstIndex].equals(teams[secondIndex]))
			System.out.println("These teams are equal");
		
		else
			System.out.println("These teams are not equal");
		
		userInput.nextLine();
	}
	
	private static void goOptionU()
	{
		Team teamToUpdate = teams[selectedTeamNumber];
		int positionOfPlayerToUpdate = 0;
		
		System.out.print("Enter the player name to update: ");
		String playerName = userInput.nextLine();
		
		for(int playerPosition=1; playerPosition <= teamToUpdate.size(); playerPosition++)
		{
			if(teamToUpdate.getPlayer(playerPosition).getName().equals(playerName))
				positionOfPlayerToUpdate = playerPosition;
		}
		
		
		System.out.print("Enter stat to update: ");
		String statToUpdate = userInput.nextLine().toLowerCase();
		
		if(statToUpdate.equals("hits"))
		{
			System.out.print("Enter the new number of hits: ");
			int newHits = userInput.nextInt();
			
			if(newHits < 0)
			{
				System.out.println("Invalid update error");
				return;
			}
			
			if(positionOfPlayerToUpdate == 0)
			{
				System.out.println("Player not found.");
				return;
			}
			
			teamToUpdate.getPlayer(positionOfPlayerToUpdate).setNumHits(newHits);
			System.out.println("Updated "+teamToUpdate.getPlayer(positionOfPlayerToUpdate).getName());
			userInput.nextLine();
		}
		
		else if(statToUpdate.equals("errors"))
		{
			System.out.print("Enter the new number of errors: ");
			int newErrors = userInput.nextInt();
			
			if(newErrors < 0)
			{
				System.out.println("Invalid update error");
				return;
			}
			
			if(positionOfPlayerToUpdate == 0)
			{
				System.out.println("Player not found.");
				return;
			}
			
			teamToUpdate.getPlayer(positionOfPlayerToUpdate).setNumErrors(newErrors);
			System.out.println("Updated "+teamToUpdate.getPlayer(positionOfPlayerToUpdate).getName());
			userInput.nextLine();
		}
		
		else
		{
			try {
				throw new IllegalArgumentException();	
			}
			
			catch(IllegalArgumentException iae) {
				System.out.println("No such statistic");
			}
		}
	}
	
}
