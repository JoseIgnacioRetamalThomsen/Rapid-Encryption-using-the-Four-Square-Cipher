package ie.gmit.sw;

/*
 * Rapid Encryption using the Four-Square Cipher
 * Jose Ignacio Retamal
 * G00351330@gmit.ie
 *
 * Runner Class
 * 
 * 
 */

public class Runner
{

	public static void main(String[] args)
	{
		//try catch for out of memory error
		try
		{

			UserInterface userInterface = new UserInterface();
			userInterface.start();
			
		} catch (Error error)
		{
			error.printStackTrace();
			System.out.println(
					"The program have crash, probably for memory problem. You can try to set The memory constraint to a smaller value.");
			
			
		}

	}// main(String[] args)

}// Runner

// http://www.textfiles.com/etext/FICTION/