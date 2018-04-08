package ie.gmit.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class WriteFile implements Runnable
{

	private final BlockingQueue<CharSequence> queue;

	PrintWriter outputFile;

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
	 * Big-O: Time (1) : O(n) n = total number of characters in the file, because
	 * O(m*a) where m is the number of lines in the input file and a is the average
	 * number number of characters in each line, so total characters = average
	 * character per line * total of lines -> O(m*a)=O(n).
	 * 
	 * Running time estimation (2) : T(m,a)= m*a + m +1 ,(m number of lines, a =
	 * average characters per line), so for "WarAndPeace-LeoTolstoy" file that have
	 * 64,927 lines and let say an average of 80 characters per line the time will
	 * be 64,927*80 +64,927 +1 = 5259088 computations, let say a processor does 3
	 * billions operations per second(3*10^9) the time for complete the file will be
	 * 5259088/ 3*10^9 = 0.00175 seconds.
	 * 
	 * 
	 * Big-O: Space (3) : O(n*m) n = average characters per line, m =  lines in
	 * the file. In the worst case would be if all the characters are in one line so
	 * will be O(n) with n = total characters. In a normal scenario with an average
	 * number of characters per line a -> O(n*a) -> where n is the number of lines
	 * which it can be assigned so is constant -> O(1)
	 * 
	 * Memory estimation (4) :there are a*l *charSize characters, a= average number
	 * of characters and l = size of queue, for the "WarAndPeace-LeoTolstoy" file
	 * let say that the average number of characters is 80 and with the default
	 * value of queue size of 10,000 and lets say that a char use 32 bits so
	 * 80*10,000*(32)bits= 25,600,600 = 25.6 MB.
	 */
	@Override
	public void run()
	{
		CharSequence line;
		try
		{

			while (true)// Big-O: Time : O(m) where m is the number of lines in the input file(the file
						// that ParseFile
						// class had read).
			{
				line = queue.take();

				if (!(line instanceof Poison))
				{
					outputFile.println(line);// Big-O: Time : O(p) where p is the number of characters in the String
												// "line", because
												// have to go character by character for write the full string in the
												// file

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
