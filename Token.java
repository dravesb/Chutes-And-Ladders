import java.lang.Math;
import java.util.ArrayList;

/**
 * The Token class. A class that represents the pieces players advance around the board. The position 
 * of the token will follow matrix notation for indexing.
 * 
 * @author Benjamin Draves
 * @version February 27, 2016
 */
public class Token
{
    private int x, y, z, tokenNumber, playerNumber;
    private String currentSpace;
    private ArrayList<Integer> dimensions;
    private boolean firstTime, finished;
    
    /**
     * Constructor for objects of class Token. Sets default position to (0,0,0).
     */
    public Token(int tokenNum, int playerNum)
    {
        x = 0;
        y = 0;
        z = 0;
        tokenNumber = tokenNum;
        playerNumber = playerNum;
        currentSpace = "Empty";
        firstTime = true;
        finished = false;
        dimensions = Statics.getBoard().getDimensions();
    }

    /**
     * getX - a method that returns the x - location of the token. 
     * 
     * @return int- the x location.
     */
    public int getX()
    {
        return (x);
    }

    /**
     * getY - a method that returns the y - location of the token.
     * 
     * @return int- the y location.
     */
    public int getY()
    {
        return (y);
    }

    /**
     * getZ - a method that returns the z - location of the token.
     * @return int- the z location.
     */
    public int getZ()
    {
        return (z);
    }
    
    /**
     * getFirstTime - a method that returns firstTime 
     * @return boolean- if its the token's first time in a hold or not
     */
    public boolean getFirstTime()
    {
        return(firstTime);
    }
    
    /**
     * setFirstTime - a method that sets firstTime 
     * @param bool- if its the token's first time in a hold or not
     */
    public void setFirstTime(boolean bool)
    {
        firstTime = bool;
    }
    
    /**
     * getCurrentSpace - a method that returns current space 
     * @return String- the current space
     */
    public String getCurrentSpace()
    {
        return(currentSpace);
    }
    
    /**
     * getPlayerNumber - a method that returns the player number
     * @return int- the player number
     */
    public int getPlayerNumber()
    {
        return playerNumber;
    }
    
    
    /**
     * getTokenNumber - a method that returns the token number
     * @return int- the token number
     */
    public int getTokenNumber()
    {
        return tokenNumber;
    }
    
    /**
     * setSpaceNumber- a method to move the token around the board.
     * 
     * @param advance - the distance to move the token (either positive or negitive)
     * @param playerNumber - the player number of the player moving the token.
     * 
     */
    public void setSpaceNumber(int advance, int playerNumber)
    {
        //Calculate total distance from (0,0,0)
        int absoluteDistance = z * dimensions.get(0)*dimensions.get(1) + y*dimensions.get(0) + x + advance;

        if(advance == 0){
            return;
        }
        else{
            //If the player overshoots the final square
            if (absoluteDistance >= dimensions.get(0)*dimensions.get(1)*dimensions.get(2)){
                System.out.println("Player "+playerNumber+" token "+tokenNumber+ " has overshot the final tile and cannot move.");
                return;
            }
            else if(absoluteDistance<0){                
                System.out.println("Player "+playerNumber+" token "+tokenNumber+ "has gone off the edge. Setting position to (0,0,0)");
                x = 0; 
                y = 0;
                z = 0;
            }
            else{
                //Division algorithm
                int count = 0;
                while(absoluteDistance>=dimensions.get(0)*dimensions.get(1)){
                    absoluteDistance =  absoluteDistance - dimensions.get(0)*dimensions.get(1);
                    count++;
                }
                z = count;
                count = 0;
                while(absoluteDistance>=dimensions.get(0)){
                    absoluteDistance =  absoluteDistance - dimensions.get(0);
                    count++;
                }
                y = count;
                x = absoluteDistance; 
            }
        }
        currentSpace = Statics.getBoard().getSpace(x,y,z).getType();
        finished = checkEnd();
    }

