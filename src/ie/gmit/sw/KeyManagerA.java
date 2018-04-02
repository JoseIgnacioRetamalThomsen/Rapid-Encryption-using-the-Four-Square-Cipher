package ie.gmit.sw;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class KeyManagerA
{
	private static final int MATRIX_SIZE = 5;

	// Keys :Bottom left and top right matrices
	char[][] mTR; // Top right key.
	char[][] mBL; // Bottom left key.

	
	Scanner scanner;
	/*
	 * Constructor
	 */
	public KeyManagerA(Scanner s)
	{
		scanner =s;

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
	 * Get
	 */

	public char[][] getTRKeyRef()
	{
		return this.mTR;
	}
	
	/*
	 * Set Keys.
	 */
	
	public void setKeys(char[] tR, char[] bL )
	{
		setKeyTR(tR);
		setKeyBL(bL);
		
	}

	// Top right key.
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
	
	//Top right key.
	public void inputTRKey()
	{
		this.setKeyTR(inputKeyAll());
		
	}//inputTRKey()

	
	//Bottom left key
	public void inputBLKey()
	{
		this.setKeyBL(inputKeyAll());
		
	}//inputBLKey()
	
	
	//Used for input both keys.
	private char[] inputKeyAll()
	{
		//Scanner scanner1 = new Scanner(System.in);
	
		final int MATRIX_SIZE = 25;

		//Scanner scanner = new Scanner(System.in);

		String keyInput = new String();

		boolean isKeyGood = false;

		HashSet<Character> inputKeySet = new HashSet<Character>();

		Character tempCharacter;

		while (!isKeyGood)
		{
			System.out.print("Please enter the 25 uniques  characters  key and then press enter:\n ");
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
						
					}//if (keyInput.charAt(i) >= 'A' && keyInput.charAt(i) <= 'Z' && keyInput.charAt(i) != 'J')

				} // for (int i = 0; i < keyInput.length(); i++)

			} // if (keyInput.length() != MATRIX_SIZE)

			if (inputKeySet.size() == MATRIX_SIZE) isKeyGood = true;
							
		} // while (!isKeyGood)

		//Character c[] = inputKeySet.toArray(new Character[0]);

	//	scanner.close();

		return keyInput.toCharArray();
		
	}// inputKey()

	
	public void generateRandomKeys()
	{
		List<Character> mBLElements = new LinkedList<Character>();
		List<Character> mTRElements = new LinkedList<Character>();

		for (int i = 65; i <= 90; i++)
		{
			if(i==74) continue;
			mTRElements.add((char) (i));
			mBLElements.add((char) (i));
		}

	
		Collections.shuffle(mTRElements);
		Collections.shuffle(mBLElements);
	
		Character[] cTR = mTRElements.toArray(new Character[0]);
		Character[] cBL = mBLElements.toArray(new Character[0]);
		
		char[] tR = new char[25];
		int i =0;
		for(Character ch: cTR)
		{
			tR[i++] = (char)ch;
		}
		
		char[] bL = new char[25];
		
		 i =0;
		for(Character ch: cBL)
		{
			bL[i++] = (char)ch;
		}
		setKeys(tR, bL);
	
		
	}//generateRandomKeys()
	
	public static void main(String[] args)
	{
		/*
		 * Top right key: ZGPTFOIHMUWDRCNYKEQAXVSBL 
		 * Bottom left key: MFNBDCRHSAXYOGVITUEWLQZKP
		 */
	//	KeyManagerA t = new KeyManagerA();

		

	}

}
