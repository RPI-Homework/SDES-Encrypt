package SDES;

public class SDES
{
	private String KeyIntial(String Key)
	{
		return "" + 
				Key.charAt(3-1) + 
				Key.charAt(5-1) + 
				Key.charAt(2-1) + 
				Key.charAt(7-1) + 
				Key.charAt(4-1) + 
				Key.charAt(10-1) + 
				Key.charAt(1-1) + 
				Key.charAt(9-1) + 
				Key.charAt(8-1) + 
				Key.charAt(6-1);
	}
	private String KeyShift(String Key)
	{
		return "" + 
				Key.charAt(2-1) + 
				Key.charAt(3-1) + 
				Key.charAt(4-1) + 
				Key.charAt(5-1) + 
				Key.charAt(1-1) + 
				Key.charAt(7-1) + 
				Key.charAt(8-1) + 
				Key.charAt(9-1) + 
				Key.charAt(10-1) + 
				Key.charAt(6-1);
	}
	private String KeyFinal(String Key)
	{
		return "" + 
				Key.charAt(6-1) + 
				Key.charAt(3-1) + 
				Key.charAt(7-1) + 
				Key.charAt(4-1) + 
				Key.charAt(8-1) + 
				Key.charAt(5-1) + 
				Key.charAt(10-1) + 
				Key.charAt(9-1);
	}
	private String TextIntial(String Text)
	{
		return "" + 
				Text.charAt(2-1) + 
				Text.charAt(6-1) + 
				Text.charAt(3-1) + 
				Text.charAt(1-1) + 
				Text.charAt(4-1) + 
				Text.charAt(8-1) + 
				Text.charAt(5-1) + 
				Text.charAt(7-1);
	}
	private char XOR(char Text, char Key)
	{
		if((Text == '0' && Key == '0') || (Text == '1' && Key == '1'))
		{
			return '0';
		}
		else
		{
			return '1';
		}
	}
	private String XOR(String Text, String Key)
	{
		return "" + 
				XOR(Text.charAt(1-1), Key.charAt(1-1)) + 
				XOR(Text.charAt(2-1), Key.charAt(2-1)) + 
				XOR(Text.charAt(3-1), Key.charAt(3-1)) + 
				XOR(Text.charAt(4-1), Key.charAt(4-1)) + 
				XOR(Text.charAt(5-1), Key.charAt(5-1)) + 
				XOR(Text.charAt(6-1), Key.charAt(6-1)) + 
				XOR(Text.charAt(7-1), Key.charAt(7-1)) + 
				XOR(Text.charAt(8-1), Key.charAt(8-1));
	}
	private String XOR2(String Text, String Key)
	{
		return "" + 
				XOR(Text.charAt(1-1), Key.charAt(1-1)) + 
				XOR(Text.charAt(2-1), Key.charAt(2-1)) + 
				XOR(Text.charAt(3-1), Key.charAt(3-1)) + 
				XOR(Text.charAt(4-1), Key.charAt(4-1));
	}
	private String TextFinal(String Text)
	{
		return "" + 
				Text.charAt(4-1) + 
				Text.charAt(1-1) + 
				Text.charAt(3-1) + 
				Text.charAt(5-1) + 
				Text.charAt(7-1) + 
				Text.charAt(2-1) + 
				Text.charAt(8-1) + 
				Text.charAt(6-1);
	}
	private String Expansion(String Text)
	{
		return "" +
			Text.charAt(8-1) +
			Text.charAt(5-1) +
			Text.charAt(6-1) +
			Text.charAt(7-1) +
			Text.charAt(6-1) +
			Text.charAt(7-1) +
			Text.charAt(8-1) +
			Text.charAt(5-1);
	}
	private int GetInt(char c1, char c2)
	{
		if(c1 == '1')
		{
			if(c2 == '1')
			{
				return 3;
			}
			else
			{
				return 2;
			}
		}
		else
		{
			if(c2 == '1')
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
	}
	private String S0(String Text)
	{
		String[][] SBox = {{"01", "00", "11", "10"}, {"11", "10", "01", "00"}, {"00", "10", "01", "11"}, {"11", "01", "11", "10"}};
		return SBox[GetInt(Text.charAt(2-1), Text.charAt(3-1))][GetInt(Text.charAt(1-1), Text.charAt(4-1))];
	}
	private String S1(String Text)
	{
		String[][] SBox = {{"00", "01", "10", "11"}, {"10", "00", "01", "11"}, {"11", "00", "01", "00"}, {"10", "01", "00", "11"}};
		return SBox[GetInt(Text.charAt(6-1), Text.charAt(7-1))][GetInt(Text.charAt(5-1), Text.charAt(8-1))];
	}
	private String FFinal(String Text)
	{
		return "" + 
				Text.charAt(2-1) + 
				Text.charAt(4-1) + 
				Text.charAt(3-1) + 
				Text.charAt(1-1);
	}
	private String FBox(String Text, String Key)
	{
		String SubText = XOR(Expansion(Text), Key);
		return FFinal(S0(SubText) + S1(SubText));
	}
	public String Encrypt(String Key, String Text)
	{
		String K1 = KeyFinal(KeyShift(KeyIntial(Key)));
		String K2 = KeyFinal(KeyShift(KeyShift(KeyShift(KeyIntial(Key)))));
		Text = TextIntial(Text);
		String Output = FBox(Text, K1);
		Output = XOR2(Output, Text);
		Text = Text.substring(4) + Output;
		Output = FBox(Text, K2);
		Output = XOR2(Output, Text);
		Text = Output + Text.substring(4);
		return TextFinal(Text);
	}
	public String Decrypt(String Key, String Text)
	{
		String K1 = KeyFinal(KeyShift(KeyIntial(Key)));
		String K2 = KeyFinal(KeyShift(KeyShift(KeyShift(KeyIntial(Key)))));
		Text = TextIntial(Text);
		String Output = FBox(Text, K2);
		Output = XOR2(Output, Text);
		Text = Text.substring(4) + Output;
		Output = FBox(Text, K1);
		Output = XOR2(Output, Text);
		Text = Output + Text.substring(4);
		return TextFinal(Text);
	}
	public static void main(String[] args)
	{
		SDES S = new SDES();
		String s = S.Encrypt("1010000010", "10001001");
		s = S.Decrypt("1010000010", s);
	}
}