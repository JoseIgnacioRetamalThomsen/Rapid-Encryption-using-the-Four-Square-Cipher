package ie.gmit.sw;

import java.io.FileNotFoundException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

public class Encrypt
{

	public static void encryptOne()
	{
		BlockingQueue<CharSequence> qParseToEncrypt = new LinkedBlockingQueue <CharSequence>(1000);
		BlockingQueue<CharSequence> qEncryptToWrite = new LinkedBlockingQueue <CharSequence>(1000);

		Interceptor interceptor = new Interceptor();

		ParseFile parseFile = new ParseFile(qParseToEncrypt);
		EncryptFive encryptFile = new EncryptFive(qParseToEncrypt, qEncryptToWrite);
		WriteFile writeFile = new WriteFile(qEncryptToWrite);
		parseFile.interceptor = encryptFile.interceptor = writeFile.interceptor = interceptor;

		//parseFile.inputFileName("warandPeace-leotolstoy.txt", false);
		parseFile.inputFileName("f10.txt", false);
		//parseFile.inputFileName("http://www.textfiles.com/etext/FICTION/80day10.txt", true);
		try
		{
			writeFile.inputFileName("d123.txt");
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread t1 = new Thread(parseFile);

		Thread t2 =new Thread(encryptFile);
		

		Thread t3 =new Thread(writeFile);
		
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
	
	public static void decryptOne()
	{
		BlockingQueue<CharSequence> qParseToEncrypt = new LinkedBlockingQueue <CharSequence>(1000);
		BlockingQueue<CharSequence> qEncryptToWrite = new LinkedBlockingQueue <CharSequence>(1000);

		Interceptor interceptor = new Interceptor();

		ParseFile parseFile = new ParseFile(qParseToEncrypt);
		DecryptOne encryptFile = new DecryptOne(qParseToEncrypt, qEncryptToWrite);
		WriteFile writeFile = new WriteFile(qEncryptToWrite);
		parseFile.interceptor = encryptFile.interceptor = writeFile.interceptor = interceptor;

		//parseFile.inputFileName("warandPeace-leotolstoy.txt", false);
		parseFile.inputFileName("d123.txt", false);
		//parseFile.inputFileName("http://www.textfiles.com/etext/FICTION/80day10.txt", true);
		try
		{
			writeFile.inputFileName("aa3.txt");
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread t1 = new Thread(parseFile);

		Thread t2 =new Thread(encryptFile);
		

		Thread t3 =new Thread(writeFile);
		
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
