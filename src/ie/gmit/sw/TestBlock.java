package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class TestBlock
{

	public static void main(String[] args)
	{
		long startTimef1 = System.nanoTime();
		new Setup().main();
		long startTime1 = System.nanoTime();
		System.out.println("finish");
		//System.out.println(startTime1-startTimef1);
	}
}

class Producer implements Runnable
{

	String line;
	BufferedReader fileIn;
	private final BlockingQueue<String> queue;

	conector c;

	Producer(BlockingQueue q)
	{
		queue = q;
	}

	public void run()
	{
		c.startTime();
		try
		{

			fileIn = new BufferedReader(new InputStreamReader(new FileInputStream("warandpeace-leotolstoy.txt")));

			while ((line = fileIn.readLine()) != null)
			{
				queue.put(line);
			}
		} catch (InterruptedException | IOException ex)
		{
			ex.printStackTrace();
		}
		c.isw = false;
	}

	// Object produce() { }
}

class Consumer implements Runnable
{
	private final BlockingQueue<String> queue;
	conector c;
	int counter = 0;
	String line;
StringBuilder sb = new StringBuilder();;
	Consumer(BlockingQueue q)
	{
		queue = q;
	}

	public void run()
	{
		try
		{
			while (c.isw || queue.isEmpty() == false)
			{
				consume(queue.take());
				counter++;
			}
		} catch (InterruptedException ex)
		{
			ex.printStackTrace();
		}
		c.stopTime();
		c.dTime();
		System.out.println(sb.length());
	}

	void consume(Object x)
	{
		sb.append((String)x);
		//System.out.println(counter + " " + x);
	}
}

class Setup
{
	void main()
	{
		BlockingQueue<String> q = new LinkedBlockingQueue<String>();
		//BlockingQueue<String> q = new LinkedTransferQueue<String>();
		
		//BlockingQueue<String> q = new PriorityBlockingQueue <String>();
		//BlockingQueue<String> q = new PriorityBlockingQueue <String>();
		
		//BlockingQueue<String> q = new ArrayBlockingQueue<String>(1000); 
		Producer p = new Producer(q);
		Consumer c1 = new Consumer(q);
		conector con = new conector();
		p.c = con;
		c1.c = con;
		// Consumer c2 = new Consumer(q);
		Thread t1 = new Thread(p);
		Thread t2 =new Thread(c1);
		t1.start();
		t2.start();
		
		// new Thread(c2).start();
	}
}

class conector
{
	boolean isw = true;
	long startTimef1;
	long startTime1;
	public void startTime()
	{
		 startTimef1 = System.nanoTime();
	}
	public void stopTime()
	{
		startTime1 = System.nanoTime();
	}
	public void dTime()
	{
		System.out.println((startTime1-startTimef1)/Math.pow(10, 9));
	}
}