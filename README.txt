	Rapid Encryption using the Four-Square Cipher
	*********************************************
	Jose Ignacio Retamal
	G00351330@gmit.ie

	When you just run the program, you will get 3 options, one for Encrypt another for decrypt and the
last one for exit the program. In the encrypt and decrypt you will need to setup the input and output(4 and 6 ) 
file or URL(At least 5), when that is done you can chose the option for encrypt/decrypt. 
	In this file I list the features of the program and after I do I quick experiment for the encryption 
algorithm, I am not adding the files used to test to submission because it take to long to compress, I can 
send it on request.
	The last think I do (since we got some extra time), was an extended algorithm that support more letters (91) 
and can be easily extend to a bigger range of letters.


	Features:
	---------

	- Multithread using LinkedBlockigQueue, use 3 threads (parse, encrypt/decrypt and write) for encrypt/decrypt.
	- Memory constraint can be set, this is the size of the queues used. It can be set to different values (from 1 
 	 to 2147483647). It can be set to low values for use less memory, encryption/decryption will be slower at small
 	 queue size. Also, can handle big files using the same amount of memory.
	- Encrypt and Decrypt line by line, 2 variations of the algorithm:
  	 A: Treat the file as a one big String (if there are odd lines the extra character is passed to the next line). 
  	 If the total amount characters is odd, the last character is match with the letter X, any characters that is 
  	 no in the 25 letters range is replaced with a white space.
     B:  Each line is encrypted/decrypted independent, if the line have and odd number of characters the extra one
     will be match with X. Characters that are outside of the letters range will be keep in the same position.
     Note: They are compatible.
	- Generate random keys, using LinkedList and Collections.shuffle().
	- Input key from console, use HashSet for check that has no repeated elements.
	- Input file path, check if the file exists before proceeding.
	- Input by URL, check if the URL before proceeding.
	- Choose output file, check that the file does not exist an if not create a new file for the output.
	- Some basic input error checking.
	- Output time taken for encrypt/decrypt.
 
	-Extended encryption/decryption algorithm, the same algorithm extended for use a 9-side matrix.


	Big-O test with WarAndPeace-LeoTolstoy.txt Time
	-----------------------------------------------


	I will test the program with different file size, the WarAndPeace-LeoTolstoy.txt will be use as base then 10x, 
100x and 500x the same file. I will compare the expected big-O values in comparation with the estimation and 
the estimated value for bigger file.

	I have estimate a value of 0.065 second for encrypt the file so we expect considering that the algorithm is O(n):

       Normal file         10x          100x          500x
time   0.065sec            0.65sec      6.5sec        32.5sec

	This are the values obtained using a i3-4170 3.75 Ghz and two cores: 

EncryptA algorithm:                          
       Normal file         10x          100x          500x
Time   0.15sec             0.51sec      4.52sec       24.81sec        

	We can see that the first value is no so good for the estimation but when the file gets bigger the estimation is 
better. After they all I try again the first file and get a much better value: 0.046sec that is a really good 
value because the calculation is for 2 billion operations for second and the processor used can do mora than that. Some students where talking about that the JVM need to worm out before so I guess for that I get the low value for the first test.
Now I want to compare how the second value get if for the normal file evolve in comparation with the O(n). So, 
will do theoretical values for 0.046 with O(n)

		Normal file experimental value    10x theoretical value  100x theoretical  500x theoretical
Time       0.046sec                           0,46sec            4.6sec            23sec


	Here we can see how the algorithm scale very well and the experimental values are accord with the theoretical 
values expected, so we have proof that the algorithm is O(n).
	Also, I conclude that the estimated time was pretty much in good range.


	Extended Algorithm
	-----------------------------------------------


		I have easy extended the algorithm for cover more letters, I select a matrix of side 9 means can use 91 characters 
	from Unicode, but they must be consecutive. It can be easy extended for any range of characters given that they are 
	supported in the Unicode system install in the operating system. I din not try a bigger range because I program with 
	windows and my Unicode do not support some characters after 130.
		The extended algorithm have almost the same big-O that the normal, but if we increase the number of letters in the
	matrix de algorithm will increase linear to the number of letters. So the algorithm is O(l) + O(n) where l is the number 
	of letters in the matrix (side*side) and n the number of characters in the file.

