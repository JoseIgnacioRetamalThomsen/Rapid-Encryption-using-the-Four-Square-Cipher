package ie.gmit.sw;

import java.util.Scanner;

/*
 * Rapid Encryption using the Four-Square Cipher
 * Jose Ignacio Retamal
 * G00351330@gmit.ie
 *
 * Graphic user interface using console.
 * 
 * 
 */

public class UserInterface
{
	// constants
	// menu strings
	final static String mainMenuHeader1 = "Rapid Encryption using the Four-Square Cipher ";
	final static String mainMenuHeader2 = "Jose Ignacio Retamal, Data Strutures & Algorithms, GMIT 2018";
	final static String[] mainMenuOptions =
	{ "Encryption.", "Decryption.", "Extended Encryption/Decryption.", "Exit." };

	// 5x5 matrix strings
	final static String simpleMatrixMenuHeader1A = " Encryption.";
	final static String simpleMatrixMenuHeader1B = " Decryption.";
	final static String simpleMatrixMenuHeader2 = " Each of the 5x5 matrices contains 25 letters(j is remplaced by i). ";
	final static String encryptionAMenuOptions[] =
	{ "Please setup everything.", "Input new keys.", "Random generate key.", "Set Input File.", "Set input URL ",
			"Set output file", "Set memory constraint.", "Change Algorith.", "Back to main menu." };

	final static String decryptionAMenuOptions[] =
	{ "Please setup everything.", "Input new keys.", "Random generate key.", "Set Input File.", "Set input URL ",
			"Set output file", "Set memory constraint.", "Change Algorith.", "Back to main menu." };

	// extended headers
	final static String extendMatrixMenuHeader1A = " Extended Encryption/Decryption.";
	final static String extendMatrixMenuHeader2 = " Extend algorith using 9 size matrix. ";

	final static String extendedMenuOptions[] =
	{ "Encrypt", "Change Encryption/Decryption.", "Random generate key.", "Set Input File.", "Set input URL ",
			"Set output file", "Set memory constraint.", "Back to main menu." };

	// file variable
	Scanner scanner = new Scanner(System.in);

	KeyManagerA keyManagerA = new KeyManagerA(scanner);
	FileManager fileManagerA = new FileManager(scanner);

	EncryptAndDecrypt encryptAllA = new EncryptAndDecrypt(keyManagerA, fileManagerA);

	// extended
	ExtendedKeyManager eKeyManager = new ExtendedKeyManager(scanner);

	ExtendedEncryptAndDecrypt eEncrypt = new ExtendedEncryptAndDecrypt(eKeyManager, fileManagerA);

	public void start()
	{

		boolean isProgramRunning = true;

		while (isProgramRunning)
		{

			// display header
			displayHeader(mainMenuHeader1, mainMenuHeader2);

			isProgramRunning = mainMenu(inputMenu(1, mainMenuOptions));

		} // while (isProgramRunning)

	}// start()

	private boolean mainMenu(int menuOption)
	{

		switch (menuOption)
		{
		// encryption
		case 1:

			encryptionAMenu();
			break;

		// decryption
		case 2:

			decryptAMenu();
			break;

		// extended
		case 3:

			extendedEncDecMenu();
			break;

		// exit
		case 4:

			System.out.println("Program terminated.");
			return false;

		}// switch (menuOption)

		return true;

	}// mainMenu(int menuOption)

