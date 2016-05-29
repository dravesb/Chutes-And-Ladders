import java.util.ArrayList;
import java.io.File; 

/**
 * ExperimentController Class. A plays a game and records the results as well as the time to run the program
 * 
 * @author Benjamin Draves
 * @version March 5, 2016
 */
public class ExperimentController
{
    /**
     * initialTest - a method that runs a game of chutes and ladders. The data is stored in a MyResults object and then 
     * returned.
     * @param a - the die number
     * @param b - the number of players
     * @param c - the number of tokens
     * @param d - the configuration file
     * @param e - ? the maze configurationa
     * @return MyResults - an object that stores all the results from the game play
     */
    public MyResults initialTest(String[] args){
        long start = System.currentTimeMillis();
        ChutesAndLadders play = new ChutesAndLadders(args);
        MyResults results  = play.testPlay();

        long end = System.currentTimeMillis();
        long runTime = (end - start);
        results.time = runTime;
        
        return results;
    }
    
}


    