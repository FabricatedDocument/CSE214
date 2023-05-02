/*
 * Name: SukMin Sim
 * ID: 112190914
 * Recitation: 02
 */
package hw3;

/*
 * Three levels of strength would be used for this assignment.
 * Fragile cannot endure moderate and sturdy cargo
 * Moderate cargo cannot endure sturdy cargo
 * Sturdy cargo can endure all of containers.
 */
enum CargoStrength
{FRAGILE, MODERATE, STURDY}

public class Cargo {
	private String name;
	private double weight;
	private CargoStrength strength;
	
	/*
	 * Constructor method of Cargo you can create Cargo object using this method.
	 * 
	 * @Param initName
	 * 	Name of Cargo
	 * 
	 * @Param initWeight
	 * 	weight of the Cargo. It should be over 0
	 * 
	 * @Param initStrength
	 * 	Every cargos should have their own strength. One of "Fragile", "Moderate" and "Sturdy"
	 * 
	 * @throws IllegalArgumentException
	 * 	Following conditions should be met
	 * - initName should not be null reference.
	 * - initWeight should be over 0.
	 * - initWeight should be numeric value.
	 * - initStrength should be null reference.
	 */
	public Cargo(String initName, double initWeight, CargoStrength initStrength)
	{
		try {
			if(initName == null || initWeight <= 0 || initStrength == null)
				throw new IllegalArgumentException();
		}
		
		catch(IllegalArgumentException iae)
		{
			System.out.println("Invalid input.");
			return;
		}
		
			name = initName;
			weight = initWeight;
			strength = initStrength;
	}
	
	/*
	 * Return the name of this Cargo
	 * 
	 * @return
	 * 	An instantiated String value which is used as name
	 */
	public String getName()
	{return name;}
	
	/*
	 * Return the weight of this Cargo
	 * 
	 * @return
	 * 	Weight of this Cargo in "double" type
	 */
	public double getWeight()
	{return weight;}
	
	/*
	 * Return the Strength of this Cargo
	 * 
	 * @return
	 * 	The value of Strength this Cargo has
	 */
	public CargoStrength getStrength()
	{return strength;}
	
	/*
	 * Return the Strength as numerical value
	 * Cargo which has bigger numeric value can endure equal or smaller Cargo.
	 * 
	 * @ return
	 * 	Numerically converted Strength value.
	 */
	public int getNumericStrength()
	{
		if(strength == CargoStrength.FRAGILE)
			return 0;
		
		else if(strength == CargoStrength.MODERATE)
			return 1;
		
		else
			return 2;
	}
	
	/*
	 * The abbreviation of CargoStrength will be used as Cargo information for this work.
	 * 
	 * @return
	 * 	A String value which containing the abbreviation of Cargo Strength
	 */
	public String toString() 
	{	
		if(strength == CargoStrength.FRAGILE)
			return "F";
		
		else if(strength == CargoStrength.MODERATE)
			return "M";
		
		else
			return "S";
	}
}
