
/**
 * TreasurePotA Class. A class that stores a treasure pot with the a set output value and a total number of removals. 
 * 
 * @author Benjamin Draves 
 * @version March 5, 2016
 */
public class TreasurePotA extends Space
{
    private int removals, numToRemove;
    /**
     * Constructor for objects of class TreasurePotA. Calls the parent constructor and via the reader object sets the number of removals and the 
     * set output value. Sets the type to TreasurePotA. 
     */
    public TreasurePotA()
    {
       super();
       numToRemove = reader.getTreasurePotAArray().get(1);
       removals = reader.getTreasurePotAArray().get(2);
       type="TreasurePotA";
    }
    
    /**
     * getHere - a method that returns this treasure pot 
     * @return TreasurePotA - this treasure pot
     */
    public TreasurePotA getHere(){
        return this;
    }
    
    /**
     * getTreasure - a method that returns the amount of treasure from this treasure pot. 
     * @param doNothing - an integer that does nothing (needs to be overwritten from Spaces)
     * @return int - the number of treasure to recieve 
     */
    public int getTreasure(int doNothing){
        if(removals>0){
            removals--;
            return numToRemove;
        }
        else{
            return 0;
        }
    }
    
    public int getTreasureTotal(int doNothing){
        if(removals>0){
            return numToRemove;
        }
        else{
            return 0;
        }
    }
   
    /**
     *getRemovals - a method that returns the number of removals left in this treasure pot 
     *@return int - the number of treasures attained
    */
    public int getRemovals(){
        return removals;
    }
    
    /**
     * toString - a toString method summarazing this treasure pot
     * @return String - the treasure pot's summary
     */
    public String toString(){
        return "A TreasurePot(A) with "+removals+" removals left at "+numToRemove+ " removals per landing";
    }
}
