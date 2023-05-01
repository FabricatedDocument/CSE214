package hw2;

public class SlideList {
	
	//Member variable
	private SlideListNode head;
	private SlideListNode tail;
	private SlideListNode cursor;
	
	private int totalNumSlides;
	private double totalDuration;
	private int totalNumBullets;

	/*
	 * Constructor method is just for instantiation.
	 */
	public SlideList()
	{
		totalNumSlides = 0;
		totalDuration = 0.0;
		totalNumBullets = 0;
	}

	/*
	 * Calculate the number of node connected.
	 * 
	 * @return
	 * 	the number of node.
	 */
	public int size()
	{return totalNumSlides;}

	/*
	 * Add the value of duration in each nodes.
	 * 
	 * @return
	 * 	the value of duration totally added.
	 */
	public double duration()
	{return Math.round(totalDuration*10) / 10.0;}
	
	/*
	 * Add the number of bullet(s) in each nodes.
	 * 
	 * @return
	 * 	the number of bullets added.
	 */
	public int numBullets()
	{return totalNumBullets;}
	
	/*
	 * This method is to manipulate the value of 'totalNumBullets'
	 * 
	 * @Param newNumBullets
	 * 	New value for 'totalNumBullets'
	 */
	public void setNumBullets(int newNumBullets)
	{totalNumBullets = newNumBullets;}
	
	/*
	 * Return 'Slide' object which is pointed by 'cursor'.
	 * 
	 * @return
	 * 	'Slide' object in 'cursor' node
	 */
	public Slide getCursorSlide()
	{return cursor.getData();}

	/*
	 * Set cursor reference to head
	 */
	public void resetCursorHead()
	{
		if(totalNumSlides == 0)
		{
			System.out.println("Empty Slideshow");
			return;
		}
		System.out.println("Cursor has been reset to the head");
		cursor = head;
	}
	
	/*
	 * Set cursor reference to next node of the current cursor node.
	 * 
	 * throw EndOfListException
	 * 	If cursor is pointing 'tail', the last node, this error will be thrown.
	 */
	public void cursorForward()
	{
		try	
		{
			if(cursor == tail)
				throw new EndOfListException();
		}

		catch(EndOfListException ele)
		{
			System.out.println("End of list cannot move forward");
			return;
		}

		cursor = cursor.getNext();
		

		System.out.println("The cursor moved forward to slide \""
				+getCursorSlide().getTitle()+"\"");
	}
	
	/*
	 * Set cursor reference to previous node of the current cursor node.
	 * 
	 * throw EndOfListException
	 * 	If cursor is pointing 'head', the first node, this error will be thrown.
	 */
	public void cursorBackward()
	{
		try	
		{
			if(cursor == head)
				throw new EndOfListException();
		}

		catch(EndOfListException ele)
		{
			System.out.println("End of list cannot move backward");
			return;
		}

		cursor = cursor.getPrev();

		System.out.println("The cursor moved backward to slide \""
				+getCursorSlide().getTitle()+"\"");
	}
	
	/*
	 * Add a new node as previous node of 'cursor'(current) node.
	 * Plus, the new node will be pointed by cursor.
	 * 
	 * @Param newSlide
	 * 	'Slide' object to be added to a new node.
	 * 
	 * @throws IllegalArgumentException
	 * 	If newSlide is referencing null, this error will be thrown.
	 */
	public void insertBeforeCursor(Slide newSlide)
	{
		try {
			if(newSlide == null)
				throw new IllegalArgumentException();
		}

		catch(IllegalArgumentException iae)
		{
			System.out.println("new slide is invalid.");
			return;
		}

		SlideListNode newSlideNode = new SlideListNode(newSlide);

		if(cursor == null)
		{
			cursor = newSlideNode;
			head = cursor;
			tail = cursor;
		}

		else if(cursor == head)
		{
			newSlideNode.setNext(cursor);

			head.setPrev(newSlideNode);
			head = newSlideNode;
			cursor = newSlideNode;
		}

		else
		{
			newSlideNode.setNext(cursor);
			newSlideNode.setPrev(cursor.getPrev());

			cursor.getPrev().setNext(newSlideNode);
			cursor.setPrev(newSlideNode);
			
			cursor = newSlideNode;
		}

		totalNumSlides += 1;
		totalDuration += newSlideNode.getData().getDuration();
		totalNumBullets += newSlideNode.getData().getNumBullets();
	}

