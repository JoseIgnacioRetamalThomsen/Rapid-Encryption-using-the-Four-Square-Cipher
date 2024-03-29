package ie.gmit.sw;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.BlockingQueue;

/*
 * Rapid Encryption using the Four-Square Cipher
 * Jose Ignacio Retamal
 * G00351330@gmit.ie
 *
 * Parse file, method that read the input file, can be from system path or URL
 * 
 * 
 */


/*
 * Big-O: 
 * n = total number of characters in the input file.
 * m = total number of lines in the input file.
 * a = average number of characters per line in the input file.
 * p = number of characters in one line.
 */

public class ParseFile implements Runnable
{

	private final BlockingQueue<CharSequence> queue;

	BufferedReader fileIn;

	String line;

	//constructor
	public ParseFile(BlockingQueue<CharSequence> queueP)
	{
		queue = queueP;

	}

	/*
	 * Read the file path or URL and open the file MUST BE ALWAYS CALL BEFORE run();
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
	 * Read line and put it into the BlockingQueue inputFilename() must be be call
	 * before.
	 */
	/*
	 * Big-O: Time: O(n) n = Please see at the analysis in WriteFile (1) the same
	 * apply.
	 * 
	 * Estimation : same estimation than WriteFile (2) apply, well here will be(not
	 * the difference 2m to (1)) T(m,a)= m*a + 2m +1 ,(m number of lines, a =
	 * average characters per line), so 64927*80 +2*64,927 +1 = 5.324.015 operations
	 * at 3 billion operation per second give 5.324.015/(3*10^9) = 0.00177 seconds.
	 * Please look at WriteFile (2) for the estimations.
	 * 
	 * 
	 * Big-O: Space : Same than WriteFile (3). Worst case : O(n) With fair
	 * distributed characters per line O(1). Please look at WriteFile (3) for
	 * analysis.
	 * 
	 * Estimation: same than WriteFile (4).
	 */
	@Override
	public void run()
	{

		try
		{
			while ((line = fileIn.readLine()) != null) // Big-O Time: O(n*m), n = total lines in the file, m average
														// number of
														// characters per line, fileIn.readLine() have to read
														// characters one by one.
			{
				try
				{
					//put line in queue
					queue.put(line);// O(1) queue put on front operation.

				} catch (InterruptedException e)
				{

					e.printStackTrace();
				}
			}

			try
			{
				//ad poison to queue
				queue.put(new Poison());

			} catch (InterruptedException e)
			{

				e.printStackTrace();
			} // while ((line = fileIn.readLine()) != null)

			fileIn.close();

		} catch (IOException e)// | InterruptedException e)
		{

			e.printStackTrace();

		} // try catch

	}// public void run()

}// class ParseFile
