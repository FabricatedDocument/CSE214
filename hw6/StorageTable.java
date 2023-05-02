/*
 * Name: SukMin Sim
 * ID: 112190914
 * Recitation: 02
 */
package hw6;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class StorageTable extends Hashtable<Integer,Storage> implements Serializable{
	private static final long serialVersionUID = 21406L;
	
	/*
	 * Register a new Storage on the Hash table.
	 * 
	 * @Param id
	 * 	Value of Storage id
	 * 
	 * @Param stuff
	 * 	Value of Storage contents
	 */
	public void putStorage(int id, Storage stuff)
	{super.put(id, stuff);}
	
	/*
	 * Returns a Storage object through id value.
	 * 
	 * @Param id
	 * 	Key for mapping
	 * 
	 * @return
	 * 	Mapped Storage object
	 */
	public Storage getStorage(int id)
	{return super.get(id);}
	
	/*
	 * Returns a List containing the valid Integer type of keys on Hash table.
	 * 
	 * @return
	 * 	List of keys sorted in ascending order.
	 */
	public List<Integer> getSortedKeySet()
	{
		List<Integer> keys = new ArrayList<Integer>(super.keySet());
		Collections.sort(keys);
		return keys;
	}
	
	/*
	 * Generate the information of StorageTable.
	 * The information of all Storage objects on the table will be returned in String.
	 * 
	 * @return
	 * 	Information of StorageTable.
	 */
	public String toString()
	{
		String tableInfo = "";
		tableInfo += getStorageInfoFrame();
		
		for(int key:getSortedKeySet())
			tableInfo += super.get(key).toString()+"\n";
		return tableInfo;
	}
	
	public String getStorageInfoFrame()
	{
		String frame = "Box#         Contents                        Owner\n";
		frame += "-".repeat(64)+"\n";
		return frame;
	}
}
