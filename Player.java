import java.util.ArrayList;
import java.util.Random;

/**
 * Player Class 
 * 
 * @author Benjamin Draves
 * @version March 6
 */
public class Player
{
    private TokenHolder tokens;
    private Token token;
    private Integer playerNumber, treasureTotal, lastRoll, points;
    private String personality;
    private boolean finish;
    private ArrayList<Integer> stackLevels;
    
    /**
     * Constructor for objects of class Player. Stores the player's tokens, their treasure total, their last roll,
     * their current space, and two booleans - firstTime and finish. firstTime is true if it is the player's
     * first time being added to a hold.
     * 
     * @param i - the player number to be added
     */
    public Player(int i, int numOfTokens, String s)
    {
        tokens = new TokenHolder(numOfTokens, i);
        token = tokens.get(0);
        playerNumber=i;
        treasureTotal = 0;
        lastRoll = 0;
        points = 0;
        finish = false;
        personality = s;
        stackLevels = new ArrayList<Integer>();
    }
    
    /**
     * playRound - a method that has a player play a round. Makes initial moves then secondary moves.
     */
    public void playRound(ArrayList<Boolean> levels)
    {  
        int forward = Statics.getDice().roll();
        token = tokens.decideToken(personality,forward, playerNumber);
        if(token == null){
            return;
        }
        
        initialMoves(forward, levels);
        secondaryMoves(levels);
    }
    
    /**
     * initialMoves - a function that makes a player's initial moves in a round. If the player is in a hold, 
     * they try to leave. If they do so, they remove their token from the hold, roll the dice and move based on 
     * the hold's multiplier. If the player is not in a hold, they simply move forward via the moveForward 
     * method. 
     * @param foward - the die roll 
     * @param levels - a list of booleans for scoring purposes
     */
    private void initialMoves(int forward, ArrayList<Boolean> levels) {
        //If the token is not in a hold, simply roll and move 
        if(token.getCurrentSpace().equals("Empty") || token.getCurrentSpace().equals("TreasurePotA") || token.getCurrentSpace().equals("TreasurePotB") || token.getCurrentSpace().equals("JStack")|| token.getCurrentSpace().equals("TreasurePotC")){
            moveForward(forward, levels);
            return;
        }

        if(token.getCurrentSpace().equals("Hold")){
            if(token.getSpace().tryToLeave(playerNumber, forward)){
                //If they player can leave the Hold update all fields
               System.out.println("Player "+playerNumber+", token "+ token.getTokenNumber()+" has left the hold.");
               token.getSpace().removeToken(playerNumber);
               token.setFirstTime(true);
                
                //Get the forward value by rolling the dice again
               int mult = token.getSpace().getMult();
               int newRoll = Statics.getDice().roll();
               int spaces = newRoll * mult;
                
                //Move forward and return
               moveForward(spaces, levels);
               lastRoll = newRoll;
               return;
            }
            return;
        }

        if(token.getCurrentSpace().equals("HoldQ")){
            if(token.getSpace().tryToLeave(playerNumber, forward)){
                //If the player can leave the HoldQ update fields
                System.out.println("Player "+playerNumber+", token "+ token.getTokenNumber()+" has left the holdQ.");
                token.getSpace().removeToken(playerNumber);
                token.setFirstTime(true);
                
                //Roll the dice
                int mult = token.getSpace().getMult();
                int newRoll = Statics.getDice().roll();
                int spaces = newRoll * mult;
                
                //Move Forward
                moveForward(spaces, levels);
                lastRoll = newRoll;
                return;
            }
            return;
        }

        if(token.getCurrentSpace().equals("PriorityHold")){
            if(token.getSpace().tryToLeave(playerNumber, forward)){
                //If the player can leave the PriorityHold
                System.out.println("Player "+playerNumber+", token "+ token.getTokenNumber()+" has left the priority queue.");
                token.getSpace().removeToken(playerNumber);
                token.setFirstTime(true);
                
                //Roll the die
                int mult = token.getSpace().getMult();
                int newRoll = Statics.getDice().roll();
                int spaces = newRoll * mult;
                
                //Move the token
                moveForward(spaces, levels);
                lastRoll = newRoll;
                return;
            }
            return;
        }
        
        if(token.getCurrentSpace().equals("Entrance")){
            if(token.getSpace().checkExit(token)){
                //If the player can leave the Entrance
                System.out.println("Player "+playerNumber+", token "+ token.getTokenNumber()+" has left maze "+ token.getSpace().getMazeNumber());
                int treasure = token.getSpace().placeToken(token);
                treasureTotal = treasureTotal + treasure;
                token.setFirstTime(true);
                token.setSpace("TreasurePotC");
                token.getSpace().removeToken(token);
                return;
            }
            else if(token.getSpace().tryToMove(token, forward)){
                System.out.println("Player "+playerNumber+", token "+ token.getTokenNumber()+" is moving in maze "+ token.getSpace().getMazeNumber());
                token.getSpace().moveToken(token, forward);
                return;
            }
            return;
        }
        
    }

