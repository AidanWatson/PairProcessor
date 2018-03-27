import ecs100.*;
/**
 * Write a description of class Pair here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Pair
{
    // instance variables - replace the example below with your own
    private char init;
    private char fin;
    private int count;

    /**
     * Constructor for objects of class Pair
     */
    public Pair(char letterOne, char letterTwo )
    {
        // initialise instance variables
        init = letterOne;
        fin = letterTwo;
        count = 01;

    }
    public void raiseCount(int x){
    count+=x;
    
    }
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int getCount()
    {
        // put your code here
        return count;
    }
    

    public char getFirst(){
        return init;
    }

    public char getLast(){return fin;}

}
