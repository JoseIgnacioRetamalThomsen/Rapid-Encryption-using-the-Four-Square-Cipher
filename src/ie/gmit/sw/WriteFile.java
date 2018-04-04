/*
 * top key ZGPTFOIHMUWDRCNYKEQAXVSBL 
 * 
 * bot key MFNBDCRHSAXYOGVITUEWLQZKP
 */

package ie.gmit.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class WriteFile implements Runnable
{

	private final BlockingQueue<CharSequence> queue;

	PrintWriter outputFile;

	Interceptor interceptor;

	public WriteFile(BlockingQueue<CharSequence> queue)
	{

		this.queue = queue;

	}//WriteFile(BlockingQueue<CharSequence> queueP)

	
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
	 * Big-O: Time : O(n) n = number of lines in file.
	 * 
	 * Big-O: Space : O(n) n = queue size = EncryptAllA.QUEUE_SIZE = cons so O(1).
	 */
	@Override
	public void run()
	{
		CharSequence line;
		try
		{

			while (true)
			{
				line = queue.take();

				if (!(line instanceof Poison))
				{
					outputFile.println(line);

				} else
				{
					break;

				}

			} // while (true)

			outputFile.close();
			interceptor.stopTime();
			interceptor.displayTimeMS();

		} catch (InterruptedException e)
		{

			e.printStackTrace();

		} // try catch

	}// run()

}// WriteFile
