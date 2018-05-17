package ie.gmit.sw;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
/*
 * Rapid Encryption using the Four-Square Cipher
 * Jose Ignacio Retamal
 * G00351330@gmit.ie
 *
 * Extende Algorith 
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
 * * l = matrix size.
 * * r= number of letters in the matrix
 */


public class ExtendedEncryptAndDecrypt extends EncryptAndDecrypt
{


	
	public ExtendedEncryptAndDecrypt(ExtendedKeyManager keyManager, FileManager fileManager)
	{
		super(keyManager, fileManager);
		
	}

	
	
	/*
	 * Big-O: Time : We look at the 3 run methods WriteFile+EncryptA+parseFile = O
	 * (n) +O(r) +O(n)+ O(n) =O(r)+  O(n) , if r= constant -> O(n)
	 * 
	 * Estimation : the higher of the thread that is EncryptionA =  0.0650893 seconds.
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
	
	public void encrypt(Boolean isFromUrl)
	{

		// create queues
		BlockingQueue<CharSequence> qParseToEncrypt = new LinkedBlockingQueue<CharSequence>(queueSize);
		BlockingQueue<CharSequence> qEncryptToWrite = new LinkedBlockingQueue<CharSequence>(queueSize);

		// create runnable classes
		ParseFile parseFile = new ParseFile(qParseToEncrypt);
		ExtendedEncryption encryptFile = new ExtendedEncryption(qParseToEncrypt, qEncryptToWrite, (ExtendedKeyManager)keyManager);
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
	 * Big-O same then encrypt
	 */
	public void decrypt(Boolean isFromUrl)
	{

		// create queues
		BlockingQueue<CharSequence> qParseToEncrypt = new LinkedBlockingQueue<CharSequence>(queueSize);
		BlockingQueue<CharSequence> qEncryptToWrite = new LinkedBlockingQueue<CharSequence>(queueSize);

		// create runnable classes
		ParseFile parseFile = new ParseFile(qParseToEncrypt);
		ExtendedDecryption encryptFile = new ExtendedDecryption(qParseToEncrypt, qEncryptToWrite, (ExtendedKeyManager)keyManager);
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
	
}// ExtendedEncryptAndDecrypt
