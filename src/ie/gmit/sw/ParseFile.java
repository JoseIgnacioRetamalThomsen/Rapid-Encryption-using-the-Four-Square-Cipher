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
	public void inputFileName(String pathOrURL, boolean isURL)
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
		}finally
		{
			//here for no risk and endless loop;
			interceptor.parseDone();
			
		}
		
		
	}

	/*
	 * private StringBuilder parsedFile;
	 * 
	 * public ParseFile() { super(); this.parsedFile = new StringBuilder(); }
	 * 
	 * public StringBuilder getParseFile() { return parsedFile; }
	 * 
	 * public void setParseFileObject(StringBuilder parseFile) { this.parsedFile =
	 * parseFile; }
	 * 
	 * public void setParseFilePointer(StringBuilder sb) { sb = parsedFile; }
	 * 
	 * public void clearParseFile() { this.parsedFile = new StringBuilder(); }
	 * 
	 * public void parseFile(String pathOrURL, boolean isUrl) throws IOException {
	 * String line; BufferedReader fileIn; int counter = 0; if (isUrl) { URL url =
	 * new URL(pathOrURL); fileIn = new BufferedReader(new
	 * InputStreamReader(url.openStream())); while ((line = fileIn.readLine()) !=
	 * null) { System.out.println(line); System.out.println(counter++);
	 * 
	 * }
	 * 
	 * } else {
	 * 
	 * fileIn = new BufferedReader(new InputStreamReader(new
	 * FileInputStream(pathOrURL)));
	 * 
	 * while ((line = fileIn.readLine()) != null) { System.out.println(line);
	 * System.out.println(counter++);
	 * 
	 * }
	 * 
	 * } }
	 * 
	 * public static void main(String[] args) throws IOException {
	 * System.out.println("here"); new
	 * ParseFile().parseFile("warandpeace-leotolstoy.txt", false); }
	 */
}
