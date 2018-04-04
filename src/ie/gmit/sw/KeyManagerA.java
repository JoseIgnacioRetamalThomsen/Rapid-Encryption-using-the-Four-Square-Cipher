package ie.gmit.sw;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/*
 * Manage the keys of the 5 side matrices.
 * Input keys by console.
 * Generate Random keys.
 * Display keys.
 */

public class KeyManagerA
{
	private static final int MATRIX_SIZE = 5;

	// Keys :Bottom left and top right matrices
	char[][] mTR; // Top right key.
	char[][] mBL; // Bottom left key.

	Scanner scanner;

	/*
	 * Constructor. Scanner parameter, that the one scanner used for the full
	 * program. Set keys to a default value.
	 */
	public KeyManagerA(Scanner s)
	{
		scanner = s;

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
	}

	/*
	 * Get Keys
	 */

	public char[][] getTRKeyRef()
	{
		return this.mTR;
	}

	/*
	 * Set Keys.
	 */

	/*
	 * Big-O: Time :  effectively O(1).(Comes from setKeyTR(char[] key))
	 * 
	 * Big-O: Space : effectively O(1).
	 */
	
	public void setKeys(char[] tR, char[] bL)
	{
		setKeyTR(tR);//effectively O(1)
		setKeyBL(bL);//effectively O(1)

	}

	// Top right key.
	/*
	 * Big-O: Time : O(n), n = number of characters in the key parameter array. But
	 * since n can't be bigger than 25(n = constant) is effectively O(1). Big-O:
	 * 
	 * Big-O: Space : O(n), n = number of characters in the key parameter array.Same than
	 * time complexity apply so O(1).
	 */
	public boolean setKeyTR(char[] key)
	{
		if (key.length != 25)
			return false;

		int row = 0, col = 0;
		for (Character character : key)
		{
			mTR[row][col++] = character;
			if (col == MATRIX_SIZE)
			{
				col = 0;
				row++;

			} // if (col == MATRIX_SIZE)

		} // for (Character character : key)

		return true;

	}// setKeyTR(char[] key)

	// Bottom left key.
	/*
	 * Big-O: Time : O(n), n = number of characters in the key parameter array. But
	 * since n can't be bigger than 25(n = constant) is effectively O(1). Big-O:
	 * 
	 * Big-O: Space : O(n), n = number of characters in the key parameter array.Same than
	 * time complexity apply so O(1).
	 */
	public boolean setKeyBL(char[] key)
	{
		if (key.length != 25)
			return false;

		int row = 0, col = 0;
		for (Character character : key)
		{
			mBL[row][col++] = character;
			if (col == MATRIX_SIZE)
			{
				col = 0;
				row++;

			} // if (col == MATRIX_SIZE)

		} // for (Character character : key)

		return true;

	}// setKeyBL(char[] key)

	/*
	 * Display keys.
	 */

	// Top Right key.
	/*
	 * Big-O: Time : O(1), it may look like O(n^2) with n = MATRIX_SIZE, but
	 * MATRIX_SIZE = 5 so O(5^2) = O(25)= O(1). If MATRIX_SIZE is variable will be.
	 * 
	 * Big-O: Space : O(n), n = MATRIX_SIZE. Big-O: Space : O(1) Same than Time complexity apply.
	 */
	public void displayTRKey()
	{
		System.out.print("Top  right  key: ");

		for (int i = 0; i < MATRIX_SIZE; i++)
		{
			for (int k = 0; k < MATRIX_SIZE; k++)
			{
				System.out.print(mTR[i][k]);

			} // k

		} // i

		System.out.println();

	}// displayTRKey()

	// Bottom left key.
	/*
	 * Big-O: Time : O(1), it may look like O(n^2) with n = MATRIX_SIZE, but
	 * MATRIX_SIZE = 5 so O(5^2) = O(25)= O(1). If MATRIX_SIZE is variable will be.
	 * 
	 * Big-O: Space : O(n), n = MATRIX_SIZE. Big-O: Space : O(1) Same than Time complexity apply.
	 */
	public void displayBLKey()
	{
		System.out.print("Bottom left key: ");

		for (int i = 0; i < MATRIX_SIZE; i++)
		{
			for (int k = 0; k < MATRIX_SIZE; k++)
			{
				System.out.print(mBL[i][k]);

			} // k

		} // i

		System.out.println();

	}// displayBLKey()

