import java.util.ArrayList;
import java.util.Random;

/**
 * TokenHolder - a class that holds all the player's tokens in a game.
 * 
 * @author Benjamin Draves
 * @version April 6
 */
public class TokenHolder
{
    private ArrayList<Token> holder;

    /**
     * TokenHolder - a constructor for objects of type TokenHolder. 
     * @param numOfTokens - the number of tokens to be added to the number of tokens
     */
    public TokenHolder(int numOfTokens, int playerNum){
        holder = new ArrayList<Token>();
        for(int i=0; i<numOfTokens; i++){
            holder.add(new Token(i+1, playerNum));
        }
    }

    /**
     * get - a method that gets the token at index i
     * @param i - the token number 
     * @return Token - the i-th token
     */
    public Token get(int i){
        return holder.get(i);
    }

    /**
     * size - a method to get the number of tokens in this holder
     * @return int - the size of the holder
     */
    public int size(){
        return holder.size();
    }

    /**
     * chooseRandom - a method that chooses a token at random that has not finished 
     * @return Token - the randomly selected token
     */
    public Token chooseRandom(){
        int count = 0;
        Random random = new Random();

        while(count<holder.size()){
            int temp = random.nextInt(holder.size());
            Token token = holder.get(temp);
            if(!token.getFinish()){
                return token;
            }
            count++;
        }
        
        return null;
    }

    /**
     * checkEnd - a method to check if all tokens in this holder are on the final space
     * @param j - the player Number
     * @return boolean - if the player has finished
     */
    public boolean checkEnd(int j ){
        for(int i = 0; i<holder.size(); i++){
            if(!holder.get(i).getFinish()){
                return false;
            }
        }

        return true;
    }

