package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class Encrypt15 implements Runnable
{
	private final BlockingQueue<CharSequence> queueIn;
	private final BlockingQueue<CharSequence> queueOut;
	
	
	final char mTL[][] = new char[15][];

	// Bottom left and top right matrices
	
	char[][] mTR = new char[15][];
	char[][] mBL = new char[15][];

	

	char[] tranformedText;
	
	Interceptor interceptor;

	public Encrypt15(BlockingQueue<CharSequence> queueInP, BlockingQueue<CharSequence> queueOutP)
	{
		queueIn = queueInP;

		queueOut = queueOutP;

		
		char c = 31;
		for (int x1 = 0; x1 < 15; x1++)
		{
			mTL[x1] = new char[15];
			for (int y1 = 0; y1 < 15; y1++)
			{
				mTL[x1][y1] = (char) c++;
			}
		}
	}

	public void generateRandomKeys()
	{
		List<Character> mBLElements = new LinkedList<Character>();
		List<Character> mTRElements = new LinkedList<Character>();

		for (int i = 32; i <= 256; i++)
		{
			mTRElements.add((char) (i));
			mBLElements.add((char) (i));
		}

		Collections.shuffle(mTRElements);
		Collections.shuffle(mBLElements);
		
		setKeys(mTRElements, mBLElements);
	}

	public void writeKeysToFile()
	{
		java.io.FileOutputStream fos = null;
		try
		{
			fos = new java.io.FileOutputStream(new File("file.txt"));
		} catch (FileNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PrintWriter outputFile;

		try
		{
			outputFile = new PrintWriter(new FileOutputStream(new File("Keys.txt"), false));
			int count = 0;
			
			for (int x1 = 0; x1 < 15; x1++)
			{
				for (int y1 = 0; y1 < 15; y1++)
				{
					System.out.println(count++);
					fos.write(mTR[y1][x1]);
					//fos.write('\r');
					outputFile.write(mTR[y1][x1]);
				}
			}
			outputFile.write('\n');
			// fos.write('\r');
			 fos.write('\n');
			outputFile.println();
			for (int x1 = 0; x1 < 15; x1++)
			{
				for (int y1 = 0; y1 < 15; y1++)
				{
					System.out.println(count++);
					fos.write(mBL[y1][x1]);
					outputFile.write(mBL[y1][x1]);
				}
			}
			outputFile.close();
			fos.close();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readKeysFromFile()
	{
		List<Character> mBLElements = new LinkedList<Character>();
		List<Character> mTRElements = new LinkedList<Character>();

		int count = 0;

		byte key[] = new byte[500];
		
		Scanner fileIn;
		String line;
		try
		{
			FileInputStream in = new FileInputStream("file.txt");
			fileIn = new Scanner(new File("file.txt"));
			//line = fileIn.nextLine();
			in.read(key);
			
			for (int i = 0; i < 225; i++)
			{

				mTRElements.add((char)key[i]);
				System.out.println(key[i]);

			}
			//line = fileIn.nextLine();
			for (int i =225; i < 450; i++)
			{

				mBLElements.add((char)key[i]);

			}
			fileIn.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setKeys(mTRElements, mBLElements);

	}

	public void setKeys(List<Character> mTRElements, List<Character> mBLElements)
	{
		int c = 0;
		for (int x1 = 0; x1 < 15; x1++)
		{
			mTR[x1] = new char[15];

			for (int y1 = 0; y1 < 15; y1++)
			{
				mTR[x1][y1] = mTRElements.get(c++);
			}
		}

		c = 0;
		for (int x1 = 0; x1 < 15; x1++)
		{
			mBL[x1] = new char[15];
			for (int y1 = 0; y1 < 15; y1++)
			{
				mBL[x1][y1] = mBLElements.get(c++);
			}
		}
	}

	public void displayKeys()
	{
		System.out.print("Top righ key: \n");
		for (int x1 = 0; x1 < 15; x1++)
		{
			System.out.printf("%3s",x1 +" ");
			for (int y1 = 0; y1 < 15; y1++)
			{
				
				System.out.print(" x"+mTR[y1][x1] + "x ");
			}
			System.out.println();
		}

		System.out.print("\nTop Bottom left key:\n ");

		for (int x1 = 0; x1 < 15; x1++)
		{
			System.out.printf("%3s",x1 +" ");
			for (int y1 = 0; y1 < 15; y1++)
			{
				System.out.print(" x"+mBL[y1][x1]+ "x ");
			}
			System.out.println();
		}

	}

	char[] tString;

	int mTRRow[] = new int[256];
	int mTRCol[] = new int[256];
	int mBLRow[] = new int[256];
	int mBRCol[] = new int[256];

	char l1 = 0, l2 = 0;
	int l1p = 0, l2p = 0;
	int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
	
	boolean letterFordward = false;

	String stringOut;
	
	
	
	private void createEncMatrices()
	{

		for (x2 = 0; x2 < 15; x2++)
		{
			for (x1 = 0; x1 < 15; x1++)
			{
				mBRCol[(int)mTL[x1][x2]] = x2;
				mTRRow[(int)mTL[x1][x2]] = x1;

			}
		}

		for (y2 = 0; y2 < 15; y2++)
		{
			for (y1 = 0; y1 < 15; y1++)
			{
				mBLRow[(int)mTL[y1][y2]] = y1;
				mTRCol[(int)mTL[y1][y2]] = y2;

			}
		}
	}// createEncMatrices()

	
	private void encryptLine(String lineEncryptp)
	{
		if (l1 != 0)
		{
			l1p = 0;
			letterFordward = true;

		} else
		{
			letterFordward = false;
		}
		tranformedText = new char[lineEncryptp.length() + 1];

		

		for (int position = 0; position < lineEncryptp.length(); position++)
		{

			
			
			if (lineEncryptp.charAt(position) > 31 && lineEncryptp.charAt(position) <= 256)
			{
				if (l1 == 0)
				{
				
					l1 = lineEncryptp.charAt(position);
					
					if (!letterFordward)
					{
						l1p = position;
					} else
					{
						l1p = position + 1;
					}

				} else if (l2 == 0)
				{
					
					
						l2 = lineEncryptp.charAt(position);
					
					  if (!letterFordward) {
                          l2p = position;
                      } else {
                          l2p = position + 1;
                      }


						tranformedText[l1p] = mTR[mTRRow[(int)l1]][mTRCol[(int)l2]];

						tranformedText[l2p] = mBL[mBLRow[(int)l2]][mBRCol[(int)l1]];
					  
					l1 = 0;
					l2 = 0;
				}
			} else
			{
				/*
				 if (!letterFordward) {
                     // System.out.println(position);
					 tranformedText[position] = ' ';
                 } else {
                	 tranformedText[position + 1] = ' ';
                 }*/

			} // if (inputText.charAt(i) >= 'A' && inputText.charAt(i) <= 'Z')
		}
		try
		{

			for(char ch:tranformedText)
			{
				System.out.print(ch+" x ");
			}
			
			queueOut.put(new String(tranformedText));
			// queueOut.put("w");

		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//encryptLine(String lineEncryptp)
	
	
	@Override
	public void run()
	{
		CharSequence line = null;
		createEncMatrices();
		

		while (true)
		{

			try
			{
				line = queueIn.take();

			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (!(line instanceof Poison))
			{
				encryptLine((String)line);

			} else
			{
				break;
			}

		} // while(true)
		
		//check if there is last letter
		if(l1!=0)
		{
			
			//line = ""+mTR[mTRRow[l1]][0]+ mBL[0][mBRCol[l1]];
		}
		try
		{
			queueOut.put(line);
			queueOut.put(new Poison());

		} catch (InterruptedException e)
		{

			e.printStackTrace();
		}

		
	}
}