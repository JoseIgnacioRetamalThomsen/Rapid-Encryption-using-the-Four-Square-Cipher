package ie.gmit.sw;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.BlockingQueue;

public class ParseFile implements Runnable
{

	private final BlockingQueue<CharSequence> queue;

	BufferedReader fileIn;

	String line;
	
	Interceptor interceptor;
	
	ParseFile(BlockingQueue<CharSequence> queueP)
	{
		queue = queueP;
		
	}

	/*
	 * Read the file path or URL and open the file
	 * MUST BE ALWAYS CALL BEFORE run();
	 * 
	 */
	/*
	 * Big-O: Time : O(1) 
	 * 
	 * Big-O: Space : O(1) .
	 */
	public void setFileName(String pathOrURL, boolean isURL)
	{
		try
		{
			if (isURL)
			{
				URL url;

				url = new URL(pathOrURL);

				fileIn = new BufferedReader(new InputStreamReader(url.openStream()));

			} else
			{

				fileIn = new BufferedReader(new InputStreamReader(new FileInputStream(pathOrURL)));

			}

		} catch (FileNotFoundException e)
		{

			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 *  Read line and put it into the BlockingQueue
	 *  inputFilename() must be be call before.
	 */
	/*
	 * Big-O: Time : O(n) n = number of lines in file.
	 * 
	 * Big-O: Space : O(n) n = queue size = EncryptAllA.QUEUE_SIZE = cons so O(1).
	 */
	@Override
	public void run()
	{
		interceptor.startTime();
		try
		{
			while ((line = fileIn.readLine()) != null)
			{
				try
				{
					queue.put(line);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try
			{
				queue.put(new Poison());
			} catch (InterruptedException e)
			{
				
				e.printStackTrace();
			}
			
			fileIn.close();
			
		} catch (IOException e)//| InterruptedException e)
		{
		
			e.printStackTrace();
			
		}//try catch
		
		
	}//public void run()

}// class ParseFile 
