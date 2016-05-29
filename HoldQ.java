import java.util.LinkedList;
import java.util.Random;

/**
 *HoldQ Class. Creates a space of type hold A. A linked list with queue operations
 * 
 * @author Benjamin Draves 
 * @version March 5, 2016
 */
public class HoldQ extends Space
{
    private int min, max, mult;
    private LinkedList<Token> tokenList;
    private LinkedList<Integer> numberList;
    private LinkedList<Integer> diceRollList;

    /**
     * Constructor for objects of class HoldQ. Calls the parent constructor. From the reader object in the parent class, the minimum and maximum 
     * value are called and a random number between these values are assigned to the multiplier. The type is set to HoldQ and the Linked List with 
     * queue operations are instantiated.
     */
    public HoldQ()
    {
        super();
        min = reader.getHoldQArray().get(1);
        max = reader.getHoldQArray().get(2);
        
        Random random = new Random();
        mult = 0;
        while(mult == 0){
            mult = random.nextInt(Math.abs(max-min))+min;
        }
        type = "HoldQ";
        tokenList = new LinkedList<Token>();
        numberList = new LinkedList<Integer>();
        diceRollList = new LinkedList<Integer>();
        
    }
    
    /**
     * getHere - a method that returns this holdQ
     * @return HoldQ - this space
     */
    public HoldQ getHere(){
        return this;
    }
    
    /**
     * addToHoldQ - adds a token to the linked list. Follows queue operations. 
     * @param toke - the token to be added 
     * @param roll - the roll the token entered the queue with
     * @param playerNum - the player to enter the queue
     */
    public void addToHoldQ(Token toke, int roll, int playNum){
        tokenList.add(toke);
        numberList.add(playNum);
        diceRollList.add(roll);
    }
    
    /**
     *removeToken - removes a token from the queue 
     *@param num - the player number of the token being added
     */
    public void removeToken(int num){
        int index = numberList.indexOf(num);
        numberList.remove(index);
        tokenList.remove(index);
        diceRollList.remove(index);
    }
    
    /**
     * isFirst - a method to check if the player is the first in the queue 
     * @param num - the player number 
     * @return boolean - true if the player is first in the queue
     */
    public boolean isFirst(int num){
        return numberList.getFirst()== num;
    }
    
    /**
     * tryToLeave- a method to see if the player can leave the queue. The player rolls. If their roll is the same as they came in with, the player
     * can leave
     * @param num - the player number 
     * @return boolean - true if the player rolls the same value they entered the queue with
     */
    public boolean tryToLeave(int playerNum, int roll){
        int index = numberList.indexOf(playerNum);
        return numberList.getFirst()== playerNum && roll == diceRollList.get(index);
    }
     
    /**
     * getMult - a method to return the multiplier of this queue
     * @return int - the multiplier
     */
    public int getMult(){
        return mult;
    }
    
    /**
     *getNumberList - a method that returns the linked list with the player Numbers
     *@return LinkedList<Integer> - the player number list
     */
    public LinkedList<Integer> getNumberList(){
        return numberList;
    }
    
    /**
     *getDiceRollList - a method that returns the linked list with the last dice roll by the players in the queue
     *@return LinkedList<Integer> - the dice roll list
     */
    public LinkedList<Integer> getDiceRollList(){
        return diceRollList;
    }
    
    /**
     *getTokenList - a method that returns the linked list with the containing the tokens
     *@return LinkedList<Integer> - the list of tokens in the queue
     */
    public LinkedList<Token> getTokenList(){
        return tokenList;
    }
    
    /**
     * toString - a to string method for this queue
     * @return String - the summary of this queue
     */
    public String toString(){
        return "A HoldQ with " + numberList.size() +" tokens and a multiplier range of ("+min+","+max+")";
    }
}
