import java.util.LinkedList;
import java.util.ArrayList;

/**
 *A class to represent the entrance to a graph on the game board.
 * 
 * @author Benjamin Draves
 * @version May 5, 2016
 */
public class Entrance extends Space
{
    private int mazeNumber, graphNumber;
    private LinkedList<TokenInMaze> tokens;
    private LinkedList<Integer> currentNodes; 
    /**
     * Entrances - a constructor for spaces of type Entrances. These will the entrances to the mazes
     * @param mn - the node number for the entrances (corresponds to the graph)
     * @param gn - the graph number this space corresponds to
     */
    public Entrance(int mn, int gn)
    {
        mazeNumber = mn+1;
        graphNumber = gn;
        tokens = new LinkedList<TokenInMaze>();
        currentNodes = new LinkedList<Integer>();
        type = "Entrance";
    }

    /**
     * addToMaze - a method that places a token into a maze
     * @param t - the token to be added
     * @param roll - the roll the token entered with
     * @return boolean - if the token was successfully added
     */
    public boolean addToMaze(Token t, Integer roll){
        tokens.add(new TokenInMaze(t,roll));
        int current = currentNodes.size();
        currentNodes.add(graphNumber);
        if(current + 1 == currentNodes.size()){
            return true;
        }else{
            return false;
        }
        
    }

    /**
     * tryToMove - a method to see if a token can move or not within the graph
     * @param t - the token
     * @param roll - the roll they currently have 
     * @return boolean - if the token can move or not
     */
    public boolean tryToMove(Token t, Integer roll){
        
        for(int i = 0; i<tokens.size(); i++){
            if(t.getTokenNumber() == tokens.get(i).getToken().getTokenNumber() && t.getPlayerNumber() == tokens.get(i).getToken().getPlayerNumber()){
                for(int j = 0; j<Statics.getBoard().getMazes().get(mazeNumber-1).getOutgoingWeights(currentNodes.get(i)).size(); j++){
                    
                    if(roll.equals(Statics.getBoard().getMazes().get(mazeNumber-1).getOutgoingWeights(currentNodes.get(i)).get(j))){
                        
                        
                        return true;
                    }
                }
            }
        }
        
        return false;
    }

    /**
     * moveToken- a method that moves the token on the graph from node to node 
     * @param t - the token to move 
     * @param roll - the roll
     * @return boolean - if the player was moved or not
     */
    public boolean moveToken(Token t, Integer roll){
        for(int i = 0; i<tokens.size(); i++){
            if(t.getTokenNumber() == tokens.get(i).getToken().getTokenNumber() && t.getPlayerNumber() == tokens.get(i).getToken().getPlayerNumber()){
                for(int j = 0; j<Statics.getBoard().getMazes().get(mazeNumber-1).getOutgoingWeights(currentNodes.get(i)).size(); j++){
                    if(roll.equals(Statics.getBoard().getMazes().get(mazeNumber-1).getOutgoingWeights(currentNodes.get(i)).get(j))){
                        currentNodes.set(i,Statics.getBoard().getMazes().get(mazeNumber-1).getOutgoing(currentNodes.get(i)).get(j));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * checkExit - a method that checks if the token made it to an exit of the graph
     * @param t - the token of interest 
     * @return boolean - if the token is at an exit
     */
    public boolean checkExit(Token t){
        for(int i = 0; i<tokens.size(); i++){
            if(t.getTokenNumber() == tokens.get(i).getToken().getTokenNumber() && t.getPlayerNumber() == tokens.get(i).getToken().getPlayerNumber()){
                for(int j = 0; j<Statics.getBoard().getMazes().get(mazeNumber-1).getExits().size(); j++){
                    if(currentNodes.get(i) == Statics.getBoard().getMazes().get(mazeNumber-1).getExits().get(j)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * placeToken - a method that moves the token to the corresponding exit space of the game board 
     * @param t - the token to move 
     * @return int - the treasure the token gets when leaving the maze
     */
    public int placeToken(Token t){
        for(int i = 0; i<tokens.size(); i++){
            if(t.getTokenNumber() == tokens.get(i).getToken().getTokenNumber() && t.getPlayerNumber() == tokens.get(i).getToken().getPlayerNumber()){
                for(int j = 0; j<Statics.getBoard().getGraphExits().size(); j++){
                    if(Statics.getBoard().getGraphExits().get(j).getMazeNumber()==mazeNumber && Statics.getBoard().getGraphExits().get(j).getGraphNumber()==currentNodes.get(i)){
                        ArrayList<Integer> location = Statics.getBoard().getGraphExits().get(j).getLocation();
                        t.setXYZ(location.get(0),location.get(1),location.get(2));
                        return tokens.get(i).getDistance();
                    }
                }
            }
        }
        return 0;
    }

    /**
     * removeToken - a method that removes the token from the maze 
     * @param t - the token to remove 
     * @return boolean - if the token was removed or not 
     */
    public boolean removeToken(Token t){
        for(int i = 0; i<tokens.size(); i++){
            if(t.getTokenNumber() == tokens.get(i).getToken().getTokenNumber() && t.getPlayerNumber() == tokens.get(i).getToken().getPlayerNumber()){
                tokens.remove(i);
                currentNodes.remove(i);
                return true;
            }
        }
        return false;
    }
    
    /**
     * getMazeNumber - a method that returns the maze number associated with the space
     * @return int - the maze number
     */
    public int getMazeNumber(){
        return mazeNumber;
    }
    
    /**
     * getGraphNumber - a method that returns the graph number associated with the space
     * @return int - the graph number
     */
    public int getGraphNumber(){
        return graphNumber;
    }

    /**
     * toString - a toString method that summarizes the entrance space 
     * @String - the summary 
     */
    public String toString(){
        return "An entrance to graph "+mazeNumber + " with number "+ graphNumber;
    }
    
    /**
     * getTokens - a method that returns the tokens in the maze
     * @return ArrayList<Token> - the tokens in the maze
     */
    public ArrayList<Token> getTokens(){
        ArrayList<Token> tokes = new ArrayList<Token>();
        for(int i = 0; i<tokens.size(); i++){
            tokes.add(tokens.get(i).getToken());
        }
        return tokes;
    }
    
    /**
     * getCurrentNodes - a method that returns the current nodes keys
     * @return LinkedList<Integer> - the current nodes keys
     */
    public LinkedList<Integer> getCurrentNodes(){
        return currentNodes;
    }
    
    /**
     * TokenInMaze - an inner class that stores the tokens in the maze
     */
    public class TokenInMaze{
        private Token toke;
        private int lastRoll;
        private int distance;
        /**
         * A constructor of this class. Stores distance traveled, the token, and the lastRoll of this token
         * @param t - the token in the maze 
         * @param roll - the roll of the token to enter the maze
         */
        TokenInMaze(Token t, int roll){
            toke = t;
            lastRoll = roll;
            distance = 0;
        }

        /**
         * getToken - a method that returns the token
         * @return Token - the token
         */
        public Token getToken(){
            return toke;
        }

        /**
         * setDistance - a method that sets the distance traveled by the token
         * @param d - the additional distance traveled
         */
        public void setDistance(int d){
            distance = distance + d;
        }

        /**
         * getDistance - a method that gets the distance traveled by this token
         * @return int - the distance traveled by this token 
         */
        public int getDistance(){
            return distance;
        }

    }

}
