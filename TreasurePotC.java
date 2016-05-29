import java.util.ArrayList;

/**
 * TreasurePotC a class that represents the exits to the graphs. Called treasure pots because they get 
 * treasure when they land here 
 * 
 * @author Benjamin Draves
 * @version May 5, 2016
 */
public class TreasurePotC extends Space
{
    private int mazeNumber, graphNumber, x, y, z;    
    /**
     * Constructor for objects of class TreasurePotC
     * @param mn - the corresponding key value of this node in the graph
     * @param gn - the graph number 
     * @param newX - the X location of this space
     * @param newY - the Y location of this space
     * @param newZ - the Z location of this space
     */
    public TreasurePotC(int mn, int gn, int newX, int newY, int newZ)
    {
        mazeNumber = mn+1;
        graphNumber = gn;
        x = newX;
        y = newY;
        z = newZ;
        type = "TreasurePotC";
    }
    
    /**
     * getLocation - gets the position of this space on the board
     * @param ArrayList<Integer> - the x,y,z coordinates of this space
     */
    public ArrayList<Integer> getLocation(){
        ArrayList<Integer> location = new ArrayList<Integer>();
        location.add(x);
        location.add(y);
        location.add(z);
        return location;
    }
    
    /**
     * getGraphNumber - a method that returns the graph number 
     * @return int - the graph number 
     */
    public int getGraphNumber(){
        return graphNumber;
    }
    
    /**
     * getMazeNumber - a method that returns the maze number 
     * @return int - the maze number 
     */ 
    public int getMazeNumber(){
        return mazeNumber;
    }
    
    /**
     * toString - the to string method for this space 
     * @return String - the space summary
     */
    public String toString(){
        return "An exit of graph "+mazeNumber;
    }
}
