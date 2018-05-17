package ie.gmit.sw;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*
 * Rapid Encryption using the Four-Square Cipher
 * Jose Ignacio Retamal
 * G00351330@gmit.ie
 *
 * Encrypt and decrypt methods 
 * Each method create the queues when run an set the queue size.
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

public class EncryptAndDecrypt
{
	private static int DEFAULT_QUEUE_SIZE = 10000;

	protected int queueSize;

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
	 * Big-O: Time : We look at the 3 run methods WriteFile+EncryptA+parseFile = O
	 * (n) +O(n)+ O(n) = O(n)
	 * 
	 * Estimation : the higher of the thread that is EncryptionA = 0.0650893 seconds
	 * 
	 * Big-O: Space : O(n) n = queuseSize, this constraint the memory usage of all
	 * the classes.
	 * 
	 * Estimation : 2 queue + memory used for each class, this is pretty much
	 * WriteFile.run(0 +parseFile.run() + EncryptA.encryptLine() +
	 * EncryptA.createMatrices() + some extra that is not relevant because for the
	 * run in ecnryptMatrices the same queues are use so 25,600,000 + 25,600,000 +
	 * 21,376 + 4,576 = 51,206,712 kits = 51 MB
	 */
	public void encryptA(Boolean isFromUrl)
	{

		// create queues
		BlockingQueue<CharSequence> qParseToEncrypt = new LinkedBlockingQueue<CharSequence>(queueSize);
		BlockingQueue<CharSequence> qEncryptToWrite = new LinkedBlockingQueue<CharSequence>(queueSize);

		// create runnable classes
		ParseFile parseFile = new ParseFile(qParseToEncrypt);
		EncryptionA encryptFile = new EncryptionA(qParseToEncrypt, qEncryptToWrite, keyManager);
		WriteFile writeFile = new WriteFile(qEncryptToWrite);

		// set file name
		if (isFromUrl)
		{
			parseFile.setFileName(fileManager.getUrlFileName(), isFromUrl);
		} else
		{
			parseFile.setFileName(fileManager.getInputFileName(), isFromUrl);
		}

		writeFile.inputFileName(fileManager.getOutputFileName());

		// create threads
		Thread t1 = new Thread(parseFile);
		Thread t2 = new Thread(encryptFile);
		Thread t3 = new Thread(writeFile);

		// start timing
		System.out.println("Starting encryption.");
		long startTime = System.nanoTime();

		// start threads
		t1.start();// Time: O(n) , space: worst O(n) , normal O(1)
		t2.start();// Time: O(n) , space: worst O(n) , normal O(1)
		t3.start();// Time: O(n) , space: worst O(n) , normal O(1)

		// yield last thread to main
		try
		{
			t3.join();

		} catch (InterruptedException e)
		{

			e.printStackTrace();
		}

		// stop timing
		System.out.println("Encryption done.");
		long stopTime = System.nanoTime();
		System.out.println("Time in  seconds: " + (stopTime - startTime) / Math.pow(10, 9));

		waitForEnter();

	}// encryptA(Boolean isFromUrl)

	/*
	 * Big-O same than encryptA
	 */

	public void decryptA(Boolean isFromUrl)
	{
		// create queues
		BlockingQueue<CharSequence> qParseToEncrypt = new LinkedBlockingQueue<CharSequence>(queueSize);
		BlockingQueue<CharSequence> qEncryptToWrite = new LinkedBlockingQueue<CharSequence>(queueSize);

		// create runnable classes
		ParseFile parseFile = new ParseFile(qParseToEncrypt);
		DecryptionA encryptFile = new DecryptionA(qParseToEncrypt, qEncryptToWrite, keyManager);
		WriteFile writeFile = new WriteFile(qEncryptToWrite);

		// set file name
		if (isFromUrl)
		{
			parseFile.setFileName(fileManager.getUrlFileName(), isFromUrl);
		} else
		{
			parseFile.setFileName(fileManager.getInputFileName(), isFromUrl);
		}

		// true);

		writeFile.inputFileName(fileManager.getOutputFileName());

		// create threads
		Thread t1 = new Thread(parseFile);
		Thread t2 = new Thread(encryptFile);
		Thread t3 = new Thread(writeFile);

		// start timing
		System.out.println("Starting encryption.");
		long startTime = System.nanoTime();

		// start threads
		t1.start();// Time: O(n) , space: worst O(n) , normal O(1)
		t2.start();// Time: O(n) , space: worst O(n) , normal O(1)
		t3.start();// Time: O(n) , space: worst O(n) , normal O(1)

		// yield last thread to main
		try
		{
			t3.join();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// stop timing
		System.out.println("Decryption done.");
		long stopTime = System.nanoTime();
		System.out.println("Time in  seconds: " + (stopTime - startTime) / Math.pow(10, 9));

		waitForEnter();

	}// decryptA(Boolean isFromUrl)

	/*
	 * Big-O same than encryptA
	 */
	public void encryptB(Boolean isFromUrl)
	{
		// create queues
		BlockingQueue<CharSequence> qParseToEncrypt = new LinkedBlockingQueue<CharSequence>(queueSize);
		BlockingQueue<CharSequence> qEncryptToWrite = new LinkedBlockingQueue<CharSequence>(queueSize);

		// create runnable classes
		ParseFile parseFile = new ParseFile(qParseToEncrypt);
		EncryptionB encryptFile = new EncryptionB(qParseToEncrypt, qEncryptToWrite, keyManager);
		WriteFile writeFile = new WriteFile(qEncryptToWrite);

		// set file name
		if (isFromUrl)
		{
			parseFile.setFileName(fileManager.getUrlFileName(), isFromUrl);
		} else
		{
			parseFile.setFileName(fileManager.getInputFileName(), isFromUrl);
		}

		writeFile.inputFileName(fileManager.getOutputFileName());

		// create threads
		Thread t1 = new Thread(parseFile);
		Thread t2 = new Thread(encryptFile);
		Thread t3 = new Thread(writeFile);

		// start timing
		System.out.println("Starting encryption.");
		long startTime = System.nanoTime();

		// start threads
		t1.start();// Time: O(n) , space: worst O(n) , normal O(1)
		t2.start();// Time: O(n) , space: worst O(n) , normal O(1)
		t3.start();// Time: O(n) , space: worst O(n) , normal O(1)

		// yield last thread to main
		try
		{
			t3.join();

		} catch (InterruptedException e)
		{

			e.printStackTrace();
		}

		// stop timing
		System.out.println("Encryption done.");
		long stopTime = System.nanoTime();
		System.out.println("Time in  seconds: " + (stopTime - startTime) / Math.pow(10, 9));

		waitForEnter();

	}// encryptB(Boolean isFromUrl)

	/*
	 * Big-O same than encryptA
	 */
	public void decryptB(Boolean isFromUrl)
	{
		// create queues
		BlockingQueue<CharSequence> qParseToEncrypt = new LinkedBlockingQueue<CharSequence>(queueSize);
		BlockingQueue<CharSequence> qEncryptToWrite = new LinkedBlockingQueue<CharSequence>(queueSize);

		// create runnable classes
		ParseFile parseFile = new ParseFile(qParseToEncrypt);
		DecryptionB encryptFile = new DecryptionB(qParseToEncrypt, qEncryptToWrite, keyManager);
		WriteFile writeFile = new WriteFile(qEncryptToWrite);

		// set file name
		if (isFromUrl)
		{
			parseFile.setFileName(fileManager.getUrlFileName(), isFromUrl);
		} else
		{
			parseFile.setFileName(fileManager.getInputFileName(), isFromUrl);
		}

		// true);

		writeFile.inputFileName(fileManager.getOutputFileName());

		// create threads
		Thread t1 = new Thread(parseFile);
		Thread t2 = new Thread(encryptFile);
		Thread t3 = new Thread(writeFile);

		// start timing
		System.out.println("Starting encryption.");
		long startTime = System.nanoTime();

		t1.start();// Time: O(n) , space: worst O(n) , normal O(1)
		t2.start();// Time: O(n) , space: worst O(n) , normal O(1)
		t3.start();// Time: O(n) , space: worst O(n) , normal O(1)

		try
		{
			t3.join();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// stop timing
		System.out.println("Encryption done.");
		long stopTime = System.nanoTime();
		System.out.println("Time in  seconds: " + (stopTime - startTime) / Math.pow(10, 9));

		waitForEnter();

	}// decryptB(Boolean isFromUrl)

	protected void waitForEnter()
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
