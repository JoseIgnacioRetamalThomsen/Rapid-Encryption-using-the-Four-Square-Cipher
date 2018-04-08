package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;

/*
 * Big-O: 
 * Basically is the same than EncryptionA
 * n = total number of characters in the input file.
 * m = total number of lines in the input file.
 * a = average number of characters per line in the input file.
 * p = number of characters in one line.
 */

public class DecryptionA implements Runnable
{

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

	private final BlockingQueue<CharSequence> queueIn;
	private final BlockingQueue<CharSequence> queueOut;

	// mathc for extra letter set to x
	private int colMatch = 2;
	private int rowMatch = 4;

	// Bottom left and top right matrices
	char[][] mBL;

	char[][] mTR;

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
	public DecryptionA(BlockingQueue<CharSequence> queueInP, BlockingQueue<CharSequence> queueOutP, KeyManagerA keyManagerP)
	{
		queueIn = queueInP;

		queueOut = queueOutP;

		this.mTR = keyManagerP.mTR;
		this.mBL = keyManagerP.mBL;

	}

	/*
	 * Big-O : O(n) same than EncryptionA(5)
	 * 
	 * Estimation : same than EncryptionA(6)
	 * 
	 * Big-O: Space : Worst: O(a) , normal : O(1) same than EncryptionA(7)
	 * 
	 * Estimation : same than EncryptionA(8)
	 */


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
			/*
			tranformedText[l1p] = mTL[mTRRow[l1]][mTRCol[l2]];

			tranformedText[l2p] = mTL[mBLRow[l2]][mBRCol[l1]];*/

			line = "" + mTL[mTRRow[l1]][colMatch] + mTL[rowMatch][mBRCol[l1]];
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

	/*
	 * Encrypt one line, if the numbers of characters is odd the extra character is
	 * passed to the next line.
	 */
	/*
	 * Big-O is the same than EncryptA
	 * Big-O: Time : O(a) ( a=lineEncryptp.length() ).
	 * 
	 * Estimation : T(n) =25n +5 , n = average number of characters per line , for
	 * the "WarAndPeace-LeoTolstoy" , n=80 and at 2 billion computation per second
	 * (25*80 + 5)/(2*10^9)= 1.0025 *10^(-6) second
	 * 
	 * Big-O: Space : O(a) ( a=lineEncryptp.length() ).
	 * 
	 * Estimation : 2*char + 7*int + 1*boolean + n + (the 4 arrays of chars) = 2*32
	 * + 7*32 + 1*32 + 80 + 4n^2 + 64 n + 256 = 21,376 bits (assuming that boolean
	 * use 32 bits and 80 average characters per line)
	 */
	
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

		lineEncryptp = lineEncryptp.toUpperCase();

		for (int position = 0; position < lineEncryptp.length(); position++)
		{

			tCharIn = lineEncryptp.charAt(position);

			// to upper case
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

					tranformedText[l1p] = mTL[mTRRow[l1]][mTRCol[l2]];

					tranformedText[l2p] = mTL[mBLRow[l2]][mBRCol[l1]];
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

	/*
	 * Create matrices for encryption
	 */
	/*
	 * Big0) is the smae than Enxrypt A
	 * Big-O: Time : O(p^2) p = MATRIX_SIZE but MATRIX_SIZE=5 so O(1).
	 * 
	 * Estimation : 25*4 + 25*4 (each loop runs 25 times with 2 operations plus the
	 * increase and the comparison) = 200 at 2 billion operation per second
	 * 200/(2*10^(9)) = 1*10(-7) seconds
	 * 
	 * Big-O: Space : O(p), p = MATRIX_SIZE but MATRIX_SIZE=5 so O(1).
	 * 
	 * Estimation A 2D char array = (16 +2n)^2 = 4n^2 + 64 n + 256 , n = char, plus
	 * 4 *int , asuming char is 32 bits and int it is 32 so 4*(32)^2 + 64*32 + 256 +
	 * 4*32 = 4,576 bits.
	 */
	private void createEncMatrices()
	{

		for (x2 = 0; x2 < 5; x2++)
		{
			for (x1 = 0; x1 < 5; x1++)
			{
				mBRCol[mTR[x1][x2]] = x2;
				mTRRow[mTR[x1][x2]] = x1;

			}
		}

		for (y2 = 0; y2 < 5; y2++)
		{
			for (y1 = 0; y1 < 5; y1++)
			{
				mBLRow[mBL[y1][y2]] = y1;
				mTRCol[mBL[y1][y2]] = y2;

			}
		}
	}// createEncMatrices()

}
