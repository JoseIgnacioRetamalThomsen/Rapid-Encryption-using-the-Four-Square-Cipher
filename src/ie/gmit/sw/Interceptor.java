package ie.gmit.sw;

public class Interceptor
{

	boolean isParseDone;
	boolean isEncryptionDone;
	boolean isWriteDone;

	long startTime;
	long stopTime;

	public Interceptor()
	{
		isParseDone = isEncryptionDone = isWriteDone = false;
	}

	public void startTime()
	{
		startTime = System.nanoTime();
	}

	public void stopTime()
	{
		stopTime = System.nanoTime();
	}

	public void displayTimeMS()
	{
		System.out.println((stopTime - startTime)/ Math.pow(10, 6));
	}

	public void parseDone()
	{
		isParseDone = true;
	}

	public void encryptionDone()
	{
		isEncryptionDone = true;
	}

	public void writeDone()
	{
		isWriteDone = true;
	}

}
