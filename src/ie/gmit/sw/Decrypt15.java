package ie.gmit.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class Decrypt15 implements Runnable
{
	private final BlockingQueue<CharSequence> queueIn;
	private final BlockingQueue<CharSequence> queueOut;
	
	
	final char mTL[][] = new char[15][];

	// Bottom left and top right matrices
	char[][] mBL = new char[15][];

	char[][] mTR = new char[15][];

	char[] tranformedText;
	
	Interceptor interceptor;

	public Decrypt15(BlockingQueue<CharSequence> queueInP, BlockingQueue<CharSequence> queueOutP)
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

		for (int i = 0; i < 225; i++)
		{
			mTRElements.add((char) (i + 31));
			mBLElements.add((char) (i + 31));
		}

		Collections.shuffle(mTRElements);

		Collections.shuffle(mBLElements);
		setKeys(mTRElements, mBLElements);
	}

	public void writeKeysToFile()
	{
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
					outputFile.print(mTR[y1][x1]);
				}
			}
			outputFile.println();
			for (int x1 = 0; x1 < 15; x1++)
			{
				for (int y1 = 0; y1 < 15; y1++)
				{
					System.out.println(count++);
					outputFile.print(mBL[y1][x1]);
				}
			}
			outputFile.close();
		} catch (FileNotFoundException e)
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

		Scanner fileIn;
		String line;
		try
		{
			fileIn = new Scanner(new File("keys.txt"));
			line = fileIn.nextLine();
			for (int i = 0; i < 225; i++)
			{

				mTRElements.add(line.charAt(i));

			}
			line = fileIn.nextLine();
			for (int i = 0; i < 225; i++)
			{

				mBLElements.add(line.charAt(i));

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
				mBRCol[(int)mTR[x1][x2]]=x2;
				mTRRow[(int)mTR[x1][x2]]=x1;

			}
		}

		for (y2 = 0; y2 < 15; y2++)
		{
			for (y1 = 0; y1 < 15; y1++)
			{
				mBLRow[(int)mBL[y1][y2]] = y1;
				mTRCol[(int)mBL[y1][y2]] = y2;

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

					  tranformedText[l1p] = mTL[mTRRow[(int)l1]][mTRCol[(int)l2]];

						tranformedText[l2p] = mTL[mBLRow[(int)l2]][mBRCol[(int)l1]];
					l1 = 0;
					l2 = 0;
				}
			} else
			{/*
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
			
			line = ""+ mTL[mTRRow[l1]][0]+ mTL[0][mBRCol[l1]];
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
