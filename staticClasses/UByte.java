package staticClasses;

public class UByte
{

	public static int u1Byte(byte b)
	{
		int unsignedByte= b & 0xFF;
		return unsignedByte;
	}
	
	public static int u2Byte(byte firstDigitByte, byte secondDigitByte)
	{
		int firstDigit = u1Byte(firstDigitByte);
		int secondDigit = u1Byte(secondDigitByte);
		
		secondDigit=secondDigit<<8;
		
		int finalNumber = firstDigit+secondDigit;
		
		return finalNumber;
	}
	
	public static int u3Byte(byte firstDigitByte, byte secondDigitByte, byte thirdDigitByte)
	{
		int firstDigit = u1Byte(firstDigitByte);
		int secondDigit = u1Byte(secondDigitByte);
		int thirdDigit = u1Byte(thirdDigitByte);
		secondDigit <<= 8;
		thirdDigit  <<= 16;
		
		int finalNumber = firstDigit+secondDigit+thirdDigit;
		
		return finalNumber;
	}
	
	public static byte intToByte2(int value)
	{
     return  (byte) (value>>8);
	}
	
	public static byte intToByte3(int value)
	{
     return  (byte) (value>>16);
	}
	
	
	
}