	private void extendedEncDecMenu()
	{

		int menuOption;

		boolean isFromUrl = false;
		boolean isSetup = false;

		boolean isEncrypt = true;

		boolean isOptionRunning = true;
		while (isOptionRunning)
		{
			// display header
			displayHeader(extendMatrixMenuHeader1A, extendMatrixMenuHeader2);

			System.out.println("\nPlease setup and input 1 when ready.\n");

			// display keys
			System.out.println("Actuals keys:");
			eKeyManager.displayTRKey();
			eKeyManager.displayBLKey();
			System.out.println();

			// display file or URL
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

			// display output file name
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

			// display memory constraint
			System.out.print("Memory constraint:  ");
			System.out.println(encryptAllA.getQueueSize());

			// display if encrypt or decrypt
			System.out.println(isEncrypt ? "Encrypt." : "Decrypt. ");
			// display encryp or decryt
			extendedMenuOptions[0] = isEncrypt ? "Encrypt." : "Decrypt. ";
			System.out.println();

			// read option
			menuOption = inputMenu(1, extendedMenuOptions);

			switch (menuOption)
			{
			// Encrypt decrypt
			case 1:
				if (isSetup)
				{
					if (isEncrypt)
					{
						eEncrypt.encrypt(isFromUrl);
					} else
					{
						eEncrypt.decrypt(isFromUrl);
					}
				}//if (isSetup)
				
			
				break;

			// change to enc/dec
			case 2:

				isEncrypt = !isEncrypt;
				// exit
				break;

			// random generate keys
			case 3:

				eKeyManager.generateRandomKeys();
				break;

			// set input file
			case 4:

				fileManagerA.inInputFileName();
				isFromUrl = false;
				break;

			// input URL
			case 5:

				fileManagerA.inputURL();
				isFromUrl = true;
				break;

			// input output file name
			case 6:

				fileManagerA.inOutputFileName();
				break;

			// set memory constraint
			case 7:

				setMemoryConstraint();
				break;

			// back
			case 8:
				isOptionRunning = false;
				break;
			}
		}
	}

	private void encryptionAMenu()
	{
		int menuOption;
		boolean isOptionRunning = true;

		boolean isFromUrl = false;
		boolean isSetup = false;

		boolean usingAlgorithA = true;

		while (isOptionRunning)
		{

			// display header
			displayHeader(simpleMatrixMenuHeader1A, simpleMatrixMenuHeader2);

			System.out.println("\nPlease setup and input 1 when ready.\n");

			// display keys
			System.out.println("Actuals keys:");
			keyManagerA.displayTRKey();
			keyManagerA.displayBLKey();
			System.out.println();

			// display file or URL
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

			// display output file name
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

			// display memory constraint
			System.out.print("Memory constraint:  ");
			System.out.println(encryptAllA.getQueueSize());

			// display algorithm selected
			System.out.print("Algoritm selected: ");
			System.out.println(usingAlgorithA
					? "A : any not letter character is removed, when odd line character is passed to next line, if the total amount of characters is odd the last will be mathc with X."
					: "B : Characters that are not letters are keeped in same position, if a line have an odd number of characters the last will be match with X. ");
			System.out.println();

			// check if is everything setup and change first options if it is
			if (isSetup)
			{
				encryptionAMenuOptions[0] = "Encrypt.";
			}

			// display options and read input
			menuOption = inputMenu(1, encryptionAMenuOptions);

			// switch inputed option
			switch (menuOption)
			{
			// "Use actuals keys/Encrypt.
			case 1:

				if (isSetup)
				{
					if (usingAlgorithA)
						encryptAllA.encryptA(isFromUrl);
					else
						encryptAllA.encryptB(isFromUrl);

					fileManagerA.clear();
					scanner.nextLine();

				} // if (isSetup)

				break;

			// input keys
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
				isFromUrl = false;
				break;

			// input URL
			case 5:

				fileManagerA.inputURL();
				isFromUrl = true;
				break;

			// input output file name
			case 6:

				fileManagerA.inOutputFileName();
				break;

			// set memory constraint
			case 7:

				setMemoryConstraint();
				break;

			// change algorithm
			case 8:

				usingAlgorithA = usingAlgorithA ? false : true;
				break;

			// exit
			case 9:

				isOptionRunning = false;
				fileManagerA.clear();
				break;

			}// switch (menuOption)

		} // while (isOptionRunning)

	}// encryptionAMenu()

