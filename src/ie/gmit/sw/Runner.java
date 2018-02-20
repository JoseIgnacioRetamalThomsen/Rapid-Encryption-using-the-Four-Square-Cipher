package ie.gmit.sw;

import java.io.FileNotFoundException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

public class Runner
{

	public static void main(String[] args)
	{

		BlockingQueue<String> qParseToEncrypt = new LinkedTransferQueue <String>();
		BlockingQueue<String> qEncryptToWrite = new LinkedTransferQueue <String>();

		Interceptor interceptor = new Interceptor();

		ParseFile parseFile = new ParseFile(qParseToEncrypt);
		EncryptFive encryptFile = new EncryptFive(qParseToEncrypt, qEncryptToWrite);
		WriteFile writeFile = new WriteFile(qEncryptToWrite);
		parseFile.interceptor = encryptFile.interceptor = writeFile.interceptor = interceptor;

		parseFile.inputFileName("warAndPeace-leotolstoy.txt", false);
		try
		{
			writeFile.inputFileName("outo88887.txt");
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
		
	}

}
