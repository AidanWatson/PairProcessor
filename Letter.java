
/**
 * Write a description of class Letter here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Letter
{
    // instance variables - replace the example below with your own
    private char Letter;
    private int count;

    /**
     * Constructor for objects of class Letter
     */
    public Letter(char L)
    {
        // initialise instance variables
        Letter =L;
        count=1;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public char returnLetter()
    {
        // put your code here
        return Letter;
    }

    public void increaseCount(){
        count+=1;
    }
    public int getCount(){
    return count;
    }
}
