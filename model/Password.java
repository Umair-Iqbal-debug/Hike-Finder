package model;

public interface Password 
{
	public static final int MIN_LENGTH = 6;
	public static final int MIN_DIGITS = 1;
	public static final int MIN_lOWERCASE_LETTER = 1;
	public static final int MIN_UPPERCASE_LETTER = 1;
	
	public static boolean isValidPassword(String password)
	{
		int digits = 0;
		int lowercaseLetters = 0;
		int uppercaseLetters = 0;
		
		for(char curr: password.toCharArray()) 
		{
			if(Character.isDigit(curr)) digits++;
			else if(Character.isLetter(curr))
			{
				if(Character.isUpperCase(curr)) uppercaseLetters++;
				else lowercaseLetters++;
			}
		}
		
		return checkLength(password) && digits >= MIN_DIGITS && lowercaseLetters >= MIN_lOWERCASE_LETTER && uppercaseLetters >= MIN_UPPERCASE_LETTER;
	}
	
	private static boolean checkLength(String password)
	{
		return password.length() >= MIN_LENGTH;
	}
	
	
	
	
	
	
	// could be made expandable by checking for all requirements seperately in different methods 
	// but could prove to be slower as you increase the number of requirements or checks as all of it is done on its own scan
}
