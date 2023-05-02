package hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;


enum GameState
{GAME_OVER_WIN, GAME_OVER_LOSE, GAME_NOT_OVER}

public class StoryTree {
	private StoryTreeNode root;
	private StoryTreeNode cursor;
	private GameState state;

	/*
	 * Constructor of StoryTree class
	 * All private variables are instantiated here.
	 */
	public StoryTree()
	{
		root = new StoryTreeNode("root", "root", "Hello, and welcome to Zork!");
		cursor = root;
		state = GameState.GAME_NOT_OVER;
	}

	/*
	 * Read given text file and build a story tree to return.
	 * If given file is invalid or contains wrong story format, it returns null.
	 * 
	 *  @Param filename
	 *   Name of the text file to load.
	 *  
	 *  @throws StringIndexOutOfBoundsException
	 *   If the story is written with wrong format, this error is thrown.
	 *  
	 *  @throws FileNotFoundException
	 *   If filename is invalid, this error is thrown.
	 *   
	 *  @throws IOException
	 *   If given text file has else problems, this error is thrown. 
	 *   
	 *  @return
	 *   Completely loaded StoryTree
	 */
	public static StoryTree readTree(String filename)
	{
		StoryTree loadedTree = new StoryTree();
		String currentPosition;
		String currentLine;
		String currentOption;
		String currentMessage;
		StoryTreeNode currentNode = null;
		BufferedReader objReader;

		try {
			objReader = new BufferedReader(new FileReader(filename));

			while(true)
			{	
				currentLine = objReader.readLine();
				if(currentLine == null)
				{
					objReader.close();
					break;
				}
					
				currentPosition = currentLine.substring(0, currentLine.indexOf("|")-1);
				currentLine = currentLine.substring(currentLine.indexOf("|")+2);

				currentOption = currentLine.substring(0, currentLine.indexOf("|")-1);
				currentLine = currentLine.substring(currentLine.indexOf("|")+2);

				currentMessage = currentLine;
				
				currentNode = new StoryTreeNode(currentPosition,
												currentOption,
												currentMessage);
				
				StoryTreeNode parentNode = loadedTree.getCursor();				
				
				for(int i = 0; i < currentPosition.length(); i++)
				{
					if(i == currentPosition.length()-1)
					{
						parentNode.setChild(currentPosition.substring(i), currentNode);
						parentNode.getChild(currentPosition.substring(i)).setParent(parentNode);
					}
					
					else
					{
						if(currentPosition.substring(i,i+1).equals("-"))
							continue;
						parentNode = parentNode.getChild(currentPosition.substring(i,i+1));
					}
				}
				loadedTree.resetCursor();
			}
			
			loadedTree.setRoot(loadedTree.getCursor().getChild("1"));
			loadedTree.resetCursor();
			return loadedTree;
		}
		
		catch(StringIndexOutOfBoundsException iob)
		{
			System.out.println("Stored story format is invalid.");
			System.out.println("Check your text file and try again.\n");
			return loadedTree;
		}

		catch (FileNotFoundException fnfe)
		{
				System.out.println("Failed to load game...");
				System.out.println("Check the path of the file.\n");
				System.out.println(filename);
				return loadedTree;
		}
		
		catch (IOException e) {
			System.out.println("An error occurred while reading from "+ filename);
			return loadedTree;
		}
		
		catch (NullPointerException npe)
		{
			System.out.println("An error occurred while reading from "+ filename);
			return loadedTree;
		}
	}

	/*
	 * Save all StoryTreeNode objects in a StoryTree to a text file.
	 * 
	 * @Param filename
	 * 	filename should be equal to the name of the file provided StoryTreeNode.
	 * 
	 * @Param tree
	 * 	StoryTree object containing whole stories to save.
	 * 
	 * @throws IllegalArgumentException
	 * 	If filename or StoryTree is empty or invalid, this error is thrown.
	 * 
	 * @throws IOException
	 * 	If a problem happen during save, this error is thrown.
	 */
	public static void saveTree(String filename, StoryTree tree)
	{
		StoryTree establishedTree = tree;
		establishedTree.resetCursor();
		StoryTreeNode currentNode = establishedTree.getCursor();
		String storyLines;
		
		try {
			if(filename == null || filename.length() == 0 || tree == null)
				throw new IllegalArgumentException();
		}

		catch(IllegalArgumentException iae)
		{
			System.out.println("Invalid input");
			return;
		}
		
		try
		{
			BufferedWriter objWriter;
			objWriter = new BufferedWriter(new FileWriter(filename));
			objWriter.write(establishedTree.parseSubTree(currentNode));
			objWriter.close();
		}
		
		catch(IOException ie)
		{
			System.out.println("An error occurred while reading from "+ filename);
			return;
		}
	}

