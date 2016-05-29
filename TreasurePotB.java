import java.lang.Math;

/**
 * TreasurePotB class. The treasure pot space that has an initial total that will be decreased by a 
 * value of the roll.
 * 
 * @author Benjamin Draves 
 * @version March 5, 2016
 */
public class TreasurePotB extends Space
{
    private int total;
    /**
     * Constructor for objects of class TreasurePotB. Calls the parent constructor and via the protected
     * reader object, sets the initial total. Finally, it sets type to TreasurePotB
     */
    public TreasurePotB()
    {
        super();
        total = reader.getTreasurePotBArray().get(1);
        type="TreasurePotB";
    }
    
    /**
     * getHere - a method that returns this treasure pot
     * @return TreasurePotB - this space
     */
    public TreasurePotB getHere(){
        return this;
    }
    
    /**
     * getTreasure - a method that returns the treasure to be recieved from the treasure pot. 
     * returns either the roll that landed the player on the space, or the remainer of the total
     * @param a - the roll that landed the player on that spot
     * @return int - the treasure to be attained
     */
    public int getTreasure(int a){
        int roll = Math.abs(a);
        int treasure = 0;
        if(total - roll < 0){//If the roll is greater than the total remaing, return the remaining
            treasure = total;
            total=0;//set the total to zero 
        }
        else{
            treasure = roll;//if the roll is less than the total, return the roll and decrease the total
            total = total - roll;
        }
        return treasure;
    }
    
    public int getTreasureTotal(int a){
        int roll = Math.abs(a);
        int treasure = 0;
        if(total - roll < 0){//If the roll is greater than the total remaing, return the remaining
            treasure = total;
        }
        else{
            treasure = roll;//if the roll is less than the total, return the roll and decrease the total
        }
        return treasure;
    }
    
    
    
    /**
     *getRemaining - a method that checks the total treasure left in this treasure pot
     *@return int - the treasure remaining
     */
    public int getRemaining(){
        return total;
    }
    
    /**
     *toString - a toString method summarizing this treasure pot
     *@return String - a summary of this treasure pot 
     */
    public String toString(){
        return "A TreasurePot(B) with "+total+" treasures left";
    }
}
