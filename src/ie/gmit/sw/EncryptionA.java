package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;

/*
  *Encryption using a 5 side matrix 
 * Line by line , if a line have a odd number of characters the character is pass over to the next line
 * if the last line have a odd number of characters the last character  will be encrypted with the first 
 * element in the matrix (row=0, col=0).
 */

/*
 * Big-O: 
 * n = total number of characters in the input file.
 * m = total number of lines in the input file.
 * a = average number of characters per line in the input file.
 * p = number of characters in one line.
 */
public class EncryptionA implements Runnable
{
	private final static int MATRIX_SIZE = 5;

	// Array with the 5x5 matrix with the 25 letters (all minus j)
	final char[][] mTL =
	{
			{ 'A', 'B', 'C', 'D', 'E' },
			{ 'F', 'G', 'H', 'I', 'K' },
			{ 'L', 'M', 'N', 'O', 'P' },
			{ 'Q', 'R', 'S', 'T', 'U' },
			{ 'V', 'W', 'X', 'Y', 'Z' } };

	// mathc for extra letter set to x
	private int colMatch = 2;
	private int rowMatch = 4;

	private final BlockingQueue<CharSequence> queueIn;
	private final BlockingQueue<CharSequence> queueOut;

	// Bottom left and top right matrices
	char[][] mTR;
	char[][] mBL;

	/**** Encryption variables ******/
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

	}// EncryptionA(

	/*
	 * Big-O (5): Time :O(n) = O(a x m) Look at WriteFile (1).
	 * 
	 * Estimation  (6): TimeToRun = createEncMatrices()Time + m*encryptLine()Time
	 * +10/(3*10^9) using the values calculated below and m= 64,927 TimeToRun =
	 * 1*10(-7)  seconds + 64,927* 1.0025 *10^(-6) second second (we can forget about that
	 * 10...) TimeTorun = 0.0650894 seconds , We can see that only the part
	 * m*encryptLine()Time have a relevant value , because m*encryptLine()Time= 0.0650893
	 * 
	 * Big-O: Space (7) : O(a*m1) m1= queue.Size() since queue.Size()= cons -> O(a).
	 * So same than WriteLine (3) apply , because in worst case all characters are in one line so will be O(a),
	 * but if the characters are well distributes in the lines (all lines with more or less same amount of characters) with  will be O(1).
	 * 
	 * Estimation (8) : SpaceUsed = createEncMatrices() + m1*encryptLine() , using the default value m1=10,000 
	 * sapaceUsed = 4,576 bits + 10,000*21,376 bits = 213,764,576 bits = 213.8 MB
	 */
	@Override
	public void run()
	{
		CharSequence line = null;
		createEncMatrices();// Big-O time: O(1)

		while (true)//Big-O Time: O(m*a) , Space : O(a*m1) m1= queue.Size()

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
				encryptLine((String) line);//Time and space O(a)

			} else
			{
				break;
			}

		} // while(true)

		// check if there is last letter
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
	 * Estimation : 2*char + 7*int + 1*boolean + n + (the 4 arrays of chars) = 2*32
	 * + 7*32 + 1*32 + 80 + 4n^2 + 64 n + 256 = 21,376 bits (assuming that boolean
	 * use 32 bits and 80 average characters per line)
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

		tranformedText = new char[lineEncryptp.length() + 1];

		// lineEncryptp = lineEncryptp.toUpperCase();

		for (int position = 0; position < lineEncryptp.length(); position++)// O(n) n = lineEncryptp.length()
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

		for (x2 = 0; x2 < MATRIX_SIZE; x2++)// Time O(n^2) n= MATRIX_SIZE but with MATRIX_SIZE=5 => O(1)(run 25 times),
											// same for space.
		{
			for (x1 = 0; x1 < MATRIX_SIZE; x1++)
			{
				mBRCol[mTL[x1][x2]] = x2;
				mTRRow[mTL[x1][x2]] = x1;

			} // for(x1)

		} // for(x2)

		for (y2 = 0; y2 < MATRIX_SIZE; y2++)// Time O(n^2) n= MATRIX_SIZE but with MATRIX_SIZE=5 => O(1)(run 25 times),
											// same for space.
		{
			for (y1 = 0; y1 < MATRIX_SIZE; y1++)
			{
				mBLRow[mTL[y1][y2]] = y1;
				mTRCol[mTL[y1][y2]] = y2;

			} // for(y1)

		} // for(y2)

	}// createEncMatrices()

}// EncryptionA
