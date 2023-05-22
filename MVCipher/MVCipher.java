import java.util.Scanner;
import java.io.PrintWriter;

/**
 * MVCipher - This program utilizes the Caeser Cipher logic to encrypt and decrypt
 * text files. The MV Cipher uses a series of Caesar ciphers based on the letters of a keyword.
 * 
 * Requires Prompt and FileUtils classes.
 *	
 * @author Aryan Singhal
 * @since September 22, 2022
 */

public class MVCipher
{
	private Scanner encryptScan; // Used when encrypting files
	private Scanner decryptScan; // Used when decrypting files
	private PrintWriter eOutput; // For writing encrypted text to separate file
	private PrintWriter dOutput; // For writing decrypted text to separate file
	// User's input as key for encrypting/decrypting. Stored as uppercase.
	private String keyInput;
	
	/** Constructor initializes field variables */
	public MVCipher()
	{
		keyInput = "";
	}
	
	/** Main method creates new MVCipher object and call run method. */
	public static void main(String[] args)
	{
		MVCipher mvc = new MVCipher();
		mvc.run();
	}
	
	/** Prompt user with greeting and call method to get the user's input (key). */
	public void run()
	{
		System.out.println("\n Welcome to the MV Cipher machine!\n");		
		getInput();
	}
	
	/** Asks user for the key. Call isValidInput() to check if the key is valid. */
	public void getInput()
	{
		keyInput = Prompt.getString("Please input a word to use as a key (letters " + 
									"only)");
		if(!isValidInput())
		{
			keyInput = "";
			System.out.println("ERROR: Key must be all letters and at least " + 
							"3 characters long");
			getInput();
		}
		else
		{
			keyInput = keyInput.toUpperCase();
			askCrypt();
		}
	}
	
	/**
	 * First check the key length.
	 * If the key length is less than 3, return false.
	 * If key passes length test, check if the key is only letters.
	 * Check each character in the key and call isAlphaChar() passing in
	 * the character to determine if it is a letter.
	 * Return false if the length of the key is too short or a character in
	 * the key is not in the alphabet.
	 * Return true otherwise
	 * 
	 * @return if key user has input is valid
	 */
	public boolean isValidInput()
	{
		boolean isLengthValid = true;
		boolean isAlpha = true;
		
		if(keyInput.length() < 3)
			isLengthValid = false;
		
		for(int i = 0; i < keyInput.length(); i++)
		{
			char keyChar = ' ';
			keyChar = keyInput.charAt(i);
			isAlpha = isAlpha && isAlphaChar(keyChar);
		}
		
		if(!isLengthValid || !isAlpha)
			return false;
			
		return true;
	}
	
	/**
	 * Asks user if they want to encrypt or decrypt using their key.
	 * 1 for encrypting and 2 for decrypting. Anything else user inputs
	 * prompts the user again.
	 * Ask user what file they want to encrypt or decrypt accordingly.
	 * Call methods to run encrypt/decrypt logic and tell user that their
	 * encrypted/decryted file has been created using their given file.
	 */
	public void askCrypt()
	{
		int crypt = 0;
		String inFile = "";
		String outFile = "";
		
		crypt = Prompt.getInt("\nEncrypt or decrypt? (1, 2)");
		if(crypt == 1)
		{
			inFile = Prompt.getString("\nName of file to encrypt");
			encryptScan = FileUtils.openToRead(inFile);
			outFile = Prompt.getString("Name of output file");
			eOutput = FileUtils.openToWrite(outFile);
			encryptFile();
			System.out.printf("\nThe encrypted file %s has been created " + 
				"using the keyword -> %s ", outFile, keyInput);			
		}
		else if(crypt == 2)
		{
			inFile = Prompt.getString("\nName of file to decrypt");
			decryptScan = FileUtils.openToRead(inFile);
			outFile = Prompt.getString("Name of output file");
			dOutput = FileUtils.openToWrite(outFile);
			decryptFile();
			System.out.printf("\nThe decrypted file %s has been created " + 
				"using the keyword -> %s ", outFile, keyInput);
		}
		else
			askCrypt();
	}
	
	/**
	 * Encrypts a file line by line and writes it to the output file.
	 * Seperate encryption for upper and lowecase letters.
	 * If the character is not a letter, leave it as is.
	 */
	public void encryptFile()
	{
		String plainLine = "";
		String encryptedLine = "";
		char lineChar = ' ';
		char encryptBy = ' ';
		boolean isAlpha = false;
		boolean isUpper = false;
		int lineCounter = 0; // Keeps count of the lines being encrypted
		int keyCounter = 0; // Keeps count of iteration of the key
		
		while(encryptScan.hasNextLine())
		{
			plainLine = encryptScan.nextLine();
			encryptedLine = "";
			
			for(int i = 0; i < plainLine.length(); i++)
			{
				lineChar = plainLine.charAt(i);
				isAlpha = isAlphaChar(lineChar);
				if(!isAlpha)
					encryptedLine += lineChar;
				else
				{
					keyCounter = lineCounter % keyInput.length();
					lineCounter++;
					encryptBy = keyInput.charAt(keyCounter);
					isUpper = isUpperLower(lineChar);
					if(isUpper)
						encryptedLine += encryptUpper(lineChar, encryptBy);
					else
						encryptedLine += encryptLower(lineChar, encryptBy);
				}
			}
			eOutput.println(encryptedLine);
		}
		encryptScan.close();
		eOutput.close();
	}
	
