package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeUnit;
/*
 * Encryption 5 matrix side A
 * Class for encryption using a 5 side matrix 
 * Line by line , if a line have a odd number of 
 * characters the character is pass over to the next line
 * if the last line have a odd number of characters the last character 
 * will be encrypted with the first element in the matrix (row=0, col=0).
 */

public class EncryptionA implements Runnable
{
	private final static int MATRIX_SIZE = 5;

	private final BlockingQueue<CharSequence> queueIn;
	private final BlockingQueue<CharSequence> queueOut;

	private KeyManagerA keyManager;

	Interceptor interceptor;

	/**** Encryption variables ******/
	// Constants
	// Array with the 5x5 matrix with the 25 letters (all minus j)
	final char[][] mTL =
	{
			{ 'A', 'B', 'C', 'D', 'E' },
			{ 'F', 'G', 'H', 'I', 'K' },
			{ 'L', 'M', 'N', 'O', 'P' },
			{ 'Q', 'R', 'S', 'T', 'U' },
			{ 'V', 'W', 'X', 'Y', 'Z' } };

	// Bottom left and top right matrices
	char[][] mTR;
	char[][] mBL;

	char[] tranformedText;

	char tCharIn;
	char[] tString;

	int mTRRow[] = new int['Z' + 1];
	int mTRCol[] = new int['Z' + 1];
	int mBLRow[] = new int['Z' + 1];
	int mBRCol[] = new int['Z' + 1];

	char l1 = 0, l2 = 0;
	int l1p = 0, l2p = 0;
	int x1 = 0, x2 = 0, y1 = 0, y2 = 0;

	boolean letterFordward = false;

	String stringOut;

	/*
	 * Constructor
	 */
	public EncryptionA(BlockingQueue<CharSequence> queueInP, BlockingQueue<CharSequence> queueOutP,
			KeyManagerA keyManagerP)
	{
		queueIn = queueInP;
		queueOut = queueOutP;

		mTR = keyManagerP.mTR;
		mBL = keyManagerP.mBL;

		/*
		 * 
		 * mBL = new char[][] { { 'M', 'F', 'N', 'B', 'D' }, { 'C', 'R', 'H', 'S', 'A'
		 * }, { 'X', 'Y', 'O', 'G', 'V' }, { 'I', 'T', 'U', 'E', 'W' }, { 'L', 'Q', 'Z',
		 * 'K', 'P' } }; mTR = new char[][] { { 'Z', 'G', 'P', 'T', 'F' }, { 'O', 'I',
		 * 'H', 'M', 'U' }, { 'W', 'D', 'R', 'C', 'N' }, { 'Y', 'K', 'E', 'Q', 'A' }, {
		 * 'X', 'V', 'S', 'B', 'L' } };
		 */
	}

	public void setQueues()
	{

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
			if (col == MATRIX_SIZE)
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
			if (col == MATRIX_SIZE)
			{
				col = 0;
				row++;
			}
		}
		return true;
	}

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
		if (l1 != 0)
		{

			line = "" + mTR[mTRRow[l1]][0] + mBL[0][mBRCol[l1]];
		}
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
		if (l1 != 0)
		{
			l1p = 0;
			letterFordward = true;

		} else
		{
			letterFordward = false;
		}
		tranformedText = new char[lineEncryptp.length() + 1];

		// lineEncryptp = lineEncryptp.toUpperCase();

		for (int position = 0; position < lineEncryptp.length(); position++)
		{

			tCharIn = lineEncryptp.charAt(position);

			if (tCharIn >= 'a' && tCharIn <= 'z')
			{
				tCharIn = (char) (((int) tCharIn) - 32);
			}

			if (tCharIn >= 'A' && tCharIn <= 'Z')
			{
				if (l1 == 0)
				{
					if (tCharIn == 'J')
					{
						l1 = 'I';
					} else
					{
						l1 = tCharIn;
					}
					if (!letterFordward)
					{
						l1p = position;
					} else
					{
						l1p = position + 1;
					}

				} else if (l2 == 0)
				{
					if (tCharIn == 'J')
					{
						l2 = 'I';

					} else
					{
						l2 = tCharIn;
					}
					if (!letterFordward)
					{
						l2p = position;
					} else
					{
						l2p = position + 1;
					}

					tranformedText[l1p] = mTR[mTRRow[l1]][mTRCol[l2]];

					tranformedText[l2p] = mBL[mBLRow[l2]][mBRCol[l1]];
					l1 = 0;
					l2 = 0;
				}
			} else
			{
				if (!letterFordward)
				{
					// System.out.println(position);
					tranformedText[position] = ' ';
				} else
				{
					tranformedText[position + 1] = ' ';
				}

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
	}

	private void createEncMatrices()
	{

		for (x2 = 0; x2 < MATRIX_SIZE; x2++)
		{
			for (x1 = 0; x1 < MATRIX_SIZE; x1++)
			{
				mBRCol[mTL[x1][x2]] = x2;
				mTRRow[mTL[x1][x2]] = x1;

			}
		}

		for (y2 = 0; y2 < MATRIX_SIZE; y2++)
		{
			for (y1 = 0; y1 < MATRIX_SIZE; y1++)
			{
				mBLRow[mTL[y1][y2]] = y1;
				mTRCol[mTL[y1][y2]] = y2;

			}
		}
	}// createEncMatrices()

}
