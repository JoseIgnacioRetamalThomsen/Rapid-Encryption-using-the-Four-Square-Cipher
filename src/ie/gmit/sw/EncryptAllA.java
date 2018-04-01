package ie.gmit.sw;

import java.io.FileNotFoundException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class EncryptAllA
{
	public final static int QUEUE_SIZE = 1000;
	
	
	
	
	
	
	public void encryptOne()
	{
	
		
		
		BlockingQueue qParseToEncrypt = new LinkedBlockingQueue <CharSequence>(1000);
		BlockingQueue qEncryptToWrite = new LinkedBlockingQueue <CharSequence>(1000);

		KeyManagerA keyManager = new KeyManagerA();
		
		Interceptor interceptor = new Interceptor();

		ParseFile parseFile = new ParseFile(qParseToEncrypt);
		EncryptionA encryptFile = new EncryptionA(qParseToEncrypt, qEncryptToWrite,keyManager);
		WriteFile writeFile = new WriteFile(qEncryptToWrite);
		
		parseFile.interceptor = encryptFile.interceptor = writeFile.interceptor = interceptor;

		//parseFile.inputFileName("warandPeace-leotolstoy.txt", false);
		parseFile.inputFileName("t.txt", false);
		//parseFile.inputFileName("http://www.textfiles.com/etext/FICTION/80day10.txt", true);
		try
		{
			writeFile.inputFileName("d787.txt");
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
