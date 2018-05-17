package ie.gmit.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
/*
 * Rapid Encryption using the Four-Square Cipher
 * Jose Ignacio Retamal
 *  G00351330@gmit.ie
 *
 * Write the result to file reading lines from queue.
 * 
 */


/*
 * Big-O: 
 * n = total number of characters in the input file.
 * m = total number of lines in the input file.
 * a = average number of characters per line in the input file.
 * p = number of characters in one line.
 *
 */
public class WriteFile implements Runnable
{

	private final BlockingQueue<CharSequence> queue;

	PrintWriter outputFile;

	//constructor
	public WriteFile(BlockingQueue<CharSequence> queue)
	{

		this.queue = queue;

	}// WriteFile(BlockingQueue<CharSequence> queueP)

	/*
	 * Big-O: Time : O(1)
	 * 
	 * Big-O: Space : O(1)
	 */
	public void inputFileName(String fileName)
	{
		try
		{
			outputFile = new PrintWriter(new FileOutputStream(new File(fileName), true));
		} catch (FileNotFoundException e)
		{

			e.printStackTrace();

		} // try catch

	}// inputFileName(String fileName)

	int countT = 0;

	/*
	 * Big-O: Time (1) : O(n), because O(m*a) but total characters = average
	 * character per line * total of lines( m*a = n) -> O(m*a)=O(n).
	 * 
	 * Running time estimation (2) : T(m,a)= m*a + m +1 , so for
	 * "WarAndPeace-LeoTolstoy" file that have 64,927 lines and let say an average
	 * of 80 characters per line the time will be 64,927*80 +64,927 +1 = 5259088
	 * computations, let say a processor does 3 billions operations per
	 * second(3*10^9) the time for complete the file will be 5259088/ 3*10^9 =
	 * 0.00175 seconds.
	 * 
	 * 
	 * Big-O: Space (3) : O(a*m) . In the worst case would be if all the characters
	 * are in one line so will be O(n) with n = total characters. If the characters
	 * are well distributed in each line a =constant so it become O(m) but m can be
	 * set and is constant as well so O(1).
	 * 
	 * Memory estimation (4) :there are a*l *charSize characters, a= average number
	 * of characters and l = size of queue, for the "WarAndPeace-LeoTolstoy" file
	 * let say that the average number of characters is 80 and with the default
	 * value of queue size of 10,000 and lets say that a char use 32 bits so
	 * 80*10,000*(32)bits= 25,600,000 bits = 25.6 MB.
	 */
	@Override
	public void run()
	{
		CharSequence line;
		try
		{

			while (true)// Big-O: Time : O(m)
			{
				//take line from queue
				line = queue.take();

				//stop when take the poison
				if (!(line instanceof Poison))
				{
					//print line to file
					outputFile.println(line);// Big-O: Time : O(p) because have to go character by character for write
												// the full string in the file 
												

				} else
				{
					break;

				}

			} // while (true)

			outputFile.close();

		} catch (InterruptedException e)
		{

			e.printStackTrace();

		} // try catch

	}// run()

}// WriteFile
