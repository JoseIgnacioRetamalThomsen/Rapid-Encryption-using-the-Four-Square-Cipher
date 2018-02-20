package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;

public class EncryptFive implements Runnable
{
	private final BlockingQueue<String> queueIn;
	private final BlockingQueue<String> queueOut;
	
	Interceptor interceptor;

	/**** Encryption variables ******/
	// Constants
	// Array with the 5x5 matrix with the 25 letters (all minus j)
	final char[][] mTL =
	{
			{ 'A', 'B', 'C', 'D', 'E' },
			{ 'F', 'G', 'H', 'I', 'K' },
			{ 'L', 'M', 'N', 'O', 'P' },
			{ 'Q', 'R', 'S', 'T', 'U' },
			{ 'V', 'W', 'X', 'Y', 'Z' } };

	// Bottom left and top right matrices
	char[][] mBL;

	char[][] mTR;

	char[] tranformedText;

	/*
	 * Constructor 
	 */
	EncryptFive(BlockingQueue<String> queueInP,BlockingQueue<String> queueOutP)
	{
		queueIn = queueInP;
		
		queueOut = queueOutP;

		mBL = new char[][]
		{
				{ 'M', 'F', 'N', 'B', 'D' },
				{ 'C', 'R', 'H', 'S', 'A' },
				{ 'X', 'Y', 'O', 'G', 'V' },
				{ 'I', 'T', 'U', 'E', 'W' },
				{ 'L', 'Q', 'Z', 'K', 'P' } };
		mTR = new char[][]
		{
				{ 'Z', 'G', 'P', 'T', 'F' },
				{ 'O', 'I', 'H', 'M', 'U' },
				{ 'W', 'D', 'R', 'C', 'N' },
				{ 'Y', 'K', 'E', 'Q', 'A' },
				{ 'X', 'V', 'S', 'B', 'L' } };
	}

	
	
	
	public boolean setKeyBL(char[] key)
	{
		if (key.length != 25)
		{
			return false;
		}

		int row = 0, col = 0;
		for (Character character : key)
		{
			mBL[row][col++] = character;
			if (col == 5)
			{
				col = 0;
				row++;
			}
		}

		return true;
	}

	public boolean setKeyTR(char[] key)
	{
		if (key.length != 25)
		{
			return false;
		}

		int row = 0, col = 0;
		for (Character character : key)
		{
			mTR[row][col++] = character;
			if (col == 5)
			{
				col = 0;
				row++;
			}
		}
		return true;
	}


	@Override
	public void run()
	{


		try

		{

			while (interceptor.isParseDone || queueIn.isEmpty() == false)

			{

				encryptLine(queueIn.take());

				

			}

		} catch (InterruptedException ex)

		{

			ex.printStackTrace();

		}

		interceptor.parseDone();
	}
	
	private void encryptLine(String line)
	{
		
		try
		{
			queueOut.put(line);
			
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
