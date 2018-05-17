package ie.gmit.sw;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ExtendedKeyManager extends KeyManagerA
{
	 protected int matrixSize = 9;

	int minCharacter = 41;
	int maxCharaceter = minCharacter + matrixSize * matrixSize;

	// Keys :Bottom left and top right matrices
	 char[][] mTR; // Top right key.
	 char[][] mBL; // Bottom left key.

	Scanner scanner;

	public ExtendedKeyManager(Scanner s)
	{
		super(s);

		mTR = new char[matrixSize][];
		mBL = new char[matrixSize][];

		
		
		createLetters(mTR);
		createLetters(mBL);
		

	}

	private void createLetters(char[][] mTL)
	{

		
		int row = 0, col = 0;
		mTL[row] = new char[matrixSize];

		int counter = 0;
		for (int i = minCharacter; i < maxCharaceter; i++)
		{
			
			mTL[row][col++] = (char) i;

			if (col == matrixSize)
			{
				col = 0;
				row++;
				if (row < 9)
					mTL[row] = new char[matrixSize];
			}

		}
	}// createLetters()

	public void displayTRKey()
	{
		System.out.print("Top  right  key: ");

		for (int i = 0; i < matrixSize; i++)
		{
			for (int k = 0; k < matrixSize; k++)
			{
				System.out.print(mTR[i][k]);

			} // k

		} // i

		System.out.println();

	}// displayTRKey()
	
	
	public void displayBLKey()
	{
		System.out.print("Bottom left key: ");

		for (int i = 0; i < matrixSize; i++)
		{
			for (int k = 0; k < matrixSize; k++)
			{
				System.out.print(mBL[i][k]);

			} // k

		} // i

		System.out.println();

	}// displayBLKey()
	
	public void generateRandomKeys()
	{
		List<Character> listForShuffle = new LinkedList<Character>();

		int index = 0;

		for (int i = 0; i < matrixSize; i++)
		{
			for (int j = 0; j < matrixSize; j++)
			{
				listForShuffle.add(mTR[i][j]);
			}
		}

		Collections.shuffle(listForShuffle);

		for (int i = 0; i < matrixSize; i++)
		{
			for (int j = 0; j < matrixSize; j++)
			{
				mTR[i][j] = listForShuffle.get(index++);
			}
		}

		Collections.shuffle(listForShuffle);

		index = 0;
		for (int i = 0; i < matrixSize; i++)
		{
			for (int j = 0; j < matrixSize; j++)
			{
				mBL[i][j] = listForShuffle.get(index++);
			}
		}
	}// generateRandomKeys()


}