    /**
     * decideToken - a method that decides which token to move
     * @param personality - the player's personality
     * @param roll - the current roll 
     * @param playerNumber - the playerNumber 
     * @return Token - the token to move
     */
    public Token decideToken(String personality, int roll, int playerNumber){
        if(personality.equals("Hybrid")){
            if(Statics.getBoard().getReader().getScoring().get(0)<625){
                personality = "TreasureHunter";
            }
            else if(Statics.getBoard().getReader().getScoring().get(0)>675){
                personality = "SmartSprinter";
            }
            else{
                Random random = new Random();
                int temp = random.nextInt(2);
                if(temp == 0){
                    personality = "TreasureHunter";
                }else{
                    personality = "SmartSprinter";
                }
            }
        }

        if(personality.equals("Random")){
            return chooseRandom();
        }

        else if(personality.equals("Sprinter")){
            //Find the token with the maximum distance and try to move it
            int max = holder.get(0).getDistance();
            int index = 0;

            for(int i = 0; i<holder.size(); i++){
                if(holder.get(i).getDistance()>max){
                    max = holder.get(i).getDistance();
                    index = i;
                }
            }

            if(holder.get(index).getFinish()){
                return chooseRandom();
            }else{
                return holder.get(index);
            }

        }

        else if(personality.equals("TreasureHunter")){
            //A player that tries to collect as much treasure as possible

            //Find all players in queues and remove them from consideration
            ArrayList<Boolean> inQueue = new ArrayList<Boolean>();
            for(int i = 0; i<holder.size(); i++){
                String s = holder.get(i).getSpace().getType();
                if(s.equals("Hold") || s.equals("HoldQ") || s.equals("PriorityHold")){
                    inQueue.add(true);
                }else{
                    inQueue.add(false);
                }
            }

            //For the remaining players, find the future space treasure pay off
            ArrayList<Integer> treasures = new ArrayList<Integer>();
            for(int i = 0; i<holder.size(); i++){
                if(!inQueue.get(i)){
                    int treasure = holder.get(i).getFutureSpace(roll).getTreasureTotal(roll);
                    treasures.add(treasure);
                }else{
                    treasures.add(0);
                }
            }

            //Find the maximum amount of treasure
            int max = treasures.get(0);
            int index = 0;
            for(int i = 0; i<holder.size(); i++){
                if(treasures.get(i)>max){
                    max = treasures.get(i);
                    index = i;
                }
            }

            //If no treasure can be won, move randomly. Otherwise 
            if(max == 0){
                return chooseRandom();
            }else{
                return holder.get(index);
            }
        }
        
        else if(personality.equals("MazeRunner")){
            //A player that tries to get into every maze possible

            //Find all players in queues and remove them from consideration
            ArrayList<Boolean> inQueue = new ArrayList<Boolean>();
            for(int i = 0; i<holder.size(); i++){
                String s = holder.get(i).getSpace().getType();
                if(s.equals("Hold") || s.equals("HoldQ") || s.equals("PriorityHold")){
                    inQueue.add(true);
                }else{
                    inQueue.add(false);
                }
            }

            //For the remaining players, find the future space type
            ArrayList<String> spaces = new ArrayList<String>();
            for(int i = 0; i<holder.size(); i++){
                if(!inQueue.get(i)){
                    String type = holder.get(i).getFutureSpace(roll).getType();
                    spaces.add(type);
                }else{
                    spaces.add("");
                }
            }

            //Find the maximum amount of treasure
            int index = -1;
            for(int i = 0; i<holder.size(); i++){
                if(spaces.get(i).equals("Entrance")){
                    index = i;
                    break;
                }
            }

            //If no treasure can be won, move randomly. Otherwise 
            if(index == -1){
                return chooseRandom();
            }else{
                return holder.get(index);
            }
        }

        else if(personality.equals("AntiHold")){
            //A player that tries to keep all of its players out of queues

            //Find which are in queues
            ArrayList<Boolean> inQueue = new ArrayList<Boolean>();
            for(int i = 0; i<holder.size(); i++){
                String s = holder.get(i).getSpace().getType();
                if(s.equals("Hold") || s.equals("HoldQ") || s.equals("PriorityHold")){
                    inQueue.add(true);
                }else{
                    inQueue.add(false);
                }
            }

            //See which of these can leave
            ArrayList<Boolean> ableToLeaveQueue = new ArrayList<Boolean>();
            for(int i = 0; i<inQueue.size(); i++){
                if(inQueue.get(i) && holder.get(i).getSpace().tryToLeave(playerNumber, roll)){
                    ableToLeaveQueue.add(true);
                }else{
                    ableToLeaveQueue.add(false);
                }
            }

            //Return the first token that is in a queue and can now move

            for(int i=0; i<ableToLeaveQueue.size(); i++){
                if(ableToLeaveQueue.get(i)){
                    return holder.get(i);
                }
            }

            //If no token is in a queue or cannot move, just move a random token
            return chooseRandom();
        }

        else if (personality.equals("Tortoise")){
            //Find the token with the minimum distance and try to move it
            int min = holder.get(0).getDistance();
            int index = 0;

            for(int i = 0; i<holder.size(); i++){
                if(holder.get(i).getDistance()<min){
                    min = holder.get(i).getDistance();
                    index = i;
                }
            }
            return holder.get(index);
        }

        else if(personality.equals("SmartSprinter")){
            //A player that moves the furtherest token if it isnt able to leave a queue

            //Find all players in a hold
            ArrayList<Boolean> inQueue = new ArrayList<Boolean>();
            for(int i = 0; i<holder.size(); i++){
                String s = holder.get(i).getSpace().getType();
                if(s.equals("Hold") || s.equals("HoldQ") || s.equals("PriorityHold")){
                    inQueue.add(true);
                }else{
                    inQueue.add(false);
                }
            }

            //See which of these can leave
            ArrayList<Boolean> ableToLeaveQueue = new ArrayList<Boolean>();
            for(int i = 0; i<inQueue.size(); i++){
                if(inQueue.get(i) && holder.get(i).getSpace().tryToLeave(playerNumber, roll)){
                    ableToLeaveQueue.add(true);
                }else{
                    ableToLeaveQueue.add(false);
                }
            }

            //Find max that is also able to leave

            int max = holder.get(0).getDistance();
            int index = 0;

            for(int i = 0; i<holder.size(); i++){
                if(holder.get(i).getDistance()>max && ableToLeaveQueue.get(i)){
                    max = holder.get(i).getDistance();
                    index = i;
                }
            }
            
            if(holder.get(index).getFinish()){
                return chooseRandom();
            }else{
                return holder.get(index);
            }
        }

        else{
            return null;
        }
    }

}