    /**
     * secondaryMoves - a method that completes the actions after all players have landed on their spaces 
     * for that round. The method first checks if a player has won the game. If that is not the case, the method
     * either distributes treasure, adds a token to a hold, or moves the players up and down levels.
     */
    
    private void secondaryMoves(ArrayList<Boolean> levels){
        if(finish){
            return;
        }
        
        if(token.getCurrentSpace().equals("JStack")){
            System.out.println("Player "+playerNumber+" token "+ token.getTokenNumber()+" has landed on a stack space with a multiplier of "+ token.getSpace().getMult() + " and a last roll of "+lastRoll);
            stackLevels.add(token.getZ());
            
            points = points + token.moveZ(lastRoll*token.getSpace().getMult(), levels);
            finish = tokens.checkEnd(playerNumber);
            if(finish){
                return;
            }
            else if(!stackLevels.contains(token.getZ())){
                secondaryMoves(levels);
            }else{
                stackLevels = new ArrayList<Integer>();
            }
            //We don't return here because we may need to add this token to a hold 
        }
        
        //If Empty do nothing
        if(token.getCurrentSpace().equals("Empty")){
            System.out.println("Player "+playerNumber+ " token "+ token.getTokenNumber()+" has landed on an empty space with a roll of "+lastRoll);
            return;
        }
        
        //If we land on an exit
        if(token.getCurrentSpace().equals("TreasurePotC")){
            System.out.println("Player "+playerNumber+ " token "+ token.getTokenNumber()+" has landed on an exit space with a roll of "+lastRoll);
            return;
        }
        
        
        //If they land on a treasure space, get the treasure and add it to their treasure total
        if(token.getCurrentSpace().equals("TreasurePotA")){ 
            int treasureAddition = token.getSpace().getTreasure(0);
            System.out.println("Player "+playerNumber+ " token "+ token.getTokenNumber()+ " has landed on a Treasure Pot A with a roll of "+lastRoll+" collecting "+treasureAddition+" treasures.");
            treasureTotal = treasureTotal + treasureAddition;
            return;
        }

        if(token.getCurrentSpace().equals("TreasurePotB")){
            int treasureAddition = token.getSpace().getTreasure(lastRoll);
            System.out.println("Player "+playerNumber+ " token "+ token.getTokenNumber()+ " has landed on a Treasure Pot B with a roll of "+lastRoll+" collecting "+ treasureAddition + " treasures.");
            treasureTotal = treasureTotal + treasureAddition;
            return;
        }
        
        //If they land on a hold, and its their first time, add them to the Maze. If it isnt, dont change anything
        if(token.getCurrentSpace().equals("Entrance")){
            if(token.getFirstTime()){
                token.getSpace().addToMaze(token, lastRoll);
                System.out.println("Player "+playerNumber+ " token "+ token.getTokenNumber()+" has entered the maze.");
                token.setFirstTime(false);
                return;
            }
            else{
                System.out.println("No change to Player "+ playerNumber+" token "+ token.getTokenNumber()+". They are in a maze "+ token.getSpace().getMazeNumber());
            }
        }

        
        if(token.getCurrentSpace().equals("Hold")){
            
            if(token.getFirstTime()){
                token.getSpace().addToHold(token, lastRoll, playerNumber);
                System.out.println("Player "+playerNumber+ " token "+ token.getTokenNumber()+" has entered a hold with a roll of "+ lastRoll);
                token.setFirstTime(false);
                return;
            }
            else{
                System.out.println("No change to Player "+ playerNumber+" token "+ token.getTokenNumber() + ". They are in a hold.");
            }
        }
        
        if(token.getCurrentSpace().equals("HoldQ")){
             if(token.getFirstTime()){
                token.getSpace().addToHoldQ(token, lastRoll, playerNumber);
                System.out.println("Player "+playerNumber+ " token "+ token.getTokenNumber()+" entered the hold queue with a roll of "+ lastRoll);
                token.setFirstTime(false);
                return;
            }
            else{
               System.out.println("No change to Player "+ playerNumber +" token "+ token.getTokenNumber()+ ". They are in a hold queue.");
            }
        }
        
        if(token.getCurrentSpace().equals("PriorityHold")){
            if(token.getFirstTime()){
                token.getSpace().addToQueue(token, lastRoll, playerNumber);
                System.out.println("Player "+playerNumber+ " token "+ token.getTokenNumber()+" entered the priority queue with a roll of "+lastRoll);
                token.setFirstTime(false);
                return;
            }
            else{
                System.out.println("No change to Player "+ playerNumber +" token "+ token.getTokenNumber()+ ". They are in a priority queue.");
            }
        }
    }