	private void decryptAMenu()
	{
		int menuOption;
		boolean isOptionRunning = true;

		boolean isFromUrl = false;
		boolean isSetup = false;

		boolean usingAlgorithA = true;

		while (isOptionRunning)
		{
			// display header
			displayHeader(simpleMatrixMenuHeader1B, simpleMatrixMenuHeader2);

			System.out.println("\nPlease setup and input 1 when ready.\n");

			// display keys
			System.out.println("Actuals keys:");
			keyManagerA.displayTRKey();
			keyManagerA.displayBLKey();
			System.out.println();

			// display file or URL
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

			// display output file name
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

			// display memory constraint
			System.out.print("Memory constraint:  ");
			System.out.println(encryptAllA.getQueueSize());

			// display algorithm selected
			System.out.print("Algoritm selected: ");
			System.out.println(usingAlgorithA
					? "A : any not letter character is removed, when odd line character is passed to next line, if the total amount of characters is odd the last will be mathc with X."
					: "B : Characters that are not letters are keeped in same position, if a line have an odd number of characters the last will be match with X. ");
			System.out.println();

			// check if is everything setup and change first options if it is
			if (isSetup)
			{
				decryptionAMenuOptions[0] = "Decrypt.";
			}

			// display options and read input
			menuOption = inputMenu(1, decryptionAMenuOptions);

			// switch inputed option
			switch (menuOption)
			{
			// "Use actuals keys/Encrypt.
			case 1:

				if (isSetup)
				{
					if (usingAlgorithA)
						encryptAllA.decryptA(isFromUrl);
					else
						encryptAllA.decryptB(isFromUrl);

					fileManagerA.clear();
					scanner.nextLine();

				} // if (isSetup)

				break;

			// input keys
			case 2:

				keyManagerA.inputTRKey();
				keyManagerA.inputBLKey();

				// random key
			case 3:

				keyManagerA.generateRandomKeys();

				break;

			// set input file
			case 4:

				fileManagerA.inInputFileName();
				isFromUrl = false;
				break;

			// set URL
			case 5:

				fileManagerA.inputURL();
				isFromUrl = true;
				break;

			// set output file name
			case 6:

				fileManagerA.inOutputFileName();
				break;
			// set memory constraint
			case 7:

				setMemoryConstraint();
				break;

			// change algorithm
			case 8:

				usingAlgorithA = usingAlgorithA ? false : true;
				break;

			// exit
			case 9:

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

		while (true)
		{
			try
			{
				System.out.print("Please select choice and press enter:");
				inputNumber = Integer.parseInt(scanner.nextLine());
				// scanner.nextLine();

				if (inputNumber < lower || inputNumber > index)
				{
					System.out.println("Wrong input, please enter a number.");

				} else
				{
					break;
				}

				// break;

			} catch (Exception e)
			{

				System.out.print("Wrong input, please enter a number.\n");

				// System.out.print("Please select choice and press enter:");
				// scanner.nextLine();

			}

		} // while (isInputRigth)

		return inputNumber;

	}// inputMenu(int lower, String[] options)

	private void displayHeader(String h1, String h2)
	{
		// print header
		System.out.println("\n\n");
		System.out.printf("%80s%n",
				"*--------------------------------------------------------------------------------*");
		System.out.printf("|     %-70s     |%n", h1);
		System.out.printf("|     %-70s     |%n", h2);
		System.out.printf("%80s%n",
				"*--------------------------------------------------------------------------------*");
	}

	private void setMemoryConstraint()
	{
		int m;

		while (true)
		{
			try
			{
				System.out.print(

						"Please enter memory constraint, must be bigger than 0 and smaller than 2147483647(Higher value more memory used). ");
				m = scanner.nextInt();
				scanner.nextLine();
				

				if (m <= 0 || m > 2147483647)
					throw new Exception();
				break;
			} catch (Exception e)
			{

				scanner.nextLine();
			}
		}
		encryptAllA.setQueueSize(m);

	}// setMemoryConstraint()

}// UserInterface
