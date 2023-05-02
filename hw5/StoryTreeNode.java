package hw5;

public class StoryTreeNode {
	public static String WIN_MESSAGE = "YOU WIN";
	public static String LOSE_MESSAGE = "YOU LOSE";

	private String position;
	private String option;
	private String message;
	private StoryTreeNode leftChild;
	private StoryTreeNode middleChild;
	private StoryTreeNode rightChild;
	private StoryTreeNode parentNode;
	
	/*
	 * Constructor of StoryTreeNode class
	 * 
	 * @Param initPosition
	 * 	Location of this node in a tree.
	 * 
	 * @Param initOption
	 * 	Option to be shown to users before the node is selected.
	 * 
	 * @Param initMessage
	 * 	Message to be printed out when the node is selected.
	 */
	public StoryTreeNode(String initPosition, String initOption, String initMessage)
	{
		try {
			if(initPosition == null || initOption == null || initMessage == null)
				throw new IllegalArgumentException();
			
			else if(initPosition.length() < 1 || initOption.length() < 1 || initMessage.length() < 1)
				throw new IllegalArgumentException();
		}
		
		catch(IllegalArgumentException iae)
		{
			System.out.println("Invalid input");
			return;
		}
		
		position = initPosition;
		option = initOption;
		message = initMessage;
	}

	/*
	 * Returns the position of node
	 * 
	 * @return
	 * 	position
	 */
	public String getPosition()
	{return position;}
	
	/*
	 * Returns the option of node
	 * 
	 * @return
	 * 	option
	 */
	public String getOption()
	{return option;}

	/*
	 * Returns the message of node
	 * 
	 * @return
	 * 	message
	 */
	public String getMessage()
	{return message;}

	/*
	 * Returns one of the children nodes.
	 * 
	 * @Param position
	 * 	Minimum value:1
	 * 	Maximum value:3
	 * 	1 returns the leftmost child node.
	 * 	If the value or the child node is invalid, return null.
	 * 
	 * @return
	 * 	selected child node.
	 */
	public StoryTreeNode getChild(String position)
	{
		if(position.equals("1"))
			return leftChild;

		else if(position.equals("2"))
			return middleChild;

		else if(position.equals("3"))
			return rightChild;

		else
			return null;
	}
	
	/*
	 * Return the parent node
	 * 
	 * @return
	 * 	Parent node of this node.
	 */
	public StoryTreeNode getParent()
	{return parentNode;}
	
	/*
	 * Return the number of children(child) this node has.
	 * 
	 * @return
	 * 	The number of valid children nodes
	 */
	public int getNumChildren()
	{
		int count = 0;

		if(leftChild != null)
			count += 1;

		if(rightChild != null)
			count += 1;

		if(middleChild != null)
			count += 1;

		return count;
	}

	/*
	 * Update the position 
	 * 
	 * @Param newPosition
	 * 	New position to be set
	 */
	public void setPosition(String newPosition)
	{position = newPosition;}

	/*
	 * Update the option
	 * 
	 * @Param newOption
	 * 	New option to be set
	 */
	public void setOption(String newOption)
	{option = newOption;}

	/*
	 * Update the message
	 * 
	 * @Param newMessage
	 * 	New message to be set
	 */
	public void setMessage(String newMessage)
	{message = newMessage;}
	
	/*
	 * Set the parent node
	 * 
	 * @Param newParent
	 * 	New parent node to be set
	 */
	public void setParent(StoryTreeNode newParent)
	{parentNode = newParent;}

	/*
	 * Set the child node.
	 * 
	 * @Param position
	 * 	"1" means new child node will be set as leftmost child.
	 * 	"2" means new child node will be set as middle child.
	 *  "3" means new child node will be set as rightmost child.
	 *  
	 * @Param newChild
	 * 	New child node to set.
	 * 	
	 */
	public void setChild(String position, StoryTreeNode newChild)
	{
		if(position.equals("1"))
			leftChild = newChild;

		else if(position.equals("2"))
			middleChild = newChild;

		else if(position.equals("3"))
			rightChild = newChild;

		else
			System.out.println("Invalid position");
	}

