package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;
/*
 *	Extended algorithm using a 9 size matrix
*/

/*
* Big-O: 
* n = total number of characters in the input file.
* m = total number of lines in the input file.
* a = average number of characters per line in the input file.
* p = number of characters in one line.
* l = matrix size.
* r= number of letters in the matrix
*/

public class ExtendedDecryption implements Runnable
{
	private final static int MATRIX_SIZE = 9;

	int minCharacter = 41;
	int maxCharaceter = minCharacter + MATRIX_SIZE * MATRIX_SIZE;

	private final BlockingQueue<CharSequence> queueIn;
	private final BlockingQueue<CharSequence> queueOut;

	/**** Encryption variables ******/
	// Constants
	// Array with the 5x5 matrix with the 25 letters (all minus j)
	char[][] mTL;

	// Bottom left and top right matrices
	char[][] mBL;

	char[][] mTR;

	char[] tranformedText;

	char tCharIn;
	char[] tString;

	int mTRRow[] = new int[maxCharaceter + 1];
	int mTRCol[] = new int[maxCharaceter + 1];
	int mBLRow[] = new int[maxCharaceter + 1];
	int mBRCol[] = new int[maxCharaceter + 1];

	char l1 = 0, l2 = 0;
	int l1p = 0, l2p = 0;
	int x1 = 0, x2 = 0, y1 = 0, y2 = 0;

	String stringOut;

	/*
	 * Constructor
	 */
	public ExtendedDecryption(BlockingQueue<CharSequence> queueInP, BlockingQueue<CharSequence> queueOutP,
			ExtendedKeyManager keyManagerP)
	{
		queueIn = queueInP;

		queueOut = queueOutP;

		this.mTR = keyManagerP.mTR;
		this.mBL = keyManagerP.mBL;

		createLetters();
	}

	private void createLetters()
	{
		mTL = new char[MATRIX_SIZE][];

		int row = 0, col = 0;
		mTL[row] = new char[MATRIX_SIZE];

		int counter = 0;
		for (int i = minCharacter; i < maxCharaceter; i++)
		{
			
			mTL[row][col++] = (char) i;

			if (col == MATRIX_SIZE)
			{
				col = 0;
				row++;
				if (row < 9)
					mTL[row] = new char[MATRIX_SIZE];
			}

		}
	}// createLetters()

	public boolean setKeyBL(char[] key)
	{
		if (key.length != 25)
			return false;

		int row = 0, col = 0;
		for (Character character : key)
		{
			mBL[row][col++] = character;
			if (col == 5)
			{
				col = 0;
				row++;
			}
		} // for (Character character : key)

		return true;

	}// setKeyBL(char[] key)

	public boolean setKeyTR(char[] key)
	{
		if (key.length != 25)
			return false;

		int row = 0, col = 0;
		for (Character character : key)
		{
			mTR[row][col++] = character;
			if (col == 5)
			{
				col = 0;
				row++;
			}

		} // for (Character character : key)

		return true;

	}// setKeyTR(char[] key)

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
				encryptLine((String) line);

			} else
			{
				break;
			}

		} // while(true)

		// check if there is last letter

		try
		{
			queueOut.put(line);
			queueOut.put(new Poison());

		} catch (InterruptedException e)
		{

			e.printStackTrace();
		}

	}// run()

	private void encryptLine(String lineEncryptp)
	{

		// create array for result
		tranformedText = new char[lineEncryptp.length() + 1];

		// loop the line
		for (int position = 0; position < lineEncryptp.length(); position++)
		{

			// grab one char
			tCharIn = lineEncryptp.charAt(position);

			// check if is on the range
			if (tCharIn >= minCharacter && tCharIn <= maxCharaceter)
			{
				if (l1 == 0)// first for encrypt
				{

					l1 = tCharIn;// put character

					l1p = position;

				} else if (l2 == 0)
				{

					l2 = tCharIn;// put character

					// assign position
					l2p = position;

					/// transform the chars and put in the result
					tranformedText[l1p] = mTL[mTRRow[l1]][mTRCol[l2]];
					tranformedText[l2p] = mTL[mBLRow[l2]][mBRCol[l1]];

					// reset the char at null
					l1 = 0;
					l2 = 0;
				}
			} else// not in range
			{

				tranformedText[position] = tCharIn;

			} // if (inputText.charAt(i) >= 'A' && inputText.charAt(i) <= 'Z')

		} // encryptLine(String lineEncryptp)

		// just reset we don't expect extra letter when decrypt
		l1 = 0;
		l2 = 0;

		try
		{
			// just print the character as it is
			queueOut.put(new String(tranformedText));
			// queueOut.put("w");

		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createEncMatrices()
	{

		for (x2 = 0; x2 < MATRIX_SIZE; x2++)
		{
			for (x1 = 0; x1 < MATRIX_SIZE; x1++)
			{
				mBRCol[mTR[x1][x2]] = x2;
				mTRRow[mTR[x1][x2]] = x1;

			}
		}

		for (y2 = 0; y2 < MATRIX_SIZE; y2++)
		{
			for (y1 = 0; y1 < MATRIX_SIZE; y1++)
			{
				mBLRow[mBL[y1][y2]] = y1;
				mTRCol[mBL[y1][y2]] = y2;

			}
		}
	}// createEncMatrices()

}//ExtendedDecryption
