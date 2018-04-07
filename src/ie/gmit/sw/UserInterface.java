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
			"Set output file", "Set memory constraint.", "Back to main menu." };

	final static String decryptionAMenuOptions[] =
	{ "Please setup evething.", "Input new keys.", "Random generate key.", "Set Input File.", "Set input URL ",
			"Set output file", "Set memory constraint.", "Back to main menu." };

	final static String encryptionAFileOptions[] =
	{ "Please setup evething.", "input file ", "input file url", "output file", "back to keys." };

	// file variable
	Scanner scanner = new Scanner(System.in);

	KeyManagerA keyManagerA = new KeyManagerA(scanner);
	FileManager fileManagerA = new FileManager(scanner);

	EncryptAndDecrypt encryptAllA = new EncryptAndDecrypt(keyManagerA, fileManagerA);

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

			System.out.print("Memory constraint:  ");
			System.out.println(encryptAllA.getQueueSize());
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

				encryptAllA.encryptA(isFromUrl);
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

				setMemoryConstraint();
				break;

			case 8:

				isOptionRunning = false;
				fileManagerA.clear();
				break;

			}// switch (menuOption)

		} // while (isOptionRunning)

	}// encryptionAMenu()

	private void setMemoryConstraint()
	{
		int m;
	
		while (true)
		{
			try
			{
				System.out.print(
						
						"Please enter memory constraint, must be bigger than 0 and smaller than 2147483647(Higher value more memory used). ");
			m =scanner.nextInt();//nextLine();
			//	m = Integer.parseInt(scanner.nextLine());
			

				if(m<=0||m>2147483647) throw new Exception();
				break;
			} catch (Exception e)
			{
				
				scanner.nextLine();
			}
		}
		encryptAllA.setQueueSize(m);

	}// setMemoryConstraint()

	private void decryptAMenu()
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
				decryptionAMenuOptions[0] = "Decrypt.";
			}

			menuOption = inputMenu(1, decryptionAMenuOptions);

			switch (menuOption)
			{
			// "Use actuals keys.
			case 1:

				encryptAllA.decryptA(isFromUrl);
				fileManagerA.clear();
				break;

			case 2:

				scanner.nextLine();
				keyManagerA.inputTRKey();
				keyManagerA.inputBLKey();

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
				fileManagerA.clear();
				break;

			}// switch (menuOption)

		} // while (isOptionRunning)
	}// decryptAMenu()

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

		return inputNumber;

	}// inputMenu(int lower, String[] options)

}// UserInterface
