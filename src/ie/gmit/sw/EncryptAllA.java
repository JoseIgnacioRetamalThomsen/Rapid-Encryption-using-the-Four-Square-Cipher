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

	public void encryptOne(Boolean isFromUrl)
	{

		BlockingQueue<CharSequence> qParseToEncrypt = new LinkedBlockingQueue<CharSequence>(1000);
		BlockingQueue<CharSequence> qEncryptToWrite = new LinkedBlockingQueue<CharSequence>(1000);

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
		try
		{
			writeFile.inputFileName(fileManager.outputFileName);
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		try
		{
			writeFile.inputFileName(fileManager.outputFileName);
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
