/*
 * Name: SukMin Sim
 * ID: 112190914
 * Recitation: 02
 */
package hw2;

public class Slide {
	
	//Member variables
	private static final int MAX_BULLETS = 5;
	private String title;
	private String[] bullets;
	private int numBullets;
	private double duration;
	
	/*
	 * Constructor for instantiation
	 */
	public Slide()
	{
		bullets = new String[MAX_BULLETS];
		duration = 0.0;
		numBullets = 0;
	}
	
	/*
	 * Constructor for variables setting
	 * @Param firstTitle
	 * 		set 'title' of this 'Slide'
	 * @Param firstDuration
	 * 		set 'duration' of this 'Slide'
	 * @Param
	 * 		set 'bullets[]' of this 'Slide'
	 * @throw IllgegalArgumentException
	 * 		If the length of firstBullets[] is greater than 5 or 
	 * 		   duration is equal or less than 0
	 */
	public Slide(String firstTitle, double firstDuration, String[]firstBullets)
	{
		try {
			if(firstBullets.length > 5)
				throw new IllegalArgumentException();
			
			else if(firstDuration <= 0)
				throw new IllegalArgumentException();
		}
		
		catch(IllegalArgumentException iae)
		{
			System.out.println("Invalid input for slide.");
			return;
		}
		
		title = firstTitle;
		duration = firstDuration;
		bullets = new String[MAX_BULLETS];
		numBullets = 0;
		
		for(int bulletIndex=0; bulletIndex < firstBullets.length; bulletIndex++)
		{
			if(firstBullets[bulletIndex] == null)
				break;
			
			bullets[bulletIndex] = firstBullets[bulletIndex];
			numBullets += 1;
		}
	}
	
	/*
	 * @return
	 * 		'title' of this 'Slide'
	 */
	public String getTitle()
	{return title;}
	
	/*
	 * Return a String value in 'bullets' array
	 * 
	 * @return
	 * 		'String' value stored in 'bullets[i-1]'
	 * @throws IllegalArgumentException
	 * 		User entered invalid bullet index or trying refer to null reference
	 */
	public String getBullet(int i)
	{
		try 
		{
			if(i < 1 || i > MAX_BULLETS)
				throw new IllegalArgumentException();	
		}
		
		catch(IllegalArgumentException iae)
		{
			System.out.println("Invalid index.");
			return null;
		}
			
		return bullets[i-1];
	}
	
	/* @return
	 * 	The number of bullets this slide has
	 */
	public int getNumBullets()
	{return numBullets;}
	
	/*
	 * @return
	 * 	'duration' value of this 'Slide'
	 */
	public double getDuration()
	{return duration;}
	
	/*
	 * Change the value of 'title'
	 * 
	 * @return
	 * 'title' of this 'Slide'
	 * 
	 * @Param newTitle
	 * 'title' should be replaced to newTitle
	 * 
	 * @throws IllegalArgumentException
	 * 	if the length of newTitle is over 15, it becomes invalid value.
	 */
	public void setTitle(String newTitle)
	{
		try	
		{
			if(newTitle == null || newTitle.length() > 15)
				throw new IllegalArgumentException();
		}
		
		catch(IllegalArgumentException iae)
		{
			System.out.println("Title should not be null or over 15 characters.");
			return;
		}
			
		title = newTitle;
	}
	
	/*
	 * Change the value of 'duration'
	 * 
	 * @Param newDuration
	 * 	'duration' should be changed to newDuration
	 * 
	 * @throws IllegalArgumentException
	 * 	if newDuration is equal or less than 0, it is invalid.
	 */
	public void setDuration(double newDuration)
	{
		try
		{
			if(newDuration <= 0)
				throw new IllegalArgumentException();
		}
		
		catch(IllegalArgumentException iae)
		{
			System.out.println("Invalid duration");
			return;
		}
		
		duration = newDuration;
	}
	
	/*
	 * Change the value of each bullet
	 * @Param newBullet
	 * 	If newBullet is null, it means user is not editing but deleting a bullet.
	 * 	Otherwise, user should be rewriting a bullet in the index.
	 * @Param i
	 * 	index of bullet to be edited. However, the least value for this should be 1.
	 * @throws IllegalArgumentException
	 * 	If user tries to refer the bullet before index 1 or greater than 5 or conditionally invalid.
	 */
	public void setBullet(String newBullet, int i)
	{
		try 
		{
			if(i < 1 || i > MAX_BULLETS || i > (numBullets+1))
				throw new IllegalArgumentException();	
		}
		
		catch(IllegalArgumentException iae)
		{
			System.out.println("Invalid index.");
			return;
		}
		
		i -= 1;
		
		if(newBullet == null)
		{
			bullets[i] = null;
			if(numBullets > i+1)
			{
				for(int emptyIndex = i+1; emptyIndex < numBullets; emptyIndex++)
					bullets[emptyIndex-1] = bullets[emptyIndex];
				
				bullets[numBullets-1] = null;
			}
			decreaseNumBullets();
		}
		
		else
		{
			if(bullets[i] == null)
				increaseNumBullets();
			bullets[i] = newBullet;
		}
	}
	
	/*
	 * Bullets are contained to a String variable.
	 * 
	 * @return
	 * 	Bullet information in this 'Slide' with good legibility.
	 */
	public String bulletInfoToString()
	{
		String slideInfo = "";
		slideInfo += "=".repeat(46)+"\n";
		slideInfo += getTitle()+"\n";
		slideInfo += "=".repeat(46)+"\n";
		
		for(int bulletIndex = 1; bulletIndex <= numBullets; bulletIndex++)
			slideInfo += bulletIndex+". "+getBullet(bulletIndex)+"\n";
		
		slideInfo += "=".repeat(46)+"\n";
		return slideInfo;
	}
	
	/*
	 *Statistic of this Slide. It contains title, duration, the number of bullets contained.
	 *
	 *@return
	 *	Overall information of this Slide with good legibility.
	 */
	public String toString()
	{
		String slideInfo = "";
		slideInfo += String.format("%-15s", title);
		slideInfo += String.format("%-15s", duration);
		slideInfo += numBullets;
		return slideInfo;
	}
	
	/*
	 * These two methods are in use of adjusting the number of bullets for better time complexity.
	 */
	public void increaseNumBullets()
	{numBullets += 1;}
	
	public void decreaseNumBullets()
	{numBullets -= 1;}
}
