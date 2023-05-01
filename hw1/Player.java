public class Player {
	
	//Member Variables
	private String name;
	private int numHits;
	private int numErrors;
	
	/*
	 * A constructor method instantiating a "Player" object 
	 * @parameter playerName
	 * 		player's name literally.
	 * @parameter playerHits
	 * 		The count of hits player has.
	 * @parameter playerError
	 * 		The count of errors player has.
	 */
	public Player(String playerName, int playerHits, int playerErrors)
	{
		name = playerName;
		numHits = playerHits;
		numErrors = playerErrors;
	}
	
	//Getter
	
	/*
	 * A getter method for player's name
	 * @return
	 * 		Player's name.
	 */
	public String getName()
	{return name;}
	
	/*
	 * A getter method for player's name
	 * @return
	 * 		Player's hits stat.
	 */
	public int getNumHits()
	{return numHits;}
	
	/*
	 * A getter method for player's name
	 * @return
	 * 		Player's errors stat.
	 */
	public int getNumErrors()
	{return numErrors;}
	
	//Setter
	
	/*
	 * Method gives Player a new name.
	 * @Param newName
	 * 		newName of Player
	 */
	public void setName(String newName)
	{name = newName;}
	
	/*
	 * Method updates Player's hits stat
	 * @Param newNumHits
	 * 		Player's hits stat would be updated to newNumHits.
	 */
	public void setNumHits(int newNumHits)
	{numHits = newNumHits;}
	
	/*
	 * Method updates Player's errors stat
	 * @parameter newNumErrors
	 * 		Player's errors stat would be updated to newNumErrors
	 */
	public void setNumErrors(int newNumErrors)
	{numErrors = newNumErrors;}
	
	/*
	 * @return
	 * 		A String containing the player's name,hits and errors in given format.
	 */
	public String toString()
	{
		String playerInfo = "";
		playerInfo += String.format("%-25s", getName());
		playerInfo += String.format("%-10s", getNumHits());
		playerInfo += getNumErrors();
		
		return playerInfo;
	}
}
