/*
 * Name: SukMin Sim
 * ID: 112190914
 * Recitation: 02
 */

public class Team {
	public static final int MAX_PLAYERS = 40; //Max capacity of players.
	
	private Player[] players; //Player objects will be stored here.
	
	/*
	 * A constructor method to instantiate Team.
	 */
	public Team()
	{
		players = new Player[MAX_PLAYERS];
	}
	
	/*
	 * Another constructor method to instantiate Team.
	 * @parameter loadedPlayers
	 * 		The array of Player to be stored in players[]
	 */
	public Team(Player[] loadedPlayers)
	{
		players = loadedPlayers;
	}
	
	/*
	 * Make a copy of this team. The fluctuation of the copied Team will not affect to other Team.
	 * @return
	 * 		New Team object containing same players in same order. 
	 */
	public Object clone()
	{
		Player[] clonedPlayers = new Player[MAX_PLAYERS];
	
		for(int playerPosition = 1; playerPosition<=size(); playerPosition++)
		{
			
			Player cloningPlayer = getPlayer(playerPosition);
			
				clonedPlayers[playerPosition-1] = 
						new Player(cloningPlayer.getName(),
									cloningPlayer.getNumHits(),
									cloningPlayer.getNumErrors());
		}
			
		return new Team(clonedPlayers);
	}
	
	/*
	 * Determines whether two Teams are same or not.
	 * 	If they are equal, it means each two teams have same players of same stats in same order.
	 * @parameter obj
	 * 		A Team object to be compared.
	 * @return
	 * 		If two teams are equal.
	 */
	public boolean equals(Object obj)
	{
		if(((Team)obj).toString().equals(toString()))
		{
			return true;
		}
		
		return false;
	}
	
	/*
	 * Measure the number of players in this Team.
	 * @return
	 * 		the number of player object(s) in players
	 */
	public int size()
	{
		int sizeOfTeam = 0;
		
		for(int i = 0; i<MAX_PLAYERS; i++)
			if(players[i] != null)
				sizeOfTeam += 1;
		
		return sizeOfTeam;
	}
	
	/*
	 * Add a new Player to players
	 * @parameter P
	 * 		A Player object to be added.
	 * @parameter position
	 * 		the location of Player object to be added.
	 * @throws IllegalArgumentException
	 * 		Indicates position is not in range of 1 ~ size()+1 or it's over 40.
	 */
	public void addPlayer(Player p, int position) throws IllegalArgumentException
	{	
		System.out.println(position);
		try {
			
		if(size() == MAX_PLAYERS) //If team size is max
		{
			System.out.println("There is no more space in this team.");
			return;
		}
		
		
		if(position < 1 || position > size()+1) //If position is not in valid range
			throw new IllegalArgumentException();
		
		if(p.getName().length() > 20)
		{
			System.out.println("Length of name should be under 20.");
			return;
		}
		
		}
		
		catch(IllegalArgumentException iae) {
			System.out.println("Invalid position for adding the new player");
			return;
		}
			
		position -= 1; //Least valid value of 'position' is 1 but, the index of array starts from '0'
		
		if(players[position] == null)
			players[position] = p;
		
		else
		{
			Player temp = players[position];
			players[position] = p;
			addPlayer(temp, position+2);
		}
	}
	
	/*
	 * Remove a Player object in players and fill out the blank appropriately.
	 * @parameter position
	 * 		the position of Player object to be removed.
	 * @throws IllegalArgumentException
	 * 		Indicates position is not in range of 1~size()
	 */
	public void removePlayer(int position) throws IllegalArgumentException
	{
		try	{
		if(position < 1 || position > size()) // If 'position' is out of valid range
			throw new IllegalArgumentException();
		}
		
		catch (IllegalArgumentException iae) {
			System.out.println("Invalid position for removing the new player");
		}
		
		position -= 1;
		
		for(int i = position; i < size()-1; i++)
			players[i] = players[i+1];
		
		
		players[size()-1] = null;
		
	}
	
	/*
	 * Get the Player object in the given position.
	 * @parameter position
	 * 		index of Player object to be selected
	 * @return
	 * 		the Player object located in given position.
	 * @throws IllegalArgumentException
	 * 		Indicates position is not in range of 1~size()
	 */
	public Player getPlayer(int position) throws IllegalArgumentException
	{
		try	{
			if(position < 1 || position > size()) // If 'position' is out of valid range
				throw new IllegalArgumentException();
			}
			
			catch (IllegalArgumentException iae) {
				System.out.println("Position is not in valid range.");
			}
		
		return players[position-1];
	}
	
	/*
	 * Method returns a Player object has the best(highest or lowest) stat
	 * @parameter stat
	 * 		the standard of the best Player object 0 means 'hits' and 1 means'errors'
	 * @return
	 * 		A Player object has the highest 'hits' or the lowest 'errors'.
	 */
	public Player getLeader(int stat)
	{
		Player leader = players[0];
		int bestStat;
		
		if(stat == 0)
		{
			bestStat = leader.getNumHits();
			for(int playerIndex = 0; playerIndex<size(); playerIndex++)
				if(players[playerIndex].getNumHits() > bestStat)
				{
					leader = players[playerIndex];
					bestStat = leader.getNumHits();
				}
		}
		
		else if(stat == 1)
		{
			bestStat = leader.getNumErrors();
			for(int playerIndex = 1; playerIndex<size(); playerIndex++)
				if(players[playerIndex].getNumErrors() < bestStat)
				{
					leader = players[playerIndex];
					bestStat = leader.getNumErrors();
				}
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
			
			
		return leader;
	}
	
	/*
	 * Simply prints out all of the information of the players in this team
	 */
	public void printAllPlayers()
	{System.out.println(toString());}
	
	/*
	 * Method has a String containing all of the information of all Player object in players array.
	 * 
	 * @return
	 * 		Whole summary of this Team object as String
	 */
	public String toString()
	{
		String teamInfo = "";
		
		teamInfo += String.format("%-10s", "Players#")
		  + String.format("%-25s", "Name")
		  + String.format("%-10s", "Hits") 
		  + "Errors\n";
		
		teamInfo += "-".repeat(teamInfo.length()+2)+"\n";
		
		int playerNumber = 0;
		while(true)
		{
			if(playerNumber == MAX_PLAYERS || players[playerNumber] == null)
				break;
			
			teamInfo += String.format("%-10s", playerNumber+1);
			teamInfo += players[playerNumber].toString() +"\n";
			playerNumber += 1;
		}
		
		
		return teamInfo;
	}
}