	/*
	 * Add a new node after 'tail' node.
	 * 
	 * @Param newSlide
	 * 	'Slide' object to be added to a new node.
	 * 
	 * @throws IllegalArgumentException
	 * 	If 'newSlide' references null, this error will be thrown.
	 */
	public void appendToTail(Slide newSlide)
	{
		try 
		{
			if(newSlide == null)
			{
				throw new IllegalArgumentException();
			}
		}

		catch(IllegalArgumentException iae)
		{
			System.out.println("new slide is invalid.");
			return;
		}

		SlideListNode newSlideNode = new SlideListNode(newSlide);

		if(tail == null)
		{
			head = newSlideNode;
			cursor = newSlideNode;
		}

		else
		{
			newSlideNode.setPrev(tail);

			tail.setNext(newSlideNode);
		}

		tail = newSlideNode;

		totalNumSlides += 1;
		totalDuration += newSlideNode.getData().getDuration();
		totalNumBullets += newSlideNode.getData().getNumBullets();
	}
	
	/*
	 * Remove the node which is pointed by cursor.
	 * It will be deleted from the node list.
	 * 
	 * @throws EndofListException
	 * 	If node is empty, this error will be thrown.
	 */
	public Slide removeCursor()
	{
		try 
		{
			if(cursor == null)
				throw new EndOfListException();
		}
			
		catch(EndOfListException ele)
		{
			System.out.println("Empty slideshow.");
			return null;
		}
		
		Slide removedSlide = cursor.getData();
		
		if(size() == 1)
		{
			cursor = null;
			head = null;
			tail = null;
			System.out.println("Slide \""+removedSlide.getTitle()+"\" has been removed");
			return removedSlide;
		}
		
		if(cursor == head)
		{
			head = cursor.getNext();
			head.setPrev(null);
			cursor = head;
		}
		
		else if(cursor == tail)
		{
			tail = cursor.getPrev();
			tail.setNext(null);
			cursor = tail;
		}
		
		else
		{
			cursor.getPrev().setNext(cursor.getNext());
			cursor.getNext().setPrev(cursor.getPrev());
			cursor = cursor.getPrev();
		}
		
		totalNumSlides -= 1;
		totalDuration -= removedSlide.getDuration();
		totalNumBullets -= removedSlide.getNumBullets();
		
		System.out.println("Slide \""+removedSlide.getTitle()+"\" has been removed");
		return removedSlide;
	}
	
	/*
	 * Edit the duration of pointed 'Slide'
	 * 
	 * @Param targetSlide
	 * 	Slide object to be modified.
	 * 
	 * @Param newDuration
	 * 	new duration to be put in.
	 */
	public void editCursorDuration(Slide targetSlide, double newDuration) 
	{
		totalDuration += (newDuration - targetSlide.getDuration());
		targetSlide.setDuration(newDuration);
	}
	
	/*
	 * Setter method of totalDuration for better time complexity.
	 * 
	 * @Param newDuration
	 * 	value of new totalDuration
	 */
	public void setTotalDuration(double newDuration)
	{totalDuration = newDuration;}
	
	/*
	 * Summarize the whole information of all nodes which are connected to.
	 * 
	 * @return
	 * 	Summarized information of whole nodes and statistic of the nodes.
	 * 	It contains the number of slides, estimated time in minute and the number of bullets.
	 */
	public String toString()
	{
		String slideNodeInfo = "";
		SlideListNode slideNode = head;
		String durationToString = Double.toString(totalDuration);
		slideNodeInfo += "=".repeat(46)+"\n  ";
		slideNodeInfo += String.format("%-10s", "Slide");
		slideNodeInfo += String.format("%-15s", "Title");
		slideNodeInfo += String.format("%-15s", "Duration");
		slideNodeInfo += "Bullets\n";
		slideNodeInfo += "-".repeat(46)+"\n";
		
		for(int slideIndex = 1; slideIndex <= totalNumSlides; slideIndex+=1)
		{
			if(slideNode == cursor)
				slideNodeInfo += "-> ";
			else
				slideNodeInfo += "   ";
			
			slideNodeInfo += String.format("%-10s", slideIndex);
			slideNodeInfo += slideNode.getData().toString()+"\n";
			slideNode = slideNode.getNext();
		}
		
		slideNodeInfo += "=".repeat(46)+"\n";
		slideNodeInfo += "Total: "+ totalNumSlides + " slide(s), " + 
						durationToString.substring(0,durationToString.indexOf(".")+2) + " minute(s), " +
									totalNumBullets + " bullet(s)\n";
		slideNodeInfo += "=".repeat(46)+"\n";
		
		return slideNodeInfo;
	}
}
