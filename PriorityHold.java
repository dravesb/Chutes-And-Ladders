import java.util.Comparator;
import java.util.TreeMap; 
import java.util.Random;
import java.util.LinkedList;

/**
 *PriorityHold Class. A class that stores a TreeMap. As players land on the space, they are added to the tree with their priority being assigned 
 *by their roll that landed them on this space. Two nested classes build a TokenInPH object to be added to the TreeMpa as well as a comparator 
 *used in the construction of the the TreeMap.
 * 
 * @author Benjamin Draves
 * @version March 5, 2016 
 */
public class PriorityHold extends Space 
{
    private int min, max, mult, size;
    private TreeMap<Integer,LinkedList<TokenInPH>> list;
    
    /**
     * Constructor for objects of class PriorityHold. Calls the parent constructor and sets the multipler value based on the values retrieved via the 
     * reader object in the Spaces class. The type is set to PriorityHold. Finally, the treemap is instantiated with a comparator object.
     */
    public PriorityHold()
    {
        super();
        min = reader.getPriorityHoldArray().get(1);
        max = reader.getPriorityHoldArray().get(2);
        size = 0;
        
        Random random = new Random();
        mult = 0;
        while(mult == 0){
            mult = random.nextInt(Math.abs(max-min))+min;
        }
        
        type="PriorityHold";
        list = new TreeMap<Integer, LinkedList<TokenInPH>>(new PriorityComp());
    }
    
    /**
     * getHere - a method that returns this priorityHold
     */
    public PriorityHold getHere(){
        return this;
    }
    
    /**
     * addToQueue - a method that adds the token to the tree. Creates a TokenInPH object then adds this object to the tree. 
     * @param toke - the token to be added
     * @param a - the last roll of the player 
     * @param b - the player number
     */
    public void addToQueue(Token toke, int a, int b){
        //If this key already exists, just add it to the list
        if(list.containsKey(a)){
            list.get(a).add(new TokenInPH(toke, a, b));
        }
        //If it doesn't add the key and instantiate the list. Then add the new value
        else{
            list.put(a, new LinkedList<TokenInPH>());
            list.get(a).add(new TokenInPH(toke, a, b));
        }
        size++;
    }
    
    /**
     * removeToken - calls poll on our tree
     * @param a - a value that is not used (overwritten method from Spaces)
     */
    public void removeToken(int a){
        int key = list.firstKey();
        list.get(key).remove();
        
        if(list.get(key).size()==0){
           list.remove(key);
        }     
        size--;
    }
    
    /**
     * tryToLeave - a method that checks if they player can leave the tree. We need only check the first person in tree 
     * @param a - a value that is not used (overwritten method from Spaces)
     * @return boolean - true if the player is allowed to leave the queue 
     */
    public boolean tryToLeave(int playerNum, int roll){
        return playerNum==list.get(list.firstKey()).get(0).getPlayerNumber() && roll == list.get(list.firstKey()).get(0).getRoll();   
    }
    
    /**
     * isFirst - a method that checks if this player is the first in the queue 
     * @param num - the player number 
     * @return boolean - true if the player is the first player in the queue
     */
    public boolean isFirst(int num){
        return num==list.get(list.firstKey()).get(0).getPlayerNumber();
    }
    
    /**
     * getMult- returns the multiplier of this queue
     * @return int - the multiplier
     */
    public int getMult(){
        return mult;
    }
    
    /**
     *toString - a toString method that summarizes the priorityHold object 
     *@return String - the priorityHold summary
     */
    public String toString(){
        return "A PriorityHold with "+size+" tokens and a multiplier range of ("+min+","+max+")";
    }
    
    /**
     *getList - a method that returns the priority queue 
     *@return PriorityQueue<TokenInPH> - the prioruty queue 
     */
    public TreeMap<Integer, LinkedList<TokenInPH>> getList(){
        return list;
    }
    
    /**
     * isEmpty - a method that checks if the tree is empty or not
     * @return boolean - true if the tree is empty
     */
    public boolean isEmpty(){
        return list.isEmpty();
    }
    
    /**
     * getFirstToken- A method that gets the first entry in the tree
     * @return TokenInPH
     */
    
    public TokenInPH getFirstToken(){
        if(!isEmpty()){
            return list.get(list.firstKey()).get(0);
        }
        else{
            return null;
        }
    }
    
    
    /**
     * TokenInPH class. Stores the die roll, token, and player number in a single object to be added to teh priority queue
     */
    public class TokenInPH{
        Token token;
        Integer roll; 
        Integer playerNumber;
        /**
         * A constuctor for objects of type TokenInPH. Instantiates the token, roll, and playerNumber fields
         * @param toke - the token to be added
         * @param a - the roll value
         * @param b - the player Number
         */
        public TokenInPH (Token toke, Integer a, Integer b){
            token = toke;
            roll = a;
            playerNumber = b;
        }
        
        /**
         * getRoll - returns the roll value
         * @return Integer - the roll value
         */
        public Integer getRoll(){
            return roll;
        }
        
        /**
         * getPlayerNumber - returns the playerNumber field 
         * @return Integer - the player number
         */
        public Integer getPlayerNumber(){
            return playerNumber;
        }
    }
    
    /**
     * PriorityComp class. A comparator class that implements a comparator object. Overrides compare and equals. 
     */
    public class PriorityComp implements Comparator<Integer>{
    
        /**
         * compare - a method that compares two integers. Simply reverses the natural ordering
         * @param a - first Integer
         * @param b - second Integer
         * @return int - the difference of the roll values
         */
        public int compare(Integer a, Integer b){
            return b-a;
        }
        
        /**
         * equals - method to check if the rolls are equals
         * @param a - first roll
         * @param b - second roll
         * @return boolean - if the roll values are equal
         */
        public boolean equals(Integer a, Integer b){
            return a == b ;
        }
    }
    
}   
