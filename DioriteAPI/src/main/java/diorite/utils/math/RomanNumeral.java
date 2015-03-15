package diorite.utils.math;

@SuppressWarnings("MagicNumber")
public class RomanNumeral
{
    private static final int[]    numbers = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] letters = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    private final     int    num;
    private transient String romanStr;

    public RomanNumeral(final int arabic)
    {
        if (arabic < 1)
        {
            throw new NumberFormatException("Value of RomanNumeral must be positive.");
        }
        if (arabic > 3999)
        {
            throw new NumberFormatException("Value of RomanNumeral must be 3999 or less.");
        }
        this.num = arabic;
    }

    public RomanNumeral(String roman)
    {
        if (roman.isEmpty())
        {
            throw new NumberFormatException("An empty string does not define a Roman numeral.");
        }

        roman = roman.toUpperCase();
        int i = 0;
        int arabic = 0;

        while (i < roman.length())
        {
            final char letter = roman.charAt(i);
            final int number = this.letterToNumber(letter);

            if (i == roman.length())
            {
                arabic += number;
            }
            else
            {
                final int nextNumber = this.letterToNumber(roman.charAt(i));
                if (nextNumber > number)
                {
                    arabic += (nextNumber - number);
                    i++;
                }
                else
                {
                    arabic += number;
                }
            }
        }

        if (arabic > 3999)
        {
            throw new NumberFormatException("Roman numeral must have value 3999 or less.");
        }

        this.num = arabic;

    }

    private int letterToNumber(final char letter)
    {
        switch (letter)
        {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                throw new NumberFormatException("Illegal character \"" + letter + "\" in Roman numeral");
        }
    }

    public String toString()
    {
        if (this.romanStr == null)
        {
            String roman = "";
            int N = this.num;
            for (int i = 0; i < numbers.length; i++)
            {
                while (N >= numbers[i])
                {
                    roman += letters[i];
                    N -= numbers[i];
                }
            }
            this.romanStr = roman;
        }
        return this.romanStr;
    }

    public int toInt()
    {
        return this.num;
    }
}