    /**
     * moveForward - a method that advances the token. The space is then updated. If a player has won, it
     * is returned. If not the lastRoll variable is updated.
     * @param size - the size of the roll
     */
    
    private void moveForward(int size, ArrayList<Boolean> levels){
        token.setSpaceNumber(size, playerNumber);
        if(levels.get(token.getZ())==false){
            points = points + (int)Math.ceil(Statics.getBoard().getReader().getScoring().get(0)/2);
            levels.set(token.getZ(), true);
        }
        
        finish = tokens.checkEnd(playerNumber);
        if(finish){
            ChutesAndLadders.finisher = this;
            return;
        }
        lastRoll = size;
    }
    
    /**
     * getLastRoll - returns this player's last roll
     * 
     * @return int - the last roll
     */
    public int getLastRoll(){
        return lastRoll;
    }
    
    /**
     * getToken - returns this player's token.
     * 
     * @return ArrayList<Token> - this player's token 
     */
    public Token getToken(){
        return token;
    }
    
    /**
     * getTokens - returns this player's tokens.
     * 
     * @return ArrayList<Token> - this player's tokens 
     */
    public TokenHolder getTokens(){
        return tokens;
    }
    
    /**
     * getTreasure - returns this player's treasureTotal
     * 
     * @return int - the treasureTotal
     */
    public int getTreasure(){
        return treasureTotal;
    }
    
    /**
     * addTreasure - adds treasure to the player
     * @param t - the treasure to be added
     */
    public void addTreasure(int t){
        treasureTotal = treasureTotal + t;
    }
    
    
    /**
     * getPersonality - a method that returns the players personality 
     * @return String - the personality
     */
    public String getPersonality(){
        return personality;
    }
    
    /**
     * getPoints - gets the number of points earned by this token
     * @return Integer - the player's points
     */
    public Integer getPoints(){
        return points;
    }
    
    /**
     * getPlayerNumber - a method that returns the players number
     * @return int - the player number
     */
    public int getPlayerNumber(){
        return playerNumber;
    }
    
    /**
     * checkfinish - checks if this player has won the game
     * 
     * @return boolean - true if the player has won 
     */
    public boolean checkFinish(){
        return finish;
    }
    
    /**
     * toString - toString for this player
     * @return String - player number, the space location, and the treasure total. 
     */
    public String toString(){
        return "Player "+playerNumber+", a "+ personality+" player, has "+tokens.size()+" tokens with "+treasureTotal+" treasures.";
    }
}