package ie.gmit.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class WriteFile implements Runnable
{

	private final BlockingQueue<String> queue;
	
	PrintWriter outputFile;
	
	Interceptor interceptor; 
	
	WriteFile(BlockingQueue<String> queueP)

	{

		queue = queueP;

	}
	
	public void inputFileName(String fileName) throws FileNotFoundException
	{
		outputFile = new PrintWriter(new FileOutputStream(new File(fileName), true));
	}
	
	
	int countT =0;
	@Override
	public void run()
	{
		try

		{

			while (!interceptor.isEncryptionDone || queue.isEmpty() == false)

			{
				//System.out.println(countT++ +" on run WriteFile"+!interceptor.isEncryptionDone  );
				
				outputFile.println(((BlockingQueue)queue).take());

				

			}

			outputFile.close();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			interceptor.writeDone();
		}
		
	}
	
	

}
