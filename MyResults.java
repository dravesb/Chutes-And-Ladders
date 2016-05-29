import java.util.ArrayList;

/**
 * MyResults a class that stores the results of a game
 * 
 * @author Benjamin Draves 
 * @version May 6 
 */
public class MyResults
{  
    ArrayList<Integer> results;
    String winner;
    long time;
    /**
     * MyResults - A constructor for the results 
     * @param res - an array of results 
     * @param win - the personality of the winner
     */
    public MyResults(ArrayList<Integer> res, String win){
        results = res;
        winner = win;
    }
}
