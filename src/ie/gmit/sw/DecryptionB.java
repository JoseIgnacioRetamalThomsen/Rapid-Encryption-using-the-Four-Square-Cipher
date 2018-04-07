package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;

public class DecryptionB implements Runnable
{
	private final BlockingQueue<CharSequence> queueIn;
	private final BlockingQueue<CharSequence> queueOut;

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

	String stringOut;

	/*
	 * Constructor
	 */
	DecryptionB(BlockingQueue<CharSequence> queueInP, BlockingQueue<CharSequence> queueOutP, KeyManagerA keyManagerP)
	{
		queueIn = queueInP;

		queueOut = queueOutP;

		this.mTR = keyManagerP.mTR;
		this.mBL = keyManagerP.mBL;

	}

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

		tranformedText = new char[lineEncryptp.length() + 1];

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
					l1p = position;

				} else if (l2 == 0)
				{
					if (tCharIn == 'J')
					{
						l2 = 'I';

					} else
					{
						l2 = tCharIn;
					}

					l2p = position;

					tranformedText[l1p] = mTL[mTRRow[l1]][mTRCol[l2]];

					tranformedText[l2p] = mTL[mBLRow[l2]][mBRCol[l1]];
					l1 = 0;
					l2 = 0;
				}
			} else
			{

				tranformedText[position] = tCharIn;

			} // if (inputText.charAt(i) >= 'A' && inputText.charAt(i) <= 'Z')

		} // encryptLine(String lineEncryptp)

		//just reset we don't expect extra letter when decrypt 
		l1 = 0;
		l2 = 0;

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

}// DecryptionB
