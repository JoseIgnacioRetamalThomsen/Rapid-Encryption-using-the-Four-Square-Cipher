package ie.gmit.sw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Encryption5x5
{
	// Constants
	// Array with the 5x5 matrix with the 25 letters (all minus j)
	final char[][] mTL =
	{
			{ 'A', 'B', 'C', 'D', 'E' },
			{ 'F', 'G', 'H', 'I', 'K' },
			{ 'L', 'M', 'N', 'O', 'P' },
			{ 'Q', 'R', 'S', 'T', 'U' },
			{ 'V', 'W', 'X', 'Y', 'Z' } };

	final byte matrixSize = 5;

	// Bottom left and top right matrices
	char[][] mBL;// = {{'M', 'F', 'N', 'B', 'D'}, {'C', 'R', 'H', 'S', 'A'}, {'X', 'Y', 'O', 'G',
					// 'V'}, {'I', 'T', 'U', 'E', 'W'}, {'L', 'Q', 'Z', 'K', 'P'}};
	char[][] mTR;

	char[] tranformedText;

	// Reading file variables
	StringBuilder inputText;
	FileReader fileReader;
	Scanner scanner;
	PrintWriter outputFile;

	// Constructors
	public Encryption5x5()
	{
		mBL = new char[][]
		{
				{ 'M', 'F', 'N', 'B', 'D' },
				{ 'C', 'R', 'H', 'S', 'A' },
				{ 'X', 'Y', 'O', 'G', 'V' },
				{ 'I', 'T', 'U', 'E', 'W' },
				{ 'L', 'Q', 'Z', 'K', 'P' } };
		mTR = new char[][]
		{
				{ 'Z', 'G', 'P', 'T', 'F' },
				{ 'O', 'I', 'H', 'M', 'U' },
				{ 'W', 'D', 'R', 'C', 'N' },
				{ 'Y', 'K', 'E', 'Q', 'A' },
				{ 'X', 'V', 'S', 'B', 'L' } };

		inputText = new StringBuilder();
	}

	public boolean setKeyBL(char[] key)
	{
		if (key.length != 25)
		{
			return false;
		}

		int row = 0, col = 0;
		for (Character character : key)
		{
			mBL[row][col++] = character;
			if (col == 5)
			{
				col = 0;
				row++;
			}
		}

		return true;
	}

	public boolean setKeyTR(char[] key)
	{
		if (key.length != 25)
		{
			return false;
		}

		int row = 0, col = 0;
		for (Character character : key)
		{
			mTR[row][col++] = character;
			if (col == 5)
			{
				col = 0;
				row++;
			}
		}
		return true;
	}

	public void printKey()
	{

		for (int row = 0; row < 5; row++)
		{
			for (int col = 0; col < 5; col++)
			{
				System.out.print(mTR[row][col] + " ");

			}
			System.out.println();
		}

	}

	/*
	 * Method used for read file having the string name will throw exception that
	 * MUST be catch in GUI class. Will read the file line per line, transform each
	 * line to upper case and add a new line character after each line. Only for
	 * local use.
	 */
	private void readFile(String fileName) throws FileNotFoundException
	{
		scanner = new Scanner(new FileReader(fileName));

		while (scanner.hasNext())
		{
			inputText.append(scanner.nextLine().toUpperCase());
			inputText.append((char) 10);
		}

		scanner.close();

	}// readFile(String fileName)

	/*
	 * Method for write to a file the processed text. Will write to a file the char
	 * array "tranformedText" which must be the text after decryption/encryption.
	 * 
	 */

	private void writeFile(String fileName) throws FileNotFoundException
	{

		outputFile = new PrintWriter(new FileOutputStream(new File(fileName), true));

		outputFile.print(tranformedText);

		outputFile.close();

	}

	/*
	 * This is the method that do the encryption, it will bee call from UI and will
	 * return true if the encryption was done successfully
	 * 
	 */
	public boolean encrypt5_5(String inputFileName, String outputFileName)

	{

		char l1 = 0, l2 = 0;
		int l1p = 0, l2p = 0;
		int x1 = 0, x2 = 0, y1 = 0, y2 = 0;

		try
		{
			readFile(inputFileName);

			tranformedText = new char[inputText.length()];

			for (int i = 0; i < inputText.length(); i++)
			{

				if (inputText.charAt(i) >= 'A' && inputText.charAt(i) <= 'Z')
				{
					if (l1 == 0)
					{
						if (inputText.charAt(i) == 'J')
						{
							l1 = 'I';
						} else
						{
							l1 = inputText.charAt(i);
						}
						l1p = i;

					} else if (l2 == 0)
					{
						if (inputText.charAt(i) == 'J')
						{
							l2 = 'I';

						} else
						{
							l2 = inputText.charAt(i);
						}
						l2p = i;

						// convert letters
						loop1: for (x2 = 0; x2 < matrixSize; x2++)
						{
							for (x1 = 0; x1 < matrixSize; x1++)
							{
								if (mTL[x2][x1] == l1)
								{

									break loop1;
								}
							}
						}

						loop2: for (y2 = 0; y2 < matrixSize; y2++)
						{
							for (y1 = 0; y1 < matrixSize; y1++)
							{
								if (mTL[y2][y1] == l2)
								{
									break loop2;
								}
							}
						}

						// tString[l1p] = mTR[l1row][l2col];
						// System.out.println(l1p + " x" + "x " + x2 + " x " + y1);

						tranformedText[l1p] = mTR[x2][y1];

						tranformedText[l2p] = mBL[y2][x1];
						l1 = 0;
						l2 = 0;
					}
				} else
				{
					tranformedText[i] = inputText.charAt(i);

				} // if (inputText.charAt(i) >= 'A' && inputText.charAt(i) <= 'Z')

			} // for

			writeFile(outputFileName);

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("File not found");
			return false;

		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception");
			return false;

		} catch (Error e)
		{
			System.out.println("Error");
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean encryptUlti5_5(String inputFileName, String outputFileName)
	{

		char l1 = 0, l2 = 0;
		int l1p = 0, l2p = 0;
		int x1 = 0, x2 = 0, y1 = 0, y2 = 0;

		int mTRRow[] = new int[100];
		int mTRCol[] = new int[100];
		int mBLRow[] = new int[100];
		int mBRCol[] = new int[100];

		
	 for (x2 = 0; x2 < matrixSize; x2++)
		{
			for (x1 = 0; x1 < matrixSize; x1++)
			{
				mBRCol[mTL[x1][x2]]=x2;
				mTRRow[mTL[x1][x2]]=x1;
			
			}
		}
		
		
		for (y2 = 0; y2 < matrixSize; y2++)
		{
			for (y1 = 0; y1 < matrixSize; y1++)
			{
				mBLRow[mTL[y1][y2]] = y1;
				mTRCol[mTL[y1][y2]] = y2;
				
			}
		}

		try
		{
			readFile(inputFileName);

			tranformedText = new char[inputText.length()];

			for (int i = 0; i < inputText.length(); i++)
			{

				if (inputText.charAt(i) >= 'A' && inputText.charAt(i) <= 'Z')
				{
					if (l1 == 0)
					{
						if (inputText.charAt(i) == 'J')
						{
							l1 = 'I';
						} else
						{
							l1 = inputText.charAt(i);
						}
						l1p = i;

					} else if (l2 == 0)
					{
						if (inputText.charAt(i) == 'J')
						{
							l2 = 'I';

						} else
						{
							l2 = inputText.charAt(i);
						}
						l2p = i;

						// convert letters
					
						
						tranformedText[l1p] = mTR[mTRRow[l1]][mTRCol[l2]];

						tranformedText[l2p] = mBL[mBLRow[l2]][mBRCol[l1]];
						l1 = 0;
						l2 = 0;
					}
				} else
				{
					tranformedText[i] = inputText.charAt(i);

				} // if (inputText.charAt(i) >= 'A' && inputText.charAt(i) <= 'Z')

			} // for

			writeFile(outputFileName);

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("File not found");
			return false;

		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception");
			return false;

		} catch (Error e)
		{
			System.out.println("Error");
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	public boolean decrypt5_5(String inputFileName, String outputFileName)
	{

		char l1 = 0, l2 = 0;
		int l1p = 0, l2p = 0;
		int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
		
		int mTRRow[] = new int[100];
		int mTRCol[] = new int[100];
		int mBLRow[] = new int[100];
		int mBRCol[] = new int[100];

		
	 for (x2 = 0; x2 < matrixSize; x2++)
		{
			for (x1 = 0; x1 < matrixSize; x1++)
			{
				mBRCol[mTR[x1][x2]]=x2;
				mTRRow[mTR[x1][x2]]=x1;
			
			}
		}
		
		
		for (y2 = 0; y2 < matrixSize; y2++)
		{
			for (y1 = 0; y1 < matrixSize; y1++)
			{
				mBLRow[mBL[y1][y2]] = y1;
				mTRCol[mBL[y1][y2]] = y2;
				
			}
		}

		try
		{
			readFile(inputFileName);

			tranformedText = new char[inputText.length()];

			for (int i = 0; i < inputText.length(); i++)
			{

				if (inputText.charAt(i) >= 'A' && inputText.charAt(i) <= 'Z')
				{
					if (l1 == 0)
					{
						if (inputText.charAt(i) == 'J')
						{
							l1 = 'I';
						} else
						{
							l1 = inputText.charAt(i);
						}
						l1p = i;

					} else if (l2 == 0)
					{
						if (inputText.charAt(i) == 'J')
						{
							l2 = 'I';

						} else
						{
							l2 = inputText.charAt(i);
						}
						l2p = i;

						// convert letters
						tranformedText[l1p] = mTL[mTRRow[l1]][mTRCol[l2]];

						tranformedText[l2p] = mTL[mBLRow[l2]][mBRCol[l1]];
						l1 = 0;
						l2 = 0;
					}
				} else
				{
					tranformedText[i] = inputText.charAt(i);

				} // if (inputText.charAt(i) >= 'A' && inputText.charAt(i) <= 'Z')

			} // for

			writeFile(outputFileName);

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("File not found");
			return false;

		} catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception");
			return false;

		} catch (Error e)
		{
			System.out.println("Error");
			e.printStackTrace();
			return false;
		}

		return true;
	}


	public static void main(String[] args)
	{
		Encryption5x5 e = new Encryption5x5();
		long startTime1 = System.nanoTime();
		
	//	e.encrypt5_51("warandPeace-leotolstoy.txt","post.txt" );
		e.encryptUlti5_5("warandPeace-leotolstoy.txt","ab1.txt" );
		
		e.decrypt5_5("ab1.txt", "thison1e.txt");
		//e.readFileDE();
		long startTimef1 = System.nanoTime();
		System.out.println(startTimef1 - startTime1);
		/*
		 * long startTime1 = System.nanoTime();
		 * 
		 * readFile(); long startTimef1 = System.nanoTime();
		 * System.out.println(startTimef1 - startTime1);
		 * 
		 * long startTime = System.nanoTime();
		 * 
		 * new Encryption5x5().encrypt5_5("WarAndPeace-LeoTolstoy.txt", "text12.txt");
		 * long startTimef = System.nanoTime(); System.out.println(startTimef -
		 * startTime);
		 */
	}


	
}