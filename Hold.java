import java.util.LinkedList;
import java.util.Random;
import java.lang.Math;
/**
 * Hold Class. Contains the space of type hold. This space holds players until they have 
 * achieved a roll they entered this space with.
 * 
 * @author Benjamin 
 * @version March 5, 2016
 */
public class Hold extends Space
{
    private int min, max, mult;
    private LinkedList<Token> hold;
    private LinkedList<Integer> dieRoll;
    private LinkedList<Integer> playerNumber;
    
    /**
     * Constructor for objects of class Hold. Calls the parent constructor. Using the protected 
     * reader object, a multipler is found. The linked lists holding the tokens, dieRolls, and 
     * playerNumbers are instaniated
     */
    public Hold()
    {
        super();
        max = reader.getHoldArray().get(1);
        min = reader.getHoldArray().get(2);
        
        Random random = new Random();
        mult = 0;
        while(mult == 0){
            mult = random.nextInt(Math.abs(max-min))+min;
        }
        
        type="Hold";
        hold = new LinkedList<Token>();
        dieRoll = new LinkedList<Integer>();
        playerNumber = new LinkedList<Integer>();
        random = new Random();
    }
    
    /**
     * getHere - a method that returns this Hold space
     * @return Hold - this space
     */
    public Hold getHere(){
        return this;
    }
    
    /**
     * addToHold - a method that adds a token to the hold
     * @param toke - the token to be added
     * @param roll - the last roll of the player that landed them on the hold space
     * @param num - the player number
     */
    public void addToHold(Token toke, int roll, int num){
       hold.add(toke);
       dieRoll.add(roll);
       playerNumber.add(num);
    }
    
    /**
     * removeToken - a method to remove the token from this hold
     * @param num - the player number of the token to remove from the Hold
     */
    public void removeToken(int num){
        int index = playerNumber.indexOf(num);
        hold.remove(index);
        dieRoll.remove(index);
        playerNumber.remove(index);
    }
    
    /**
     * tryToLeave - a method that returns if the player can leave the Hold. A player can 
     * leave the queue if they rolled the same value they entered the queue with
     * @param num - the player number 
     * @return boolean - true if the player can leave the Hold
     */
    public boolean tryToLeave(int playerNum, int roll){
        int index = playerNumber.indexOf(playerNum);
        return roll == dieRoll.get(index);
    }
    
    /**
     * getMult - a method that returns this hold's multiplier
     */
    public int getMult(){
        return mult;
    }
    
    /**
     * getHold - a method that returns the array list of the tokens 
     * @return LinkedList<Token> - the token array
     */
    public LinkedList<Token> getHold(){
        return hold;
    }

    /**
     * getDieRoll - a method that returns the array list of the dieRolls 
     * @return LinkedList<Token> - the dieRoll array
     */
    public LinkedList<Integer> getDieRoll(){
        return dieRoll;
    }
    
    /**
     * getPlayerNumber - a method that returns the array list of the playerNumbers 
     * @return LinkedList<Token> - the player Number array
     */
    public LinkedList<Integer> getPlayerNumber(){
        return playerNumber;
    }
    
    /**
     * toString - a method that returns a string summarizing the Hold object 
     * @return String - the hold summary
     */
    public String toString(){
        return "A Hold Space with "+playerNumber.size()+" tokens and a mutliplier range of("+min+","+max+")";
    }
    
}
