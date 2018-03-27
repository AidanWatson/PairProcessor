
/**
 * Write a description of class Word here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Word
{
    // instance variables - replace the example below with your own
    private int count;
    private String wordName;

    /**
     * Constructor for objects of class Word
     */
    public Word(String S)
    {
        // initialise instance variables
        count = 1;
        wordName=S;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public void raiseCount(int y)
    {
        // put your code here
        count = count + y;
    }

    public int returnCount(){
        return count;
    }

    public String returnWord(){
        return wordName;
    }
}
