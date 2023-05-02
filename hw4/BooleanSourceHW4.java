/*
 * Name: SukMin Sim
 * ID: 112190914
 * Recitation: 02
 */
package hw4;
// Last updated: 3/8/2023 @ 12:39am
import java.io.*;

class BooleanSourceHW4
{
	private final static String RANDOM_FILE_FLAGS   = "hw4randomFlags.txt";
	private final static String RANDOM_FILE_NUMBERS = "hw4randomNumbers.txt";
    private final static int MAX_SIZE = 20000;
	
	private double probability;
	private int indexFlags   = 0;
	private int indexNumbers = 0;
	private double randomFLags[]   = new double[MAX_SIZE];
    private double randomNumbers[] = new double[MAX_SIZE];
	
    public BooleanSourceHW4(double p) throws IllegalArgumentException {
        if (p < 0.0 || p > 1.0)
            throw new IllegalArgumentException();
		readRandomFile(RANDOM_FILE_FLAGS, randomFLags);
		readRandomFile(RANDOM_FILE_NUMBERS, randomNumbers);
        probability = p;
    }
	
    public boolean occursHW4() {
		boolean flag;
		if (indexFlags < MAX_SIZE)
			flag = (randomFLags[indexFlags++] < probability);
		else 
			flag = (Math.random() < probability);
        return flag;
    }
    
	public double randomHW4() {
		if (indexNumbers < MAX_SIZE)
			return randomNumbers[indexNumbers++];
		else		
			return Math.random(); 
    }

	public static void readRandomFile(String filename, double[] randomArray) {
		try {
			String currentLine;
			BufferedReader objReader = new BufferedReader(new FileReader(filename));
			for (int i = 0; i < MAX_SIZE; i++) {
				currentLine = objReader.readLine();
				randomArray[i] = Double.parseDouble(currentLine);
			}
		} catch (IOException e) {
			System.out.println("An error occurred while reading from "+filename);
			e.printStackTrace();
		}
	}
}
