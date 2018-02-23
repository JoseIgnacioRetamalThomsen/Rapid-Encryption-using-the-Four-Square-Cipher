package ie.gmit.sw;

import java.io.FileNotFoundException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

public class Runner
{

	public static void main(String[] args) throws InterruptedException
	{

		//Encrypt.encryptOne();
		//Encrypt.decryptOne();
		Encrypt15 e = new Encrypt15();
		//e.generateRandomKeys();
		//e.writeKeysToFile();
		e.readKeysFromFile();
		e.displayKeys();
	//	e.generateRandomKeys();
	/*
		for(char c=0;c<256;c++)
		{
			System.out.println(c);
		}*/
		
		System.out.println(((int)'\\'));
	}

}
