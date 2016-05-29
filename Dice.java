import java.util.*;

/**
 *Dice Class. The dice to be used in the game.
 * 
 * @author Benjamin Draves
 * @version March 4, 2016
 */
public class Dice
{
    private Random random; 
    private int highNumber;
    
    /**
     * Constuctor for objected of class Dice. Creates a new random object and stores the highest
     * number possible for this die.
     * @param highNum - the highest number possible for this die.
     */
    public Dice(int highNum)
    {
        random = new Random();
        highNumber = highNum;
    }
    
    /**
     * Constuctor for objected of class Dice. Creates a new random object and stores the highest
     * number possible for this die.
     * @param highNum - the highest number possible for this die.
     * @param 
     */
    public Dice(int highNum, long seed)
    {
        random = new Random(seed);
        highNumber = highNum;
    }

    /**
     * roll - a roll of the dice. Returns a random number between 1 and the highest number for 
     * the die.
     * @return int - a random number in the range 1 - highNumber
     */
    public int roll()
    {
        return random.nextInt(highNumber)+1;
    }
    
    /**
     * getHighNumber - returns the hightest possible number of this die.
     * @return int - the hightest number on this die
     */
    
    public int getHighNumber(){
        return highNumber;
    }
}
