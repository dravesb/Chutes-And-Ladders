import java.io.*;
import java.util.ArrayList;

/**
 * Statics Class. A class that holds all statics for the game. This includes the file that holds
 * the game "rules", the set of players in the playerHolder, the dice that all players share, 
 * and finally the board.
 * 
 * @author Benjamin Draves 
 * @version March 4, 2016
 */
public class Statics
{            
  
    private static Board board;
    private static PlayerHolder playerHolder;
    private static Dice dice;
    private static File file;
    
    public static void main(String []args){
        File graph = new File("new-maze-30-60.txt");
        ArrayList<File> files = new ArrayList<File>();
        files.add(graph);
        Statics statics = new Statics(3, 6, 4, "config.txt",files, 1235678234,1235678234);
        Space entrance = statics.board.getSpace(2,1,4);
        ArrayList<Integer> things= statics.board.getMazes().get(entrance.getMazeNumber()-1).getOutgoing(7);
        ArrayList<Integer> things2= statics.board.getMazes().get(entrance.getMazeNumber()-1).getOutgoingWeights(7);
        for(int i = 0; i<things.size(); i++){
            System.out.println(things.get(i));
        }
        
        for(int i = 0; i<things2.size(); i++){
            System.out.println(things2.get(i));
        }
    }
    
    /**
     * Constructor for objects of the class statics. This includes the board, the configuration
     * file, the playerHolder, and dice.
     * @param dimension - the dimension of the board 
     * @param highNumber - the highest roll of the dice
     * @param numOfPlayers - the number of players in the game
     * @param configurationFile - the configuration file for this game
     */
    public Statics(int highNumber,int numOfPlayers, int numOfTokens,  File configurationFile, ArrayList<File> graph_configuration)
    {
        file = configurationFile;
        board = new Board(graph_configuration);
        playerHolder = new PlayerHolder(numOfPlayers, numOfTokens);
        dice = new Dice(highNumber);
    }
    
    /**
     * Constructor for objects of the class statics with fixed seeds. This includes the board, the configuration
     * file, the playerHolder, and dice.
     * @param dimension - the dimension of the board 
     * @param highNumber - the highest roll of the dice
     * @param numOfPlayers - the number of players in the game
     * @param configurationFile - the configuration file for this game
     * @param seed1 - the seed of the random generator of the board
     * @param seed2 - the seed of the random generator of the dice
     */
    public Statics(int highNumber,int numOfPlayers, int numOfTokens, String configurationFile, ArrayList<File> graph_configuration, long seed1, long seed2)
    {
        file = new File(configurationFile);
        board = new Board(graph_configuration,seed1);
        playerHolder = new PlayerHolder(numOfPlayers, numOfTokens);
        dice = new Dice(highNumber, seed2);
    }

    
    /**
     * Constructor for objects of the class statics with fixed seeds. This includes the board, the configuration
     * file, the playerHolder, and dice.
     * @param dimension - the dimension of the board 
     * @param highNumber - the highest roll of the dice
     * @param numOfPlayers - the number of players in the game
     * @param configurationFile - the configuration file for this game
     * @param seed1 - the seed of the random generator of the board
     * @param seed2 - the seed of the random generator of the dice
     */
    public Statics(int highNumber,int numOfPlayers, int numOfTokens, String configurationFile, long seed1, long seed2)
    {
        file = new File(configurationFile);
        board = new Board(new ArrayList<File>(),seed1);
        playerHolder = new PlayerHolder(numOfPlayers, numOfTokens);
        dice = new Dice(highNumber, seed2);
    }
    
    /**
     * getBoard - a method to retrieve the board
     * @return board - the board
     */
    public static Board getBoard(){
        return board;
    }
    
    /**
     * getDice - a method to retrieve the dice
     * @return board - the board
     */
    public static Dice getDice(){
        return dice;
    }
    
    /**
     * getPlayerHolder - a method to retrieve the player holder
     * @return board - the board
     */
    public static PlayerHolder getPlayerHolder(){
        return playerHolder;
    }
    
    /**
     * getFile - a method to retrieve the configuration file
     * @return board - the board
     */
    public static File getFile(){
        return file;
    }
}
