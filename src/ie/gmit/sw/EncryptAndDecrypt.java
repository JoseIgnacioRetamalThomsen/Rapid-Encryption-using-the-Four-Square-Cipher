package ie.gmit.sw;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class EncryptAndDecrypt
{
	private static int DEFAULT_QUEUE_SIZE = 1000;

	private int queueSize;

	KeyManagerA keyManager;

	FileManager fileManager;

	public EncryptAndDecrypt(KeyManagerA keyManager, FileManager fileManager)
	{
		this.keyManager = keyManager;

		this.fileManager = fileManager;

		queueSize = DEFAULT_QUEUE_SIZE;

	}// EncryptAndDecrypt(KeyManagerA keyManager, FileManager fileManager)

	public int getQueueSize()
	{
		return queueSize;
	}

	public void setQueueSize(int queueSize)
	{
		this.queueSize = queueSize;
	}

	/*
	 * Big-O: Time : O(n), n = number of characters in the file. Because is O(n +
	 * 2*n1) where n1 = number of lines in file. because n = c * n1, c= constant
	 * (the total number of characters in the file is equal to the total number of
	 * lines multiply for a constant) so O(n + 2*n1)=O(n+2cn) = O((2c+1)n = O(Cn) =
	 * O(n) where C = constant = 2c +1
	 * 
	 * Big-O: Space : O(n + m) n = average number of characters per line, m =
	 * QUEUE_SIZE. If QUEUE_SIZE = constant => O(n).
	 */
	public void encryptA(Boolean isFromUrl)
	{

		BlockingQueue<CharSequence> qParseToEncrypt = new LinkedBlockingQueue<CharSequence>(queueSize);
		BlockingQueue<CharSequence> qEncryptToWrite = new LinkedBlockingQueue<CharSequence>(queueSize);

		ParseFile parseFile = new ParseFile(qParseToEncrypt);
		EncryptionA encryptFile = new EncryptionA(qParseToEncrypt, qEncryptToWrite, keyManager);
		WriteFile writeFile = new WriteFile(qEncryptToWrite);

		if (isFromUrl)
		{
			parseFile.setFileName(fileManager.urlFileName, isFromUrl);
		} else
		{
			parseFile.setFileName(fileManager.inputFileName, isFromUrl);
		}

		writeFile.inputFileName(fileManager.outputFileName);

		Thread t1 = new Thread(parseFile);

		Thread t2 = new Thread(encryptFile);

		Thread t3 = new Thread(writeFile);

		System.out.println("Starting encryption.");
		long startTime = System.nanoTime();

		t1.start();// Time: O(n1), n1 = number of lines in file. Space : O(n) n = number of average
					// characters per line.
		t2.start();// Time: O(n), n = number of characters in the file. Space : O(n) n = average
					// number of characters per line.
		t3.start();// Time: O(n1), n1 = number of lines in file. Space : O(1)

		try
		{
			t3.join();

		} catch (InterruptedException e)
		{

			e.printStackTrace();
		}

		System.out.println("Encryption done.");
		long stopTime = System.nanoTime();
		System.out.println("Time in  seconds: " + (stopTime - startTime) / Math.pow(10, 9));

		waitForEnter();

	}// encryptA(Boolean isFromUrl)

	public void decryptA(Boolean isFromUrl)
	{
		BlockingQueue<CharSequence> qParseToEncrypt = new LinkedBlockingQueue<CharSequence>(queueSize);
		BlockingQueue<CharSequence> qEncryptToWrite = new LinkedBlockingQueue<CharSequence>(queueSize);

		ParseFile parseFile = new ParseFile(qParseToEncrypt);
		DecryptionA encryptFile = new DecryptionA(qParseToEncrypt, qEncryptToWrite, keyManager);
		WriteFile writeFile = new WriteFile(qEncryptToWrite);

		if (isFromUrl)
		{
			parseFile.setFileName(fileManager.urlFileName, isFromUrl);
		} else
		{
			parseFile.setFileName(fileManager.inputFileName, isFromUrl);
		}

		// true);

		writeFile.inputFileName(fileManager.outputFileName);

		Thread t1 = new Thread(parseFile);

		Thread t2 = new Thread(encryptFile);

		Thread t3 = new Thread(writeFile);

		long startTime = System.nanoTime();

		t1.start();
		t2.start();
		t3.start();

		try
		{
			t3.join();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Decryption done.");
		long stopTime = System.nanoTime();
		System.out.println("Time in  seconds: " + (stopTime - startTime) / Math.pow(10, 9));

		waitForEnter();

	}// decryptA(Boolean isFromUrl)

	public void encryptB(Boolean isFromUrl)
	{

		BlockingQueue<CharSequence> qParseToEncrypt = new LinkedBlockingQueue<CharSequence>(queueSize);
		BlockingQueue<CharSequence> qEncryptToWrite = new LinkedBlockingQueue<CharSequence>(queueSize);

		ParseFile parseFile = new ParseFile(qParseToEncrypt);
		EncryptionB encryptFile = new EncryptionB(qParseToEncrypt, qEncryptToWrite, keyManager);
		WriteFile writeFile = new WriteFile(qEncryptToWrite);

		if (isFromUrl)
		{
			parseFile.setFileName(fileManager.urlFileName, isFromUrl);
		} else
		{
			parseFile.setFileName(fileManager.inputFileName, isFromUrl);
		}

		writeFile.inputFileName(fileManager.outputFileName);

		Thread t1 = new Thread(parseFile);

		Thread t2 = new Thread(encryptFile);

		Thread t3 = new Thread(writeFile);

		System.out.println("Starting encryption.");
		
		long startTime = System.nanoTime();

		t1.start();// Time: O(n1), n1 = number of lines in file. Space : O(n) n = number of average
					// characters per line.
		t2.start();// Time: O(n), n = number of characters in the file. Space : O(n) n = average
					// number of characters per line.
		t3.start();// Time: O(n1), n1 = number of lines in file. Space : O(1)

		try
		{
			t3.join();

		} catch (InterruptedException e)
		{

			e.printStackTrace();
		}

		System.out.println("Encryption done.");
		long stopTime = System.nanoTime();
		System.out.println("Time in  seconds: " + (stopTime - startTime) / Math.pow(10, 9));

		waitForEnter();

	}// encryptB(Boolean isFromUrl)

	public void decryptB(Boolean isFromUrl)
	{
		BlockingQueue<CharSequence> qParseToEncrypt = new LinkedBlockingQueue<CharSequence>(queueSize);
		BlockingQueue<CharSequence> qEncryptToWrite = new LinkedBlockingQueue<CharSequence>(queueSize);

		ParseFile parseFile = new ParseFile(qParseToEncrypt);
		DecryptionB encryptFile = new DecryptionB(qParseToEncrypt, qEncryptToWrite, keyManager);
		WriteFile writeFile = new WriteFile(qEncryptToWrite);

		if (isFromUrl)
		{
			parseFile.setFileName(fileManager.urlFileName, isFromUrl);
		} else
		{
			parseFile.setFileName(fileManager.inputFileName, isFromUrl);
		}

		// true);

		writeFile.inputFileName(fileManager.outputFileName);

		Thread t1 = new Thread(parseFile);

		Thread t2 = new Thread(encryptFile);

		Thread t3 = new Thread(writeFile);

		long startTime = System.nanoTime();
		
		t1.start();
		t2.start();
		t3.start();

		try
		{
			t3.join();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Encryption done.");
		long stopTime = System.nanoTime();
		System.out.println("Time in  seconds: " + (stopTime - startTime) / Math.pow(10, 9));

		waitForEnter();
		

	}// decryptB(Boolean isFromUrl)

	private void waitForEnter()
	{
		System.out.println("Press enter to continue.");
		try
		{
			System.in.read();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

}
