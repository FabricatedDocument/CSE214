/*
 * Name: SukMin Sim
 * ID: 112190914
 * Recitation: 02
 */
package hw2;

public class SlideListNode {
	private Slide data;
	private SlideListNode next;
	private SlideListNode prev;
	
	/*
	 * Constructor of Node form
	 * 
	 * @Param initData
	 * 	If this is null, constructor should do nothing.
	 * 	Or initData(Slide) is valid(not null), it would be allocated as a wrapped 'Slide'.
	 */
	public SlideListNode(Slide initData)
	{
		try
		{
			if(initData == null)
				throw new IllegalArgumentException();
		}
		
		catch(IllegalArgumentException iae)
		{
			System.out.println("Initial data should be instantiated.");
			return;
		}
		data = initData;
	}
	
	//Getter Methods
	
	/*
	 * Get 'Slide' of this node.
	 * 
	 * @return
	 * 	'Slide' type object
	 */
	public Slide getData()
	{return data;}
	
	/*
	 * Get next node not a 'Slide' object
	 * 
	 * @return
	 * 	node after this node.
	 */
	public SlideListNode getNext()
	{return next;}
	
	/*
	 * Get previous node not a 'Slide' object 
	 * 
	 * @return
	 * 	node before this node.
	 */
	public SlideListNode getPrev()
	{return prev;}
	
	//Setter Methods
	
	/*
	 * Set a new 'Slide' as wrapped data.
	 * 
	 * @Param newData
	 * 	Likewise, Null data address allocation will be rejected for flexibility.
	 * 
	 * @throw IllegalArgumentException
	 * 	To prevent the occurrence of Exception for null pointer reference, handle the input of null data.
	 */
	public void setData(Slide newData)
	{
		try
		{
			if(newData == null)
				throw new IllegalArgumentException();
		}
		
		catch(IllegalArgumentException iae)
		{
			System.out.println("new data should not be 'null'. ");
			return;
		}
		
		data = newData;
	}
	
	/*
	 * Set or remove 'next' node
	 * 
	 * @Param newNext
	 * 	If newNext is referencing a valid node, the node will be set as next node of this.
	 * 	If newNext is referencing null, simply it's removing next node.
	 */
	public void setNext(SlideListNode newNext)
	{next = newNext;}
	
	/*
	 * Set or remove 'previous' node
	 * 
	 * @Param newPrev
	 * 	If newPrev is referencing a valid node, the node will be set as next node of this.
	 * 	If newNext is referencing null, simply it's removing previous node.
	 */
	public void setPrev(SlideListNode newPrev)
	{prev = newPrev;}
	
}