	/**
	 * Encrypts uppercase letters. Uses a wrapping logic if the ascii of the
	 * encrypted character is more than 'Z'.
	 * 
	 * @param toEncrypt 	the character to be encrypted
	 * @param encryptBy 	the character that is being used to encrypt 
	 * 						(shift) the toEncrypt character
	 * @return 				encrypted character
	 */
	public char encryptUpper(char toEncrypt, char encryptBy)
	{
		int encryptedAscii = toEncrypt + (encryptBy - 'A' + 1);
		
		if(encryptedAscii > 'Z')
		{
			int diff = encryptedAscii - 'Z';
			return (char)('A' + diff - 1);
		}
		
		return (char)(encryptedAscii);
	}
	
	/**
	 * Encrypts lowercase letters. Uses a wrapping logic if the ascii of the
	 * encrypted character is more than 'z'.
	 * 
	 * @param toEncrypt 	the character to be encrypted
	 * @param encryptBy 	the character that is being used to encrypt 
	 * 						(shift) the toEncrypt character
	 * @return 				encrypted character
	 */
	public char encryptLower(char toEncrypt, char encryptBy)
	{
		int encryptedAscii = toEncrypt + (encryptBy - 'A' + 1);
		
		if(encryptedAscii > 'z')
		{
			int diff = encryptedAscii - 'z';
			return (char)('a' + diff - 1);
		}
		
		return (char)(encryptedAscii);
	}
	
	/**
	 * Decrypts a file line by line and writes it to the output file.
	 * Seperate decryption for upper and lowecase letters.
	 * If the character is not a letter, leave it as is.
	 */
	public void decryptFile()
	{
		String encryptedLine = "";
		String decryptedLine = "";
		char lineChar = ' ';
		char decryptBy = ' ';
		boolean isAlpha = false;
		boolean isUpper = false;
		int lineCounter = 0; // Keeps count of the lines being encrypted
		int keyCounter = 0; // Keeps count of iteration of the key
		
		while(decryptScan.hasNextLine())
		{
			encryptedLine = decryptScan.nextLine();
			decryptedLine = "";
			
			for(int i = 0; i < encryptedLine.length(); i++)
			{
				lineChar = encryptedLine.charAt(i);
				isAlpha = isAlphaChar(lineChar);
				if(!isAlpha)
					decryptedLine += lineChar;
				else
				{
					keyCounter = lineCounter % keyInput.length();
					lineCounter++;
					decryptBy = keyInput.charAt(keyCounter);
					isUpper = isUpperLower(lineChar);
					if(isUpper)
						decryptedLine += decryptUpper(lineChar, decryptBy);
					else
						decryptedLine += decryptLower(lineChar, decryptBy);
				}
			}
			dOutput.println(decryptedLine);
		}
		decryptScan.close();
		dOutput.close();
	}
	
	/**
	 * Decrypts uppercase letters. Uses a wrapping logic if the ascii of the
	 * decrypted character is less than 'A'
	 * 
	 * @param toEncrypt 	the character to be decrypted
	 * @param encryptBy 	the character that is being used to decrypt 
	 * 						(shift) the toDecrypt character
	 * @return 				decrypted character
	 */
	public char decryptUpper(char toDecrypt, char decryptBy)
	{
		int decryptedAscii = toDecrypt - (decryptBy - 'A' + 1);
		
		if(decryptedAscii < 'A')
		{
			int diff = 'A' - decryptedAscii;
			return (char)('Z' - diff + 1);
		}
		
		return (char)(decryptedAscii);
	}
	
	/**
	 * Decrypts lowercase letters. Uses a wrapping logic if the ascii of the
	 * decrypted character is less than 'A'
	 * 
	 * @param toEncrypt 	the character to be decrypted
	 * @param encryptBy 	the character that is being used to decrypt 
	 * 						(shift) the toDecrypt character
	 * @return 				decrypted character
	 */
	public char decryptLower(char toDecrypt, char decryptBy)
	{
		int decryptedAscii = toDecrypt - (decryptBy - 'A' + 1);

		if(decryptedAscii < 'a')
		{
			int diff = 'a' - decryptedAscii;
			return (char)('z' - diff + 1);
		}
		
		return (char)(decryptedAscii);
	}
	
	/**
	 * Check if the character passed in is a letter.
	 * 
	 * @param c 	character being checked if it is a letter in the alpabet
	 * @return 		if the character is a letter - boolean
	 */
	public boolean isAlphaChar(char c)
	{
		boolean isAlpha = false;
		
		if((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
			isAlpha = true;
			
		return isAlpha;
	}
	
	/**
	 * Check if the character passed in is lowercase or uppercase.
	 * 
	 * @param c 	character being checked if it is lowercase or uppercase
	 * @return 		if the character is lowercase or uppercase - boolean
	 */
	public boolean isUpperLower(char c)
	{
		boolean isUpper = false;
		
		if(c >= 'A' && c <= 'Z')
			isUpper = true;
		else if(c >= 'a' && c <= 'z')
			isUpper = false;
		else
			System.out.println("ERROR: Unexpected input character");
			
		return isUpper;
	}
}
