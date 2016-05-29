
/**
 * Empty Class. This class contains the information of an empty space on the board
 * 
 * @author Benjamin Draves
 * @version March 5, 2016
 */
public class Empty extends Space
{
    /**
     * Constructor for objects of class Empty. Calls the parent constructor and sets type to Empty
     */
    public Empty()
    {
        super();
        type="Empty";
    }
    
    /**
     * getHere - returns this empty space
     * @return Empty - this space
     */
    public Empty getHere(){
        return this;
    }
    
    /**
     * toString - a to String summarizing the space
     * @return String - the summary of the space
     */
    public String toString(){
        return "An Empty Space";
    }
}