	/*
	 * Input key from console.
	 */

	// Top right key.
	public void inputTRKey()
	{
		System.out.println("Top right key:");
		this.setKeyTR(inputKeyAll());

	}// inputTRKey()

	// Bottom left key
	public void inputBLKey()
	{
		System.out.println("Bottom left key:");
		this.setKeyBL(inputKeyAll());

	}// inputBLKey()

	// Used for input both keys.
	private char[] inputKeyAll()
	{
		// Scanner scanner1 = new Scanner(System.in);

		final int MATRIX_SIZE = 25;

		// Scanner scanner = new Scanner(System.in);

		String keyInput = new String();

		boolean isKeyGood = false;

		HashSet<Character> inputKeySet = new HashSet<Character>();

		Character tempCharacter;

		while (!isKeyGood)
		{
			System.out.print("Please enter the 25 uniques  characters  key and then press enter:");
			keyInput = scanner.nextLine().toUpperCase().trim();

			// check length
			if (keyInput.length() != MATRIX_SIZE)
			{
				System.out.print(
						"Key must be 25 charaters long not " + keyInput.length() + " character, please try again.\n");

			} else // check all characters are unique
			{

				inputKeySet = new HashSet<Character>();

				for (int i = 0; i < keyInput.length(); i++)
				{
					tempCharacter = new Character(keyInput.charAt(i));

					if (keyInput.charAt(i) >= 'A' && keyInput.charAt(i) <= 'Z' && keyInput.charAt(i) != 'J')
					{
						if (inputKeySet.add(tempCharacter))
						{

							// all good
						} else
						{
							System.out.println("the character " + tempCharacter + " is repeted, please try again.\n");

							break;
						}

					} else
					{
						System.out.println("the key contains invalid characters, please try again.\n");

						break;

					} // if (keyInput.charAt(i) >= 'A' && keyInput.charAt(i) <= 'Z' &&
						// keyInput.charAt(i) != 'J')

				} // for (int i = 0; i < keyInput.length(); i++)

			} // if (keyInput.length() != MATRIX_SIZE)

			if (inputKeySet.size() == MATRIX_SIZE)
				isKeyGood = true;

		} // while (!isKeyGood)

		// Character c[] = inputKeySet.toArray(new Character[0]);

		// scanner.close();

		return keyInput.toCharArray();

	}// inputKey()

	/*
	 * Big-O: Time : O(n + m) where n = mTRElements.size() and  m = mBLElements.size() , but since they are always 25 is O(1).
	 * 
	 * Big-O: Space : O(1) same than time apply.
	 */
	public void generateRandomKeys()
	{
		List<Character> mBLElements = new LinkedList<Character>();
		List<Character> mTRElements = new LinkedList<Character>();

		for (int i = 65; i <= 90; i++)//O(1) runs always 25 times.
		{
			if (i == 74)
				continue;
			mTRElements.add((char) (i));
			mBLElements.add((char) (i));
		}

		Collections.shuffle(mTRElements);//O(n), n size of list.
		Collections.shuffle(mBLElements);//O(m), m size of list.

		Character[] cTR = mTRElements.toArray(new Character[mTRElements.size()]);//O(n), n size of list.
		Character[] cBL = mBLElements.toArray(new Character[mBLElements.size()]);//O(m), m size of list.

		char[] tR = new char[25];
		int i = 0;
		for (Character ch : cTR)//O(n), n size of list.
		{
			tR[i++] = (char) ch;
		}

		char[] bL = new char[25];

		i = 0;
		for (Character ch : cBL) //O(m), m size of list.
		{
			bL[i++] = (char) ch;
		}
		setKeys(tR, bL);

	}// generateRandomKeys()

}//KeyManagerA
