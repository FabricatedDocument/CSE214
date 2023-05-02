package hw5;

import java.util.Scanner;

public class Zork {
	public static Scanner userInput = new Scanner(System.in);

	public static void main(String[] args) {
		String menuOption;
		StoryTree story;
		String loadedFile;
		printWelcomeMessage();

		System.out.print("Please enter file name: ");
		loadedFile = userInput.nextLine();
		System.out.println("Loading game from file...\n");

		story = StoryTree.readTree(loadedFile);
		
		if(!story.getCursorPosition().equals("root"))
			System.out.println("File loaded!");
		
		while(true)
		{
			System.out.print("Would you like to edit (E), play (P) or quit (Q)? ");
			menuOption = userInput.nextLine().toLowerCase().replaceAll(" ", "");

			if(menuOption.equals("p"))
				playGame(story);

			else if(menuOption.equals("e"))
				editGame(story);

			else if(menuOption.equals("q"))
			{
				System.out.println("Game being saved to "+loadedFile+"...\n");
				StoryTree.saveTree(loadedFile, story);
				System.out.println("Save successful!\n");
				System.out.println("Program terminating normally.");
				break;
			}

		}
	}

	/*
	 * Let user play the game.
	 * Each turn, user will choose an option if the game is not over.
	 * If there is just single option at the turn, the option will be chosen automatically.
	 * 
	 * *EXTRA CREDIT IMPLEMENTATION
	 * 	User can notice the probability of win if enter "c" or "C" as the choice of the turn.
	 * 
	 * @Param loadedStory
	 * 	StoryTree object, containing the whole story.
	 */
	public static void playGame(StoryTree loadedStory)
	{
		StoryTree story = loadedStory;
		StoryTreeNode currentNode = loadedStory.getCursor();

		while(true)
		{
			System.out.println(currentNode.getMessage());

			if(currentNode.isLeaf())
			{
				loadedStory.resetCursor();
				System.out.println("Thanks for playing.\n");
				break;
			}

			String[] options = story.getOptions();
			for(int optionIndex = 0; optionIndex < options.length; optionIndex++)
				System.out.println((optionIndex+1)+")"+options[optionIndex]);

			System.out.print("\nPlease make a choice: ");

			String userChoice = userInput.nextLine().replaceAll(" ", "").toLowerCase();
			
			if(currentNode.getChild(userChoice) != null)
			{
				currentNode = currentNode.getChild(userChoice);
				loadedStory.setCursor(currentNode);
			}

			else if(userChoice.equals("c"))
			{
				double winProb = ((double)currentNode.getNumWinningSubNode(currentNode)/
						currentNode.getNumLeafNode(currentNode)*100);
				System.out.print("Probability of a win at this point: ");
				System.out.println(String.format("%.1f", winProb)+"%");
			}

			else
				System.out.println("Wrong choice.\n");
		}

	}


	/*
	 * Let user edit, add and remove the StoryTreeNode objects in StoryTree
	 * 
	 * @Param loadedStory
	 * 	StoryTree object to be edited.
	 * 
	 * *EXTRA CREDIT IMPLEMENTATION
	 *  User can change the cursor to the parent node of current node.
	 */
	public static void editGame(StoryTree loadedStory)
	{
		StoryTree story = loadedStory;
		String selectedOption;
		StoryTreeNode cursor = story.getCursor();

		while(true)
		{
			printEditorMenu();
			System.out.print("Please select an option: ");
			selectedOption = userInput.nextLine().toLowerCase().replaceAll(" ", "");
	
			if(selectedOption.equals("v"))
				editGame_V(cursor);

			else if(selectedOption.equals("s"))
			{
				StoryTreeNode childNode = editGame_S(cursor);

				if(childNode == null)
					continue;

				cursor = childNode;
			}

			else if(selectedOption.equals("o"))
				editGame_O(cursor);

			else if(selectedOption.equals("m"))
				editGame_M(cursor);

			else if(selectedOption.equals("a"))
				editGame_A(cursor);

			else if(selectedOption.equals("d"))
				editGame_D(cursor);

			else if(selectedOption.equals("r"))
			{
				story.resetCursor();
				cursor = story.getCursor();
			}

			else if(selectedOption.equals("p"))
			{
				if(cursor.getParent() == null)
				{
					System.out.println("Cursor is pointing root node.\n");
					continue;
				}

				cursor = cursor.getParent();
				System.out.println("Cursor moved to parent.");
			}

			else if(selectedOption.equals("q"))
			{
				loadedStory.resetCursor();
				break;
			}

			else
				System.out.println("Invalid option Input");

			System.out.println();
		}
	}


	/***Methods start with "editGame_" only should be called by editGame() method***/