	/*
	 * Judge if this node is leaf or not.
	 * 
	 * @return
	 * 	Return true if this node is leaf(has no child).
	 */
	public boolean isLeaf()
	{return (leftChild == null && rightChild == null);}

	/*
	 * Judge if this node is winning node or not.
	 * 
	 * @return
	 * 	Return true if this node is winning node
	 * 
	 * 	Conditions to be a winning node
	 * 	1. Should be leaf node.
	 * 	2. Should contain WIN_MESSAGE.
	 */
	public boolean isWinningNode()
	{
		if(!isLeaf())
			return false;

		if(!message.contains(WIN_MESSAGE))
			return false;

		return true;
	}

	
	/*
	 * Judge if this node is losing node or not.
	 * 
	 * @return
	 * 	Return true if this node is winning node
	 * 
	 * 	Conditions to be a losing node
	 * 	1. Should be leaf node.
	 * 	2. Should contain LOSE_MESSAGE.
	 */
	public boolean isLosingNode()
	{
		if(!isLeaf())
			return false;

		if(!message.contains(LOSE_MESSAGE))
			return false;

		return true;
	}

	/*
	 * Sort the children node to the left corner.
	 * If this node has only child, the child must be referred by leftChildNode.
	 * If this node has two children nodes, rightChildNode will be only node refers null.
	 */
	public void arrangeChildren()
	{
		if(getNumChildren() == 3)
			return;

		else if(isLeaf())
			return;

		for(int i = 1; i<=getNumChildren(); i++)
		{
			if(getChild(Integer.toString(i)) == null)
			{
				getChild(Integer.toString(i+1)).setPosition(getPosition()+"-"+Integer.toString(i));
				setChild(Integer.toString(i), getChild(Integer.toString(i+1)));
				setChild(Integer.toString(i+1), null);
			}
		}
	}

	/*
	 * Return position,option and message of this node.
	 */
	public String toString()
	{
		String nodeInfo = "";
		nodeInfo += position+" | ";
		nodeInfo += option+" | ";
		nodeInfo += message;

		return nodeInfo;
	}
	
	/*
	 * Returns the number of nodes in subtree.
	 * 
	 * @Param cursor
	 * 	The topmost node. This is not counted as a node in subtree.
	 * 
	 * @return
	 * 	The number of valid nodes in subtree.
	 */
	public int getNumSubNodes(StoryTreeNode cursor)
	{
		if(cursor == null || cursor.isLeaf())
			return 0;
		
		int totalNum = 0;
		totalNum += cursor.getNumChildren();
		
		for(int i = 1; i<=cursor.getNumChildren(); i++)
			totalNum += getNumSubNodes(cursor.getChild(Integer.toString(i)));
		
		return totalNum;
	}
	
	/*
	 * Returns the number of leaf nodes in subtree.
	 * 
	 * @Param cursor
	 * 	The topmost node.
	 * 
	 * @return
	 * 	The number of valid leaf nodes.
	 */
	public int getNumLeafNode(StoryTreeNode cursor)
	{
		int numLeaves = 0;
		
		if(cursor.isLeaf())
			return 1;
		
		for(int i = 1; i<=cursor.getNumChildren(); i++)
			numLeaves += getNumLeafNode(cursor.getChild(Integer.toString(i)));
		
		return numLeaves;
	}
	
	/*
	 * Returns the number of winning nodes in subtree.
	 * 
	 * @Param cursor
	 * 	The topmost node.
	 * 
	 * @return
	 * 	The number of valid winning nodes in subtree.
	 */
	public int getNumWinningSubNode(StoryTreeNode cursor)
	{
		int numWins = 0;
		
		if(cursor.isLeaf())
		{
			if(cursor.isWinningNode())
				return 1;
			
			else
				return 0;
		}
			
		for(int i = 1; i<=cursor.getNumChildren(); i++)
			numWins += getNumWinningSubNode(cursor.getChild(Integer.toString(i)));
		
		return numWins;
	}
}
