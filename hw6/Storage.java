/*
 * Name: SukMin Sim
 * ID: 112190914
 * Recitation: 02
 */
package hw6;

import java.io.Serializable;

public class Storage implements Serializable{
	private static final long serialVersionUID = 21406L;
	
	private int id;
	private String client;
	private String contents;
	
	/*
	 * Constructor method of Storage.
	 * 
	 * @Param initId
	 * 	Unique number of each boxes for identification.
	 * 	This value will also play as a Key for mapping.
	 * 
	 * @Param initClientName
	 * 	Name of the owner of this(Storage).
	 * 
	 * @Param initContents
	 * 	simply described contents in Storage.
	 * 
	 * @throws IllegalArgumentException
	 * 	If clientName of contents is null, this error is thrown.
	 * 
	 */
	public Storage(int initId, String initClientName, String initContents)
	{
		try
		{
			if(initClientName == null || initContents == null)
			{
				System.out.println("Invalid Storage info.");
				throw new IllegalArgumentException();
			}
		}
		
		catch(IllegalArgumentException iae)
		{return;}
		
		id = initId;
		client = initClientName;
		contents = initContents;
	}
	
	/*
	 * Getter method for Id
	 * 
	 * @return
	 * 	Id value of Storage object
	 */
	public int getId()
	{return id;}
	
	/*
	 * Getter method for client name
	 * 
	 * @return
	 * 	Name of the client.
	 */
	public String getClientName()
	{return client;}
	
	/*
	 * Getter method for contents
	 * 
	 * @return
	 * 	Contents in Storage object.
	 */
	public String getContents()
	{return contents;}
	
	/*
	 * Id setter method
	 * 
	 * @Param newId
	 * 	New Id value
	 */
	public void setId(int newId)
	{id = newId;}
	
	/*
	 * Update client name
	 * 
	 * @Param newName
	 * 	New value for client name.
	 */
	public void setClientName(String newName)
	{client = newName;}
	
	/*
	 * Update contents
	 * 
	 * @Param newContents
	 * 	New contents
	 */
	public void setContents(String newContents)
	{contents = newContents;}
	
	/*
	 * Generate appropriate format of Storage information.
	 * 
	 * @return
	 * 	Formatted Storage information.
	 */
	public String toString()
	{
		String storageInfo = "";
		storageInfo += String.format("%-13s", id);
		storageInfo += String.format("%-32s", contents);
		storageInfo += client;
		
		return storageInfo;
	}
	
	
}