	/*
	 * Returns current game state.
	 * 
	 * @return
	 * 	Current game state.
	 */
	public GameState getGameState()
	{
		if(cursor.isLosingNode())
			return GameState.GAME_OVER_LOSE;
		
		else if(cursor.isWinningNode())
			return GameState.GAME_OVER_WIN;
		
		else
			return GameState.GAME_NOT_OVER;
	}

	/*
	 * Returns the position value of cursor.
	 * 
	 * @return
	 * 	Position value of cursor node.
	 */
	public String getCursorPosition()
	{return cursor.getPosition();}
	
	/*
	 * Returns the arrayed options of cursor's children node.
	 * 
	 * @return
	 * 	The options of cursor node's children nodes
	 */
	public String[] getOptions()
	{
		String[] cursorChildrenInfo;
		int arraySize = 0;

		if(cursor.getChild("1") != null)
			arraySize +=1;

		if(cursor.getChild("2") != null)
			arraySize += 1;

		if(cursor.getChild("3") != null)
			arraySize += 1;

		cursorChildrenInfo = new String[arraySize];
		
		for(int i = 0; i < cursorChildrenInfo.length; i++)
		{	
			cursorChildrenInfo[i] = 
					cursor.getChild(Integer.toString(i+1)).getOption();
		}
		return cursorChildrenInfo;
	}

	/*
	 * Return cursor node.
	 * 
	 * @return
	 * 	Cursor of this tree.
	 */
	public StoryTreeNode getCursor()
	{return cursor;}

	/*
	 * Update the message of cursor node.
	 * 
	 * @Param newMessage
	 * 	updated message
	 */
	public void setCursorMessage(String newMessage)
	{cursor.setMessage(newMessage);}

	/*
	 * Update the option of cursor node.
	 * 
	 * @Param newOption
	 * 	updated option
	 */
	public void setCursorOption(String newOption)
	{cursor.setOption(newOption);}

	public void setRoot(StoryTreeNode newRoot)
	{root = newRoot;}
	
	/*
	 * Update cursor reference
	 * 
	 * @Param selectedNode
	 * 	New StoryTreeNode object to be referred by cursor.
	 * 
	 * @throws IllegalArgumentException
	 * 	If selectedNode is null, this error is thrown.
	 */
	public void setCursor(StoryTreeNode selectedNode)
	{
		try 
		{
			if(selectedNode == null)
				throw new IllegalArgumentException();
		
		}
		
		catch(IllegalArgumentException iae)
		{
			System.out.println("Cursor cannot refer null.");
			return;
		}
			
		cursor = selectedNode;
	}

	/*
	 * Change the reference of cursor to root node.
	 */
	public void resetCursor()
	{cursor = root;}
	
	/*
	 * Parse and return the position, option and message of all StoryTreeNode objects in cursor's subtree.
	 * 
	 * @Param cursor
	 * 	Start point to parse
	 * 
	 * @return
	 *  The information of each StoryTreeNode objects in string format. 
	 */
	public String parseSubTree(StoryTreeNode cursor)
	{	
		if(cursor == null)
		{
			return "";
		}
		
		String nodeInfo = "";
		nodeInfo += cursor.toString()+"\n";
		
		if(cursor.isLeaf())
			return nodeInfo;
			
		for(int i = 1; i<=cursor.getNumChildren(); i++)
		{
			StoryTreeNode child = cursor.getChild(Integer.toString(i));
			nodeInfo += parseSubTree(child);
		}
		
		return nodeInfo;
	}
	
}
