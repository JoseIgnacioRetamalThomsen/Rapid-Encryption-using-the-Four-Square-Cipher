package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;
/*
 * Rapid Encryption using the Four-Square Cipher
 * Jose Ignacio Retamal
 * G00351330@gmit.ie
 *
 *Encryption B
 *
 * Each line is encrypted  independent, if the line have and odd 
 * number of characters the extra one will be match with X. Characters that are outside of 
 * the letters range will be keep in the same position.
 * 
 * 
 */

/*
 * Big-O: 
 * n = total number of characters in the input file.
 * m = total number of lines in the input file.
 * a = average number of characters per line in the input file.
 * p = number of characters in one line.
 */

public class EncryptionB implements Runnable
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

	private final BlockingQueue<CharSequence> queueIn;
	private final BlockingQueue<CharSequence> queueOut;

	// mathc for extra letter set to x
	private int colMatch = 2;
	private int rowMatch = 4;

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

	String stringOut;

	/*
	 * Constructor
	 */
	public EncryptionB(BlockingQueue<CharSequence> queueInP, BlockingQueue<CharSequence> queueOutP,
			KeyManagerA keyManagerP)
	{
		queueIn = queueInP;
		queueOut = queueOutP;

		mTR = keyManagerP.mTR;
		mBL = keyManagerP.mBL;

	}// EncryptionB(

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
		//create line for read
		CharSequence line = null;
		//create matrices for encryption
		createEncMatrices();

		while (true)//true while the queue is not empty
		{

			try
			{
				//take for queue
				line = queueIn.take();

			} catch (InterruptedException e)
			{
				
				e.printStackTrace();
			}

			//stop when poison encounter
			if (!(line instanceof Poison))
			{
				encryptLine((String) line);// O(n) n = lineEncryptp.length()

			} else
			{
				break;
			}

		} // while(true)

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
		//create array for result
		tranformedText = new char[lineEncryptp.length() + 1];

		//loop the line
		for (int position = 0; position < lineEncryptp.length(); position++)// O(n) n = lineEncryptp.length()
		{
			//grab one char
			tCharIn = lineEncryptp.charAt(position);

			// to upper case
			if (tCharIn >= 'a' && tCharIn <= 'z')
			{
				tCharIn = (char) (((int) tCharIn) - 32);
			}
			
			//check if is on the range
			if (tCharIn >= 'A' && tCharIn <= 'Z')
			{
				if (l1 == 0)//first for encrypt
				{
					//Replace J for I
					if (tCharIn == 'J')
					{
						l1 = 'I';//put character
					} else
					{
						l1 = tCharIn;//put character
					}
					
					//assign position
					l1p = position;

				} else if (l2 == 0)
				{
					if (tCharIn == 'J')
					{
						l2 = 'I';//put character

					} else
					{
						l2 = tCharIn;//put character
					}

					//assign position
					l2p = position;

					///transform the chars and put in the result
					tranformedText[l1p] = mTR[mTRRow[l1]][mTRCol[l2]];
					tranformedText[l2p] = mBL[mBLRow[l2]][mBRCol[l1]];
					
					//reset the char at null
					l1 = 0;
					l2 = 0;
				}
			} else//not in range
			{

				//just print the character as it is
				tranformedText[position] = tCharIn;

			} // if (inputText.charAt(i) >= 'A' && inputText.charAt(i) <= 'Z')

		} // for (int position = 0; position < lineEncryptp.length(); position++)

		// take care of leeter forward in this line

		if (l1 != 0)// there is a letter carried forward
		{
			// we match it with letter
			tranformedText[l1p] = mTR[mTRRow[l1]][colMatch];
			tranformedText[l1p + 1] = mBL[rowMatch][mBRCol[l1]];
		}
		l1 = 0;
		l2 = 0;

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
