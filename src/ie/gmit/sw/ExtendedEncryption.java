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

public class ExtendedEncryption implements Runnable
{
	private int matrixSize = 9;

	int minCharacter = 41;
	int maxCharaceter = minCharacter + matrixSize * matrixSize;

	// array for letters
	char[][] mTL;

	private final BlockingQueue<CharSequence> queueIn;
	private final BlockingQueue<CharSequence> queueOut;

	// mathc for extra letter set to x
	private int colMatch = 0;
	private int rowMatch = 0;

	// Bottom left and top right matrices
	char[][] mTR;
	char[][] mBL;

	/**** Encryption variables ******/
	char[] tranformedText;

	boolean letterFordward = false;
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

	public ExtendedEncryption(BlockingQueue<CharSequence> queueInP, BlockingQueue<CharSequence> queueOutP,
			ExtendedKeyManager keyManagerP)
	{

		createLetters();

		queueIn = queueInP;
		queueOut = queueOutP;

		mTR = keyManagerP.mTR;
		mBL = keyManagerP.mBL;

	}// EncryptionB(

	/*
	 * Big-O Time :O(l)
	 * 
	 * 
	 * 
	 * Big-O: Space : O(l)
	 * 
	 * 
	 * 
	 */
	private void createLetters()
	{
		mTL = new char[matrixSize][];

		int row = 0, col = 0;
		mTL[row] = new char[matrixSize];

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

	/*
	 * Big-O :O(r)+ O(n) if r=constants as it is O(n)
	 * 
	 * Estimation : createMatrices() + m*encryptLine() = 3.24*10(-7) seconds +
	 * 1.0025 *10^(-6)* 64,927 second = 0.06525196
	 * 
	 * Big-O: Space : Worst: O(a) , normal : O(1) same than EncryptionA(7)
	 * 
	 * Estimation : same than EncryptionA(8)
	 */
	@Override
	public void run()
	{
		// create line for read
		CharSequence line = null;
		// create matrices for encryption
		createEncMatrices();// O(r)

		while (true)// true while the queue is not empty
		{

			try
			{
				// take for queue
				line = queueIn.take();

			} catch (InterruptedException e)
			{

				e.printStackTrace();
			}

			// stop when poison encounter
			if (!(line instanceof Poison))
			{
				encryptLine((String) line);// O(n) n = lineEncryptp.length()

			} else
			{
				break;
			}

		} // while(true)

		if (l1 != 0)
		{

			line = "" + mTR[mTRRow[l1]][colMatch] + mBL[rowMatch][mBRCol[l1]];
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
	 * Big-O: Time : O(a) ( a=lineEncryptp.length() ).
	 * 
	 * Estimation : T(n) =25n +5 , n = average number of characters per line , for
	 * the "WarAndPeace-LeoTolstoy" , n=80 and at 2 billion computation per second
	 * (25*80 + 5)/(2*10^9)= 1.0025 *10^(-6) second
	 * 
	 * Big-O: Space : O(a) ( a=lineEncryptp.length() ).
	 * 
	 * Estimation : 2*char + 7*int + 1*boolean + 4a + (the 4 arrays of chars) = 2*2
	 * + 7*4 + 1*2 + 4*80 + 16*4 + 4*2*122 = 1,434 bytes.
	 */
	private void encryptLine(String lineEncryptp)
	{
		if (l1 != 0)// there is a letter carried forward
		{
			l1p = 0;
			letterFordward = true;

		} else
		{
			letterFordward = false;
		}

		// create array for result
		tranformedText = new char[lineEncryptp.length() + 100];

		// loop the line
		for (int position = 0; position < lineEncryptp.length(); position++)// O(n) n = lineEncryptp.length()
		{
			// grab one char
			tCharIn = lineEncryptp.charAt(position);

			// check if is on the range
			if (tCharIn >= minCharacter && tCharIn <= maxCharaceter)
			{
				if (l1 == 0)// first for encrypt
				{

					l1 = tCharIn;// put character

					// assign position
					if (!letterFordward)
					{
						l1p = position;
					} else
					{
						l1p = position + 1;
					}

				} else if (l2 == 0)
				{

					l2 = tCharIn;// put character

					// assign position
					if (!letterFordward)
					{
						l2p = position;
					} else
					{
						l2p = position + 1;
					}

					/// transform the chars and put in the result
					tranformedText[l1p] = mTR[mTRRow[l1]][mTRCol[l2]];

					tranformedText[l2p] = mBL[mBLRow[l2]][mBRCol[l1]];

					// reset the char at null
					l1 = 0;
					l2 = 0;
				}
			} else// not in range
			{

				if (!letterFordward)
				{

					tranformedText[position] = ' ';
				} else
				{
					tranformedText[position + 1] = ' ';
				}

			} // if (inputText.charAt(i) >= 'A' && inputText.charAt(i) <= 'Z')

		} // for (int position = 0; position < lineEncryptp.length(); position++)

		try
		{

			queueOut.put(new String(tranformedText));

		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}// encryptLine(String lineEncryptp)

	/*
	 * Create matrices for encryption
	 */
	/*
	 * Big-O: Time : O(l^2) =O(r) because r= l*l. O(1) if we keep r constant
	 * 
	 * Estimation : 81*4 + 81*4 (each loop runs 81 times with 2 operations plus the
	 * increase and the comparison) = 648 at 2 billion operation per second
	 * 648/(2*10^(9)) = 3.24*10(-7) seconds
	 * 
	 * Big-O: Space : O(r), p =
	 * 
	 * Estimation A 2D char array = (16 +2n)^2 = 4n^2 + 64 n + 256 , n = r=81, plus
	 * 4 *int , -> 4*81*81 + 65*81 +256 +4*4 = 31,781 Bytes
	 * 
	 */
	private void createEncMatrices()
	{

		for (x2 = 0; x2 < matrixSize; x2++)// Time O(n^2) n= MATRIX_SIZE but with MATRIX_SIZE=5 => O(1)(run 25 times),
											// same for space.
		{
			for (x1 = 0; x1 < matrixSize; x1++)
			{
				mBRCol[mTL[x1][x2]] = x2;
				mTRRow[mTL[x1][x2]] = x1;

			} // for(x1)

		} // for(x2)

		for (y2 = 0; y2 < matrixSize; y2++)// Time O(n^2) n= MATRIX_SIZE but with MATRIX_SIZE=5 => O(1)(run 25 times),
											// same for space.
		{
			for (y1 = 0; y1 < matrixSize; y1++)
			{
				mBLRow[mTL[y1][y2]] = y1;
				mTRCol[mTL[y1][y2]] = y2;

			} // for(y1)

		} // for(y2)

	}// createEncMatrices()

}// ExtendedEncryption
