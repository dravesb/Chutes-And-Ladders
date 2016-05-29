
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Spaces Class. An abstract class that will provide structure for all classes below it.
 * 
 * @author Benjamin Draves
 * @version March 4, 2016
 */
abstract class Space
{
    protected String type;
    protected Reader reader;

    /**
     * Constructor for objects of class Spaces. Only initializes the reader object that will be used in all subclasses. 
     */
    public Space()
    {  
        reader = new Reader(Statics.getFile());
    }

    /**
     * getType - a method that returns the type of the space
     * @return String - the type of space
     */
    public String getType(){
        return type;
    }
    
    /**
     * getTokens - a method that returns the tokens in the maze
     */
    public ArrayList<Token> getTokens(){
        return null;
    }

    /**
     * isEmpty - checks if the space is empty 
     * @return boolean - of the space container is empty
     */
    public boolean isEmpty(){
        return false;
    }
    
    /**
     * getCurrentNodes - a method that returns the current nodes keys
     * @return LinkedList<Integer> - the current nodes keys
     */
    public LinkedList<Integer> getCurrentNodes(){
        return null;
    }
    
    /**
     * getGraphNumber - gets the graph number 
     * @return int - the graph number
     */
    public int getGraphNumber(){
        return -1;
    }

    /**
     * getLocation - gets the location of the space
     * @return - ArrayList<Integer> the x,y,z coordinates of this space
     */
    public ArrayList<Integer> getLocation(){
        return null;
    }

    /**
     * size - gets the size of the container 
     * @return int - the size
     */
    public int size(){
        return -1;
    }

    /**
     * getTreasureTotal - the treasure total of this space 
     * @param a - the roll 
     * @return int - the treasure total
     */
    public int getTreasureTotal(int a){
        return 0;
    }

    /**
     * getMazeNumber - returns the maze number of the space 
     * @return int - the maze number
     */
    public int getMazeNumber(){
        return -1;
    }

    /**
     * placeToken - a method that moves the token across the board
     * @param t - the token to move 
     * @return int - the tresure to be added 
     */
    public int placeToken(Token t){
        return -1;
    }

    /**
     * tryToMove - a method that sees if a token can move in a maze 
     * @param t - the token to move 
     * @param i - the roll 
     * @return boolean - if the token can move 
     */
    public boolean tryToMove(Token t, Integer i){
        return false;
    }

    /**
     * moveToken - a method that moves the token in the maze 
     * @param t - the token to move 
     * @param i - the roll 
     * @return boolean - if the token was moved
     */
    public boolean moveToken(Token t, Integer i){
        return false;
    }

    /**
     * addToMaze - a method that adds the token to the maze 
     * @param t - the token to add
     * @param i - the roll 
     * @return boolean - if the token was added 
     */
    public boolean addToMaze(Token t, Integer i){
        return false;
    }

    /**
     * checkExit - see if the token is on an exit 
     * @param t - the token of interest 
     * @return boolean - if the token is on an exit or not
     */
    public boolean checkExit(Token t){
        return true;
    }

    /**
     * getTreasure - a method to be instantiated in the treasure pot classes. Returns the treasure attained from this treasure pot
     * @param roll - to be used in treasure pot b will not be used in treasure pot a
     * @return int
     */
    public int getTreasure(int roll){
        return 0;
    }

    /**
     * addToHold - a method to be instantiated in the Hold class. Takes a player's token, their last roll, and their playerNumber and adds the player 
     * to the hold.
     * @param toke - the Token to be added 
     * @param lastRoll - the last roll of the player
     * @param playerNumber - the player's Number
     */
    public void addToHold(Token toke, int lastRoll, int playerNumber){

    }

    /**
     * addToHoldQ - a method to be instantiated in the HoldQ class. Takes a player's token, their last roll, and their playerNumber and adds the player 
     * to the hold.
     * @param toke - the Token to be added 
     * @param lastRoll - the last roll of the player
     * @param playerNumber - the player's Number
     */    public void addToHoldQ(Token toke, int roll, int playerNum){

    }

    /**
     * addToQueue - a method to be instantiated in the Priority Queue class. Takes a players token, their last roll, and their player number
     * @param toke - the token to be added
     * @param a - the last roll 
     * @param b - the player number
     */
    public void addToQueue(Token toke, int a, int b){
    }

    /**
     * getHere - a method that returns the space
     * @return Space - this space
     */
    public Space getHere(){
        return this;
    }

    /**
     * removeToken - a method that removes a token from a hold. Clears all data structures of this token
     * @param num- the playerNumber 
     */
    public void removeToken(int num){

    }

    /**
     * removeToken - a method that removes a token from a hold. Clears all data structures of this token
     * @param num- the playerNumber 
     */
    public boolean removeToken(Token t){
        return false;
    }

    /**
     * tryToLeave - a method to be instantiated in the Hold classes. The players try to leave the hold by rolling their past roll.
     * @param num - the playerNumber
     * @return boolean - true if the player can leave
     */
    public boolean tryToLeave(int playerNum, int roll){
        return true;
    }

    /** 
     *getMult- a method that retuns the multiplier of the hold classes 
     */
    public int getMult(){
        return 1;
    }

    /**
     * isFirst - a method that checks if the player is the first in this queue 
     * @param num - the playerNumber 
     * @return boolean - true if the player is first in the queue
     */
    public boolean isFirst(int num){
        return false;
    }

    /**
     * toString - a toString method to be instaniated in the child classes
     * @return String - the string describing this space
     */
    public String toString(){
        return ""; 
    }
}
