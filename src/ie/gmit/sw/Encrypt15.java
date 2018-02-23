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

public class Encrypt15
{
	final char mTL[][] = new char[15][];

	// Bottom left and top right matrices
	char[][] mBL = new char[15][];

	char[][] mTR = new char[15][];

	char[] tranformedText;

	public Encrypt15()
	{
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
		System.out.print("Top righ key: ");
		for (int x1 = 0; x1 < 15; x1++)
		{
			for (int y1 = 0; y1 < 15; y1++)
			{
				System.out.print(mTR[y1][x1]);
			}
		}

		System.out.print("\nTop Bottom left key: ");

		for (int x1 = 0; x1 < 15; x1++)
		{
			for (int y1 = 0; y1 < 15; y1++)
			{
				System.out.print(mBL[y1][x1]);
			}
		}

	}
}
