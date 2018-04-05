package ie.gmit.sw;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class FileManager
{

	public String inputFileName;
	public String outputFileName;
	public String urlFileName;

	public File inputFile;
	public File outputFile;

	private Scanner scanner;

	public FileManager(Scanner scanner)
	{
		this.scanner = scanner;
	}

	/*
	 * Display
	 */
	public void displayUrl()
	{

		System.out.println(urlFileName);
	}

	public String getInputFileName()
	{
		return inputFileName;
	}

	public void setInputFileName(String inputFileName)
	{
		this.inputFileName = inputFileName;
	}

	public String getOutputFileName()
	{
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName)
	{
		this.outputFileName = outputFileName;
	}

	public String getUrlFileName()
	{
		return urlFileName;
	}

	public void setUrlFileName(String urlFileName)
	{
		this.urlFileName = urlFileName;
	}

	public void displayFileName()
	{
		System.out.println(inputFileName);
	}

	public void displayOutputFileName()
	{
		System.out.println(outputFileName);
	}
	/*
	 * Input File Name
	 */

	// In file
	public void inInputFileName()
	{

		String input;

		// scanner = new Scanner(System.in);

		System.out.print("Please enter the file name: ");
		input = scanner.nextLine().trim();

		while (!checkIfFileExist(input, true))
		{

			System.out.println(
					"File not found, if you are entering a relative file must be in the root foolder of the program\n anyway if we keep not finding your file please enter  the full address (eg: \" c:\\users\\desktop\\myfile.txt \")");

			System.out.print("Please enter the file name: ");
			input = scanner.nextLine().trim();

		} // while (!checkIfFileExist(input, true))

		inputFileName = input;

	}// inInputFileName()

	// out file
	public void inOutputFileName()
	{
		// Scanner scanner;
		String input;

		// scanner = new Scanner(System.in);

		System.out.print("Please enter the new file name: ");
		input = scanner.nextLine().trim();

		while (checkIfFileExist(input, true))
		{

			System.out.println("That file exists please select another name for the new file.");

			System.out.print("Please enter the new file name: ");
			input = scanner.nextLine().trim();

		} // while (!checkIfFileExist(input, true))

		outputFileName = input;

	}// inOutputFileName()

	// method for check if a file exist
	private boolean checkIfFileExist(String fileName, boolean relativePath)
	{
		File file = new File(fileName);

		return file.isFile();

	}// checkIfFileExist(String fileName, boolean relativePath)

	@SuppressWarnings("unused")
	public boolean checkIfUrlExist(String urlString)
	{
		try
		{
			URL url = new URL(urlString);

		} catch (Exception e)
		{
			return false;

		}
		return true;

	}// checkIfUrlExist(String urlString)

	// clear name
	public void clear()
	{
		this.inputFile = null;
		this.outputFile = null;
		this.inputFileName = null;
		this.outputFileName = null;

	}// clear()

	public void inputURL()
	{
		String input;

		// scanner = new Scanner(System.in);

		System.out.print("Please enter the file URL: ");
		input = scanner.nextLine().trim();

		while (!checkIfUrlExist(input))
		{

			System.out.println("That URN do not exists please try again.");

			System.out.print("Please enter the file URL: ");
			input = scanner.nextLine().trim();

		} // while (!checkIfFileExist(input, true))

		urlFileName = input;

	}// inputURL()

}// FileManager
