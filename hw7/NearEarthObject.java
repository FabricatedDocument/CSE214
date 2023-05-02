/*
 * Name: SukMin Sim
 * ID: 112190914
 * Recitation: 02
 */
package hw7;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

public class NearEarthObject {
	
	int referenceID;
	String name;
	double absoluteMagnitude;
	double averageDiameter;
	boolean isDangerous;
	Date closestApproachDate;
	double missDistance;
	String orbitingBody;
	
	/*
	 * Constructor of NearEarthObject
	 * 
	 * @AllParam
	 * 	All of the parameters in this method are instantiating the variables.
	 * 
	 * @Param initName
	 * 	If the length of this value over 26, only the first 26 characters will be remained.
	 */
	public NearEarthObject(int initRef, String initName, double initAbsMag, double initAvgDia, boolean initIsDanger,
			String initAppDate, double initMissDis, String initOrbBody)
	{
		referenceID = initRef;
		name = initName;
		absoluteMagnitude = initAbsMag;
		averageDiameter = initAvgDia;
		isDangerous = initIsDanger;
		SimpleDateFormat inputDate = new SimpleDateFormat("yyyy-MM-dd");
		try
		{closestApproachDate = inputDate.parse(initAppDate);}
		catch(ParseException pe)
		{System.out.println("Wrong data format");}
		missDistance = initMissDis;
		orbitingBody = initOrbBody;
		
		if(name.length() > 26)
			name = name.substring(0,26);
	}
	
	/*
	 *  ***GETTER METHODS***
	 */
	
	/*
	 * Get reference ID value
	 * @return
	 * 	referenceID of planet.
	 */
	public int getReferenceID()
	{return referenceID;}
	
	/*
	 * Get the name of Planet
	 * @return
	 * 	name of the planet
	 */
	public String getName()
	{return name;}
	
	/*
	 * Get the absolute magnitude
	 * @return absolute magnitude value
	 */
	public double getAbsoluteMagnitude()
	{return absoluteMagnitude;}
	
	/* ***Same explanation for other GETTER METHODS below*** */
	public double getAverageDiameter()
	{return averageDiameter;}
	
	public boolean getIsDangerous()
	{return isDangerous;}
	
    public String getOrbitingBody() 
    {return orbitingBody;}
	
    public Date getClosestApproachDate() 
    {return closestApproachDate;}

    public double getMissDistance() 
    {return missDistance;}
    
    /*
     * ***SETTER METHODS***
     */
    
    /*
     * Set the value of miss distance of the planet.
     * @Param newMissDistance
     * 	New 'missDistance' value.
     */
    public void setMissDistance(double newMissDistance) 
    {missDistance = newMissDistance;}
    
    /* ***Same explanation for other SETTER METHODS below*** */
    public void setOrbitingBody(String newOrbitingBody) 
    {orbitingBody = newOrbitingBody;}

    public void setReferenceID(int newReferenceID) 
    {this.referenceID = newReferenceID;}

    public void setName(String newName) 
    {name = newName;}

    public void setAbsoluteMagnitude(double newAbsoluteMagnitude) 
    {absoluteMagnitude = newAbsoluteMagnitude;}

    public void setAverageDiameter(double newAverageDiameter) 
    {averageDiameter = newAverageDiameter;}

    public void setIsDangerous(boolean newIsDangerous) 
    {isDangerous = newIsDangerous;}

    public void setClosestApproachDate(Date newClosestApproachDate) 
    {closestApproachDate = newClosestApproachDate;}
    
    /*
     * Formatted overall information of the planet
     * 
     * @return
     * 	Summarized, rounded value of each planet properties.
     */
    public String toString()
    {
    	String planetInfo = "";
    	SimpleDateFormat convertedDate = new SimpleDateFormat("MM-dd-yyyy");
    	
    	planetInfo += String.format("%-9s", referenceID);
    	planetInfo += String.format("%-28s", name);
    	planetInfo += String.format("%-8s", String.format("%.1f", absoluteMagnitude));
    	planetInfo += String.format("%-10s", String.format("%.3f", averageDiameter));
    	planetInfo += String.format("%-9s", isDangerous);
    	planetInfo += String.format("%-13s", convertedDate.format(closestApproachDate));
    	planetInfo += String.format("%-12s", String.format("%.0f", missDistance));
    	planetInfo += orbitingBody;
    	 	
    	return planetInfo;
    }
}
