import java.util.ArrayList;
import java.util.Random;

/**
 *PlayerHolder class that stores an array of Players. 
 * 
 * @author Benjamin Draves 
 * @version March 4, 2016
 */
public class PlayerHolder
{
    private ArrayList<Player> playerList;
    private int numOfPlayers, numOfTokens;
    private ArrayList<String> personalities;
    private ArrayList<Boolean> levelsVisited;
    private Random random;
    
    /**
     * Constructor for objects of class PlayerHolder. Will be used to organize the players. Also initials the number of players
     * variable
     * 
     * @param numberOfPlayers - the number of players to be added to the array
     */
    public PlayerHolder(int numberOfPlayers, int numberOfTokens)
    {   
        random = new Random();
        personalities = new ArrayList<String>();
        personalities.add("Random");
        personalities.add("Sprinter");
        personalities.add("SmartSprinter");
        personalities.add("TreasureHunter");
        personalities.add("MazeRunner");
        personalities.add("Hybrid");
        personalities.add("AntiHold");
        personalities.add("Tortoise");
        
        playerList = new ArrayList<Player>(numberOfPlayers);
        
        for(int i = 0; i<numberOfPlayers; i++){
            playerList.add(new Player(i+1,numberOfTokens, personalities.get(random.nextInt(personalities.size()))));
        }
        
        levelsVisited = new ArrayList<Boolean>();
        for(int i = 0; i<Statics.getBoard().getDimensions().get(2); i++){
            levelsVisited.add(false);
        }
        levelsVisited.add(false);//this is for the first finisher condition
        
        numOfPlayers = numberOfPlayers;
        numOfTokens = numberOfTokens;
    }

    /**
     * playRound - a method that has each player in the array play a round. After each turn if the player has won, the
     * program will end
     * 
     */
    public void playRound()
    {
        //Play the round
        for(int i = 0; i<numOfPlayers; i++){
            if(!playerList.get(i).checkFinish()){
                playerList.get(i).playRound(levelsVisited);
            }
            else{
                System.out.println("Player "+(i+1)+" has finished.");
            }
        }
        
        //See if everyone has finished
        for(int i = 0; i<numOfPlayers; i++){
            if(!playerList.get(i).checkFinish()){
                return;
            }
        }
        System.out.println(Statics.getPlayerHolder().printPlayerSummary());
        ChutesAndLadders.isWinner = true;
        calculateWinner();
    }
    
    
    /**
     * calculateWinner- a mathod that finds the winner of the game. Gets the scoring values from the board class. 
     * 
     */
    public void calculateWinner(){
        ArrayList<Double> playerTotals = new ArrayList<Double>();
        double treasureBonus = Statics.getBoard().getReader().getScoring().get(1);
        
        for(int i = 0; i<numOfPlayers; i++){
            playerTotals.add(playerList.get(i).getTreasure()*treasureBonus + playerList.get(i).getPoints().doubleValue());
        }
        
        double max = playerTotals.get(0); 
        int winnerNumber=1;
        
        for(int i = 1; i<numOfPlayers;i++){
            if(max<playerTotals.get(i)){
                max = playerTotals.get(i);
                winnerNumber = i+1;
            }
        }
        
        ChutesAndLadders.winner = playerList.get(winnerNumber - 1);
        System.out.println("Player "+winnerNumber+" has won the game with a value of "+playerTotals.get(winnerNumber-1));
    }
    
    /**
     * getPlayer - returns a player in the array
     * 
     * @param  playerNumber - the number of the player to return
     * @return Player - the player to get
     */
    public Player getPlayer(int playerNumber)
    {
        return playerList.get(playerNumber-1);
    }
    
    /**
     * getNumOfPlayers - returns the number of players in this holder
     * 
     * @return int - number of players in this array
     */
    public int getNumOfPlayers()
    {
        return numOfPlayers;
    }
    
    /**
     * printPlayerSummary - a method that summarizes the player's current positions and treasure totals. Builds a string based on the toString method 
     * in the Player class
     */
    public String printPlayerSummary(){
        String s="";
        for(int i = 0; i<numOfPlayers;i++){
            s = s + playerList.get(i).toString() + "\n";
        }
        return s;
    }
    
    /**
     * getNumOfTokens - returns the number of tokens in this holder
     * 
     * @return int - number of tokens per player
     */
    public int getNumOfTokens()
    {
        return numOfTokens;
    }
}
