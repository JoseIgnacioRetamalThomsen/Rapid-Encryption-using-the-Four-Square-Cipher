package ie.gmit.sw;

import java.io.File;
import java.util.Scanner;

public class FileManager
{

	public String inputFileName;
	public String outputFileName;

	public File inputFile;
	public File outputFile;

	/*
	 * Display
	 */
	public void displayFileName()
	{
		System.out.println(inputFileName);
	}

	/*
	 * Input File Name
	 */

	// In file
	public void inInputFileName()
	{
		Scanner scanner;
		String input;

		scanner = new Scanner(System.in);

		System.out.print("Please enter the file name: ");
		input = scanner.nextLine();

		while (!checkIfFileExist(input, true))
		{

			System.out.println(
					"File not found, if you are entering a relative file must be in the root foolder of the program\n anyway if we keep not finding your file please enter  the full address (eg: \" c:\\users\\desktop\\myfile.txt \")");

			System.out.print("Please enter the file name: ");
			input = scanner.nextLine();

		} // while (!checkIfFileExist(input, true))

		scanner.close();

		inputFileName = input;

	}// inInputFileName()

	// out file
	public void inOutputFileName()
	{
		Scanner scanner;
		String input;

		scanner = new Scanner(System.in);

		System.out.print("Please enter the new file name: ");
		input = scanner.nextLine();

		while (checkIfFileExist(input, true))
		{

			System.out.println(
					"That file exists please select another name for the new file.");

			System.out.print("Please enter the new file name: ");
			input = scanner.nextLine();

		} // while (!checkIfFileExist(input, true))

		scanner.close();

		outputFileName = input;
		
	}// inOutputFileName()

	// method for check if a file exist
	private boolean checkIfFileExist(String fileName, boolean relativePath)
	{
		File file = new File(fileName);

		return file.isFile();

	}// checkIfFileExist(String fileName, boolean relativePath)

	public static void main(String[] args)
	{
		FileManager f = new FileManager();
		//f.inInputFileName();

		//f.displayFileName();
		f.inOutputFileName();
		
	}
}
