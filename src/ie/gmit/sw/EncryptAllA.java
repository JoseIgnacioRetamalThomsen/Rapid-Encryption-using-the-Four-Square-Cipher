package ie.gmit.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class EncryptAllA
{
	public final static int QUEUE_SIZE = 1000;

	KeyManagerA keyManager;

	FileManager fileManager;

	public EncryptAllA(KeyManagerA keyManager, FileManager fileManager)
	{
		this.keyManager = keyManager;

		this.fileManager = fileManager;
	}

	
	/*
	 * Big-O: Time : O(n), n = number of characters in the file. Because is O(n + 2*n1) where n1 = number of lines in file.
	 * because n = c * n1, c= constant (the total number of characters in the file is equal to the total number of lines multiply for a constant) so
	 * O(n + 2*n1)=O(n+2cn) = O((2c+1)n = O(Cn) = O(n) where C = constant = 2c +1
	 * 
	 * Big-O: Space : O(n) n = average number of characters per line . because O(1)+O(n)+O(1) = O(n).
	 */
	public void encryptOne(Boolean isFromUrl)
	{

		BlockingQueue<CharSequence> qParseToEncrypt = new LinkedBlockingQueue<CharSequence>(QUEUE_SIZE);
		BlockingQueue<CharSequence> qEncryptToWrite = new LinkedBlockingQueue<CharSequence>(QUEUE_SIZE);

		Interceptor interceptor = new Interceptor();

		ParseFile parseFile = new ParseFile(qParseToEncrypt);
		EncryptionA encryptFile = new EncryptionA(qParseToEncrypt, qEncryptToWrite, keyManager);
		WriteFile writeFile = new WriteFile(qEncryptToWrite);

		parseFile.interceptor = encryptFile.interceptor = writeFile.interceptor = interceptor;

		// parseFile.inputFileName("warandPeace-leotolstoy.txt", false);

		if (isFromUrl)
		{
			parseFile.setFileName(fileManager.urlFileName, isFromUrl);
		} else
		{
			parseFile.setFileName(fileManager.inputFileName, isFromUrl);
		}
		// parseFile.inputFileName("http://www.textfiles.com/etext/FICTION/80day10.txt",
		// true);

		writeFile.inputFileName(fileManager.outputFileName);

		Thread t1 = new Thread(parseFile);

		Thread t2 = new Thread(encryptFile);

		Thread t3 = new Thread(writeFile);

		t1.start();//Time:  O(n1), n1 = number of lines in file. Space : O(1)
		t2.start();//Time:  O(n), n = number of characters in the file. Space : O(n)  n = average number of characters per line.
		t3.start();//Time:  O(n1), n1 = number of lines in file. Space : O(1)

		try
		{
			t3.join();
		} catch (InterruptedException e)
		{
			
			e.printStackTrace();
		}
		interceptor.displayTimeMS();
	}

	public void decryptOne()
	{
		BlockingQueue<CharSequence> qParseToEncrypt = new LinkedBlockingQueue<CharSequence>(1000);
		BlockingQueue<CharSequence> qEncryptToWrite = new LinkedBlockingQueue<CharSequence>(1000);

		Interceptor interceptor = new Interceptor();

		ParseFile parseFile = new ParseFile(qParseToEncrypt);
		DecryptA encryptFile = new DecryptA(qParseToEncrypt, qEncryptToWrite, keyManager);
		WriteFile writeFile = new WriteFile(qEncryptToWrite);
		parseFile.interceptor = encryptFile.interceptor = writeFile.interceptor = interceptor;

		// parseFile.inputFileName("warandPeace-leotolstoy.txt", false);
		parseFile.setFileName(fileManager.inputFileName, false);
		// parseFile.inputFileName("http://www.textfiles.com/etext/FICTION/80day10.txt",
		// true);

		writeFile.inputFileName(fileManager.outputFileName);

		Thread t1 = new Thread(parseFile);

		Thread t2 = new Thread(encryptFile);

		Thread t3 = new Thread(writeFile);

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
		interceptor.displayTimeMS();
	}

}