    /**
     * getFutureSpace- a method to check the future space of a token
     * 
     * @param advance - the distance the token would move (either positive or negitive)
     * @return Space - the future Space
     */
    public Space getFutureSpace(int advance)
    {
        //Calculate total distance from (0,0,0)
        int absoluteDistance = z * dimensions.get(0)*dimensions.get(1) + y*dimensions.get(0) + x + advance;
        //Coordinates of the future space
        ArrayList<Integer> coor = new ArrayList<Integer>();

        if(advance == 0){
            coor.add(x);
            coor.add(y);
            coor.add(z);
            return Statics.getBoard().getSpace(coor.get(0),coor.get(1),coor.get(2));
        }
        else{
            //If the player overshoots the final square
            if (absoluteDistance >= dimensions.get(0)*dimensions.get(1)*dimensions.get(2)){
                coor.add(x);
                coor.add(y);
                coor.add(z);
                return Statics.getBoard().getSpace(coor.get(0),coor.get(1),coor.get(2));
            }
            else if(absoluteDistance<0){                
                coor.add(0);
                coor.add(0);
                coor.add(0);
                return Statics.getBoard().getSpace(coor.get(0),coor.get(1),coor.get(2));
            }
            else{
                //Division algorithm
                int count = 0;
                while(absoluteDistance>=dimensions.get(0)*dimensions.get(1)){
                    absoluteDistance =  absoluteDistance - dimensions.get(0)*dimensions.get(1);
                    count++;
                }
                coor.add(0,count);
                count = 0;
                while(absoluteDistance>=dimensions.get(0)){
                    absoluteDistance =  absoluteDistance - dimensions.get(0);
                    count++;
                }
                coor.add(0,count);
                coor.add(0,absoluteDistance);

                if(Statics.getBoard().getSpace(coor.get(0),coor.get(1),coor.get(2)).getType().equals("JStack")){
                    //If levels is nonegitive, division algorithm then set the z value
                    int levels = advance * Statics.getBoard().getSpace(coor.get(0),coor.get(1),coor.get(2)).getMult();
                    levels = levels + coor.get(2);
                    if(levels>=0){
                        while(levels>=dimensions.get(2)){
                            levels = levels - dimensions.get(2);
                        }
                        coor.set(2,levels);
                    }
                    //If levels is negitive, division algorithm on absolute value then set the z value to dimension 
                    //and levels differnt
                    else{
                        levels = Math.abs(levels);
                        while(levels>dimensions.get(2)){
                            levels = levels - dimensions.get(2);
                        }
                        coor.set(2,dimensions.get(2)-levels);
                    }   
                    return Statics.getBoard().getSpace(coor.get(0),coor.get(1),coor.get(2));
                }

                else{
                    return Statics.getBoard().getSpace(coor.get(0),coor.get(1),coor.get(2));
                }

            }
        }

    }

    /**
     * moveZ- a method that moves a token across the z coordinate of the board 
     * @param levels - number of levels to move 
     * @param booleans - used for scoring 
     * @return int - the amount gained from moving 
     */
    public int moveZ(int levels, ArrayList<Boolean> booleans){
        //If levels is nonegitive, division algorithm then set the z value
        levels = levels + z;
        if(levels>=0){
            while(levels>=dimensions.get(2)){
                levels = levels - dimensions.get(2);
            }
            z = levels;
        }
        //If levels is negitive, division algorithm on absolute value then set the z value to dimension 
        //and levels differnt
        else{
            levels = Math.abs(levels);
            while(levels>dimensions.get(2)){
                levels = levels - dimensions.get(2);
            }
            z = dimensions.get(2) - levels;

        }
        currentSpace = Statics.getBoard().getSpace(x,y,z).getType();
        finished = checkEnd();
        if(booleans.get(z)==false){
            booleans.set(z, true);
            return (int)Math.ceil(Statics.getBoard().getReader().getScoring().get(0)/2);
        }else if(finished && booleans.get(booleans.size()-1) == false){
            booleans.set(booleans.size()-1, true);
            return Statics.getBoard().getReader().getScoring().get(0).intValue();
        }else{
            return 0;
        }
    }
    
    /**
     * setXYZ - a method that sets the x y z position of the token
     */
    public void setXYZ(int newX, int newY, int newZ){
        x = newX;
        y = newY;
        z = newZ;
    }

    /**
     * updateSpace - a method that returns the type of space the token is currently on.
     * 
     * @return String - the type of space the token is on (e.g. "TreasurePotA")
     */

    public String updateSpace(){
        return(Statics.getBoard().getSpace(x,y,z).getType());
    }

    /**
     * getSpace - a method that returns the space the token is currently on. 
     * @return Space - the space the token is on.
     */

    public Space getSpace(){
        return(Statics.getBoard().getSpace(x,y,z));
    }
    
    /**
     * getDistance - get the absolute distance from (0,0,0)
     * @return int- the absolute distane
     */
    public int getDistance(){
        return z * dimensions.get(0)*dimensions.get(1) + y*dimensions.get(0) + x;
    }
    
    /**
     * getFinish - a method to check if the token has finished or not
     * @return boolean - if the token has finished or not
     */
    public boolean getFinish(){
        return finished;
    }

    /**
     * checkEnd - checks if the token is on the last space. Will call up the class tree to terminate
     * the game if true.
     * 
     * @param i- the player number
     * @return boolean- if the player has won the game or not
     */

    private boolean checkEnd(){
        if(x==dimensions.get(0)-1 && y==dimensions.get(1)-1 && z==dimensions.get(2)-1 ){
            return true;
        }
        return false; 
    }
    
    /**
     * setSpace - a method that sets the space the token is on
     * @param s - the type of space 
     * @return boolean - if the name was updated
     */
    public boolean setSpace(String s){
        currentSpace = s;
        return true;
    }

}

