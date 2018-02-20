package ie.gmit.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class WriteFile implements Runnable
{

	private final BlockingQueue<String> queue;
	
	PrintWriter outputFile;
	
	Interceptor interceptor; 
	
	WriteFile(BlockingQueue queueP)

	{

		queue = queueP;

	}
	
	public void inputFileName(String fileName) throws FileNotFoundException
	{
		outputFile = new PrintWriter(new FileOutputStream(new File(fileName), true));
	}
	
	@Override
	public void run()
	{
		try

		{

			while (interceptor.isEncryptionDone || queue.isEmpty() == false)

			{

				outputFile.print(queue.take());

				

			}

			outputFile.close();
		} catch (InterruptedException ex)

		{

			ex.printStackTrace();

		}finally
		{
			interceptor.writeDone();
		}
		
	}
	
	

}
