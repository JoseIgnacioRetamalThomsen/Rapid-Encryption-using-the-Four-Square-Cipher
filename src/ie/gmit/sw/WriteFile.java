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

	public WriteFile(BlockingQueue<CharSequence> queueP)

	{

		queue = queueP;

	}

	public void inputFileName(String fileName) throws FileNotFoundException
	{
		outputFile = new PrintWriter(new FileOutputStream(new File(fileName), true));
	}

	int countT = 0;

	@Override
	public void run()
	{
		CharSequence line;
		try

		{

			while (true)

			{
				// System.out.println(countT++ +" on run
				// WriteFile"+!interceptor.isEncryptionDone );

				line = queue.take();
				
				if (!(line instanceof Poison ))
				{
					outputFile.println(line);
				//	outputFile.write(line.toString().toCharArray());

				} else
				{
					break;
				}

			}

			outputFile.close();
			interceptor.stopTime();
			interceptor.displayTimeMS();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			
		}

	}

}
