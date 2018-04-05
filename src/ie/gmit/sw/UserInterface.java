package ie.gmit.sw;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInterface
{
	// constants
	// menu strings
	final static String mainMenuHeader1 = "Rapid Encryption using the Four-Square Cipher ";
	final static String mainMenuHeader2 = "Jose Ignacio Retamal, Data Strutures & Algorithms, GMIT 2018";
	final static String[] mainMenuOptions =
	{ "Encryption using 5x5 matrices.", "Decryption.", "Exit" };

	// 5x5 matrix strings
	final static String simpleMatrixMenuHeader1 = " Four-Square Cipher. Four 5x5 matrices.";
	final static String simpleMatrixMenuHeader2 = " Each of the 5x5 matrices contains 25 letters(j is remplaced by i). ";
	final static String encryptionAMenuOptions[] =
	{ "Please setup evething.", "Input new keys.", "Random generate key.", "Set Input File.", "Set input URL ",
			"Set output file", "Back to main menu." };

	final static String encryptionAFileOptions[] =
	{ "Please setup evething.", "input file ", "input file url", "output file", "back to keys." };

	// file variable
	Scanner scanner = new Scanner(System.in);

	

	KeyManagerA keyManagerA = new KeyManagerA(scanner);
	FileManager fileManagerA = new FileManager(scanner);

	EncryptAllA encryptAllA = new EncryptAllA(keyManagerA, fileManagerA);

	public void start()
	{

		boolean isProgramRunning = true;

		while (isProgramRunning)
		{

			// print header
			System.out.println();
			System.out.printf("%80s%n",
					"*================================================================================*");
			System.out.printf("|     %-70s     |%n", mainMenuHeader1);
			System.out.printf("|     %-70s     |%n", mainMenuHeader2);
			System.out.printf("%80s%n",
					"*================================================================================*\n\n");

			isProgramRunning = mainMenu(inputMenu(1, mainMenuOptions));

		} // while (isProgramRunning)

	}// start()

	private boolean mainMenu(int menuOption)
	{

		switch (menuOption)
		{
		case 1:

			encryptionAMenu();
			break;

		case 2:

			decryptAMenu();
			break;

		case 3:

			System.out.println("Program terminated.");
			return false;

		}// switch (menuOption)

		return true;

	}// mainMenu(int menuOption)

	private void encryptionAMenu()
	{
		int menuOption;
		boolean isOptionRunning = true;

		boolean isFromUrl = false;
		boolean isSetup = false;

		while (isOptionRunning)
		{
			// print header
			System.out.println("\n\n");
			System.out.printf("%80s%n",
					"*--------------------------------------------------------------------------------*");
			System.out.printf("|     %-70s     |%n", simpleMatrixMenuHeader1);
			System.out.printf("|     %-70s     |%n", simpleMatrixMenuHeader2);
			System.out.printf("%80s%n",
					"*--------------------------------------------------------------------------------*");

			System.out.println("\nPlease setup and pres 1 when ready.\n");

			System.out.println("Actuals keys:");
			keyManagerA.displayTRKey();
			keyManagerA.displayBLKey();
			System.out.println();

			System.out.print("File or URL: ");
			if (isFromUrl)
			{
				if (fileManagerA.getUrlFileName() == null)
				{
					System.out.println("Please setup.");
				} else
				{
					fileManagerA.displayUrl();
					isSetup = true;
				}
			} else
			{
				if (fileManagerA.getInputFileName() == null)
				{
					System.out.println("Please setup.");
				} else
				{
					fileManagerA.displayFileName();
				}
			}
			System.out.println();

			System.out.print("output result to : ");
			if (fileManagerA.getOutputFileName() == null)
			{
				System.out.println("Please setup.");
				isSetup = false;

			} else
			{
				fileManagerA.displayOutputFileName();
				isSetup = true;
			}
			System.out.println();

			if (isSetup)
			{
				encryptionAMenuOptions[0] = "Encript.";
			}

			menuOption = inputMenu(1, encryptionAMenuOptions);

			switch (menuOption)
			{
			// "Use actuals keys.
			case 1:

				encryptAllA.encryptOne(isFromUrl);
				fileManagerA.clear();
				break;

			case 2:
				
				scanner.nextLine();
				keyManagerA.inputTRKey();
				keyManagerA.inputBLKey();

				break;

			// random key
			case 3:
				
				
				keyManagerA.generateRandomKeys();

				break;

			// set input file
			case 4:

				scanner.nextLine();
				fileManagerA.inInputFileName();
				isFromUrl = false;
				break;

			case 5:

				fileManagerA.inputURL();
				break;

			case 6:
				scanner.nextLine();
				fileManagerA.inOutputFileName();
				break;

			case 7:
				isOptionRunning = false;
				break;

			}// switch (menuOption)

		} // while (isOptionRunning)

	}// encryptionAMenu()

	private void decryptAMenu()
	{
		int menuOption;
		boolean isOptionRunning = true;

		while (isOptionRunning)
		{
			// print header
			System.out.println("\n\n");
			System.out.printf("%80s%n",
					"*--------------------------------------------------------------------------------*");
			System.out.printf("|     %-70s     |%n", simpleMatrixMenuHeader1);
			System.out.printf("|     %-70s     |%n", simpleMatrixMenuHeader2);
			System.out.printf("%80s%n",
					"*--------------------------------------------------------------------------------*");

			System.out.println("\nPlease enter new keys or press 1 for continue with the actual keys.\n");

			System.out.println("Actuals keys:");
			keyManagerA.displayTRKey();
			keyManagerA.displayBLKey();
			System.out.println();

			System.out.print("File or URL: ");
			fileManagerA.displayFileName();
			System.out.println();

			System.out.println("output result to : ");
			fileManagerA.displayOutputFileName();
			;

			menuOption = inputMenu(1, encryptionAMenuOptions);

			switch (menuOption)
			{
			// "Use actuals keys.
			case 1:

				encryptAllA.decryptOne();
				fileManagerA.clear();
				break;

			case 2:
				scanner.nextLine();

				keyManagerA.inputTRKey();
				keyManagerA.inputBLKey();

				break;

			// random key
			case 3:

				keyManagerA.generateRandomKeys();

				break;

			// set input file
			case 4:

				fileManagerA.inInputFileName();

				break;

			case 5:

				fileManagerA.inputURL();

				break;

			case 6:
				scanner.nextLine();
				fileManagerA.inOutputFileName();
				break;

			case 7:
				isOptionRunning = false;
				break;

			}// switch (menuOption)

		} // while (isOptionRunning)
	}// decryptAMenu()

	private void fileChoseMenu()
	{
		int menuOption;
		boolean isOptionRunning = true;

		// need to ad from url

		while (isOptionRunning)
		{
			System.out.println("Set file menu");

			menuOption = inputMenu(1, encryptionAFileOptions);

			switch (menuOption)
			{
			//
			case 1:

				break;

			// Input
			case 2:
				fileManagerA.inInputFileName();
				scanner.nextLine();
				break;

			// URL
			case 3:

				break;

			}// switch (menuOption)

		} // while (isOptionRunning)
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

			} else
			{
				isInputRigth = false;
			}

		} // while (isInputRigth)

		// scanner.close();
		return inputNumber;
	}

	/*
	 * private static char[] inputKey25() { Scanner scanner;
	 * 
	 * final int MATRIX_SIZE = 25;
	 * 
	 * String keyInput = new String(); boolean isKeyGood = false;
	 * 
	 * scanner = new Scanner(System.in);
	 * 
	 * HashSet<Character> inputKeySet = new HashSet<Character>();
	 * 
	 * Character tempCharacter;
	 * 
	 * while (!isKeyGood) { System.out.
	 * print("Please enter the 25 uniques  characters  key and then press enter:\n "
	 * ); keyInput = scanner.nextLine().toUpperCase();
	 * 
	 * // check length if (keyInput.length() != MATRIX_SIZE) { System.out.print(
	 * "Key must be 25 charaters long not " + keyInput.length() +
	 * " character, please try again.\n"); } else // check all characters are unique
	 * {
	 * 
	 * inputKeySet = new HashSet<Character>();
	 * 
	 * for (int i = 0; i < keyInput.length(); i++) { tempCharacter = new
	 * Character(keyInput.charAt(i));
	 * 
	 * if (keyInput.charAt(i) >= 'A' && keyInput.charAt(i) <= 'Z' &&
	 * keyInput.charAt(i) != 'J') { if (inputKeySet.add(tempCharacter)) { // all
	 * good } else { System.out.println("the character " + tempCharacter +
	 * " is repeted, please try again.\n"); break; }
	 * 
	 * } else {
	 * System.out.println("the key contains invalid characters, please try again.\n"
	 * ); break; }
	 * 
	 * } // for (int i = 0; i < keyInput.length(); i++)
	 * 
	 * } // if (keyInput.length() != MATRIX_SIZE)
	 * 
	 * if (inputKeySet.size() == MATRIX_SIZE) {
	 * 
	 * isKeyGood = true; }
	 * 
	 * } // while (!isKeyGood)
	 * 
	 * for (Character ca : inputKeySet) { System.out.println(ca); }
	 * 
	 * Character c[] = inputKeySet.toArray(new Character[0]);
	 * 
	 * for (Character ca : c) { System.out.println(ca); }
	 * 
	 * return keyInput.toCharArray(); }
	 */

	/*
	 * private static String inputFileName() { Scanner scanner; String input;
	 * 
	 * // Pattern pattern = Pattern.compile("\\w[.][a-zA-Z][a-zA-Z][a-zA-Z]");
	 * 
	 * scanner = new Scanner(System.in);
	 * 
	 * input = scanner.nextLine();
	 * 
	 * boolean fileExiste = checkIfFileExist(input, true); if (fileExiste) { return
	 * input; } else { return null; }
	 * 
	 * }
	 */
	/*
	 * private static boolean checkIfFileExist(String fileName, boolean
	 * relativePath) { File file = new File(fileName);
	 * 
	 * return file.isFile(); }
	 */
	public static void main(String[] args)
	{

		// new UserInterface().MainMenu();
		UserInterface u = new UserInterface();
		u.start();
		// u.inputFileName();
		// u.MainMenu();
		// System.out.println(u.checkIfFileExist("/out8.txt", true));
		/*
		 * Encryption5x5 e = new Encryption5x5(); e.setKeyBL(new
		 * UserInterface().inputKey25()); e.printKey(); // TODO Auto-generated method
		 * stub
		 */
	}

}