	/*
	 * Print out the information of cursor node
	 * 
	 * @Param cursor
	 * 	Node to be printed out
	 */
	private static void editGame_V(StoryTreeNode cursor)
	{	
		String nodeInfo = "";
		nodeInfo += "Position: "+cursor.getPosition();
		nodeInfo += "\nOption: "+cursor.getOption();
		nodeInfo += "\nMessage: "+cursor.getMessage();

		System.out.println(nodeInfo);
	}

	/*
	 * Return one of the children nodes.
	 * If cursor is leaf node, it just prints out a message.
	 * 
	 * @Param cursor
	 * 	Parent node of returned StoryTreeNode.
	 * 
	 * @return
	 * 	One of the children nodes of cursor node.
	 */
	private static StoryTreeNode editGame_S(StoryTreeNode cursor)
	{
		if(cursor.isLeaf())
		{
			System.out.println("Cursor has no child.\n");
			return null;
		}

		String optionInput;
		System.out.print("Please select a child (MAX = "+cursor.getNumChildren()+"): ");
		optionInput = userInput.nextLine();

		if(cursor.getChild(optionInput) == null)
			System.out.println("Error. No child "+optionInput+" for the current node.");

		return cursor.getChild(optionInput);
	}

	/*
	 * Update the option of node.
	 * 
	 * @Param cursor
	 * 	StoryTreeNode to be updated
	 */
	private static void editGame_O(StoryTreeNode cursor)
	{
		System.out.print("Please enter a new option: ");
		String newOption = userInput.nextLine();

		if(newOption.length() < 1)
		{
			System.out.println("Invalid new option.");
			return;
		}

		cursor.setOption(newOption);
		System.out.println("Option set.");
	}

	/*
	 * Update the message of node
	 * 
	 * @Param cursor
	 * 	StoryTreeNode to be updated
	 */
	private static void editGame_M(StoryTreeNode cursor)
	{
		System.out.print("Please enter a new message: ");
		String newMessage = userInput.nextLine();

		if(newMessage.length() < 1)
		{
			System.out.println("Invalid new message.");
			return;
		}

		cursor.setMessage(newMessage);
		System.out.println("Message set.");
	}

	/*
	 * Add new child node if cursor has left children slot.
	 * 
	 * @Param cursor
	 * 	Parent node of the new node
	 */
	private static void editGame_A(StoryTreeNode cursor)
	{
		if(cursor.getChild("3") != null)
		{
			System.out.println("Error, this cursor has max children.");
			return;
		}

		String newOption;
		String newMessage;

		System.out.print("Enter an Option: ");
		newOption = userInput.nextLine();

		System.out.print("Enter an message: ");
		newMessage = userInput.nextLine();

		if(newOption.length() < 1 || newMessage.length() < 1)
		{
			System.out.println("Invalid input. Length should be at least 1.");
			return;
		}

		cursor.arrangeChildren();

		for(int i = 1; i<=3; i++)
		{
			String childPosition = Integer.toString(i);
			if(cursor.getChild(childPosition) == null)
			{
				cursor.setChild(childPosition, new StoryTreeNode(cursor.getPosition()+"-"+childPosition
						, newOption, newMessage));

				if(cursor.getPosition().equals("root"))
					cursor.getChild(childPosition).setPosition(childPosition);

				break;
			}
		}
	}

	/*
	 * Delete one of the children nodes and all subtree of it.
	 * 
	 * @Param cursor
	 * 	Parent node of the node to delete.
	 */
	private static void editGame_D(StoryTreeNode cursor)
	{
		if(cursor.isLeaf())
		{
			System.out.println("Error, Cursor has no child.");
			return;
		}

		String removedChildIndex;

		System.out.print("Please select a child (MAX = "+ cursor.getNumChildren() +"): ");
		removedChildIndex = userInput.nextLine();

		if(cursor.getChild(removedChildIndex) == null)
		{
			System.out.println("Error. No child"+removedChildIndex+"for the current node.");
			return;
		}

		cursor.setChild(removedChildIndex, null);
		cursor.arrangeChildren();
		System.out.println("Subtree deleted.");
	}

	private static void printWelcomeMessage()
	{System.out.println("Hello and welcome to Zork!\n");}

	private static void printEditorMenu()
	{
		String menus = "";
		menus += "Zork Editor:\n";
		menus += " ".repeat(4)+"V: View the cursor's position, option and message.\n";
		menus += " ".repeat(4)+"S: Select a child of this cursor (options are 1, 2, and 3).\n";
		menus += " ".repeat(4)+"O: Set the option of the cursor.\n";
		menus += " ".repeat(4)+"M: Set the message of the cursor.\n";
		menus += " ".repeat(4)+"A: Add a child StoryNode to the cursor.\n";
		menus += " ".repeat(4)+"D: Delete one of the cursor's children and all its descendants.\n";
		menus += " ".repeat(4)+"R: Move the cursor to the root of the tree.\n";
		menus += " ".repeat(4)+"P: Return the cursor to parent node.\n";
		menus += " ".repeat(4)+"Q: Quit editing and return to main menu.\n";

		System.out.println(menus);
	}
}
