package ie.gmit.sw;

import java.io.*;
import java.net.URL;

public class ParseFile
{

	private StringBuilder parsedFile;

	public ParseFile()
	{
		super();
		this.parsedFile = new StringBuilder();
	}

	public StringBuilder getParseFile()
	{
		return parsedFile;
	}

	public void setParseFileObject(StringBuilder parseFile)
	{
		this.parsedFile = parseFile;
	}

	public void setParseFilePointer(StringBuilder sb)
	{
		sb = parsedFile;
	}
	
	public void clearParseFile()
	{
		this.parsedFile = new StringBuilder();
	}

	public void parseFile(String pathOrURL, boolean isUrl) throws IOException
	{
		String line;
		BufferedReader fileIn;
		int counter = 0;
		if (isUrl)
		{
			URL url = new URL(pathOrURL);
			fileIn = new BufferedReader(new InputStreamReader(url.openStream()));
			while ((line = fileIn.readLine()) != null)
			{
				System.out.println(line);
				System.out.println(counter++);

			}
			
		} else
		{

			fileIn = new BufferedReader(new InputStreamReader(new FileInputStream(pathOrURL)));
			
			while ((line = fileIn.readLine()) != null)
			{
				System.out.println(line);
				System.out.println(counter++);

			}
			
		}
	}

	public static void main(String[] args) throws IOException
	{
		System.out.println("here");
		new ParseFile().parseFile("warandpeace-leotolstoy.txt", false);
	}
}
