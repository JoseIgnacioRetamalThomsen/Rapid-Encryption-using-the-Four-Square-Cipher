package ie.gmit.sw;

import java.io.IOException;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface
{
	// constants
	// menu strings
	final String mainMenuHeader1 = "Rapid Encryption using the Four-Square Cipher ";
	final String mainMenuHeader2 = "Jose Ignacio Retamal, Data Strutures & Algorithms, GMIT 2018";
	final String[] mainMenuOptions =
	{ "Encryption using four 5x5 matrices, 25 letters, j is remplaced by i",
			"nxn matrix using a chosed sequence of character from asc table", "Exit" };

	// 5x5 matrix strings
	final String simpleMatrixMenuHeader1 = " Four-Square Cipher using 25 english alphabet letters";
	final String simpleMatrixMenuHeader2 = " ";
	final String simpleMatrixOptions[] =
	{ "Input Keys in console.", "Input keys using file.", "Random generate keyy.", "Use last keys.",
			"Back to main menu." };

	// En/De objects
	Encryption5x5 encrypt5;

	// file variable
	Scanner scanner;

	private void MainMenu()
	{
		int menuOption;
		boolean isProgramRunning = true;

		while (isProgramRunning)
		{
			// print header
			System.out.printf("*%80s*%n",
					"*=============================================================================*");
			System.out.printf("|     %-70s     |%n", mainMenuHeader1);
			System.out.printf("|     %-70s     |%n", mainMenuHeader2);
			System.out.printf("*%80s*%n",
					"*=============================================================================*");

			menuOption = inputMenu(1, mainMenuOptions);

			switch (menuOption)
			{
			case 1:
				doEncryption5();
				break;
			case 2:
				System.out.println("2");
				break;
			case 3:
				isProgramRunning = false;
				System.out.println("Program terminated.");
				break;
			}
		}
	}// MainMenu()

	private void doEncryption5()
	{
		int menuOption;
		boolean isOptionRunning = true;

		while (isOptionRunning)
		{
			// print header
			System.out.println("\n\n");
			System.out.printf("|%80s|%n",
					"*-----------------------------------------------------------------------------*");
			System.out.printf("|     %-70s     |%n", simpleMatrixMenuHeader1);
			System.out.printf("|     %-70s     |%n", simpleMatrixMenuHeader2);
			System.out.printf("|%80s|%n",
					"*-----------------------------------------------------------------------------*");

			menuOption = inputMenu(1, simpleMatrixOptions);

			switch (menuOption)
			{
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				isOptionRunning = false;
				break;
			}

		} // while (isProgramRunning)
	}

	/*
	 * Method for show menu option and get the choice from console lower is the
	 * first option which will be increase by one until the length of the array
	 * options
	 */
	private int inputMenu(int lower, String[] options)
	{

		int inputNumber = 0;
		boolean isInputRigth = true;
		scanner = new Scanner(System.in);
		StringBuilder menuOptions = new StringBuilder();

		// int number = scanner.nextInt();

		// compose menu
		int index = lower;
		for (String optionString : options)
		{
			menuOptions.append(index++ + "-> ");
			menuOptions.append(optionString);
			menuOptions.append("\n");
		}

		--index;
		System.out.println(menuOptions);
		System.out.print("Please enter Choice and press enter:");
		while (isInputRigth)
		{
			try
			{
				inputNumber = scanner.nextInt();
				isInputRigth = false;
			} catch (InputMismatchException e)
			{
				System.out.println("Wrong input, please enter a number.");
				System.out.print("Please enter Choice and press enter:");

				scanner.nextLine();

			}

			if (inputNumber < lower || inputNumber > index)
			{
				System.out.println("Wrong input, please enter a number.");
				System.out.print("Please enter Choice and press enter:");

				isInputRigth = true;

				scanner.nextLine();

			} else
			{
				isInputRigth = false;
			}

		}

		return inputNumber;
	}

	private Character[] inputKey25()
	{
		final int MATRIX_SIZE = 25;
		
		String keyInput = new String();
		boolean isKeyGood = false;

		scanner = new Scanner(System.in);

		HashSet<Character> inputKeySet = new HashSet<Character>();

		Character tempCharacter;

		while (!isKeyGood)
		{
			System.out.print("Please enter the 25 uniques  characters  key and then press enter:\n ");
			keyInput = scanner.nextLine().toUpperCase();

			// check lenght
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

						if (inputKeySet.size() == MATRIX_SIZE)
						{
							isKeyGood = true;
						}
					} else
					{
						System.out.println("the key contains invalid characters, please try again.\n");
						break;
					}
				}
			}

		}

		Character[] c = inputKeySet.toArray(new Character[0]);
		for (Character ch : c)
		{
			System.out.println(ch);
		}
		return inputKeySet.toArray(new Character[0]);
	}

	public static void main(String[] args)
	{
		String op[] =
		{ "one", "two" };
		// new UserInterface().MainMenu();
		new UserInterface().inputKey25();
		// TODO Auto-generated method stub

	}

}
