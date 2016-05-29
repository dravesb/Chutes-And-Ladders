import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Reader Class. An object that reads a configuration file. 
 * 
 * @author Benjamin Draves
 * @version March 22, 2016
 */
public class Reader
{
    ArrayList<Integer> holdQList = new ArrayList<Integer>();
    ArrayList<Integer> holdList = new ArrayList<Integer>();
    ArrayList<Integer> priorityHoldList = new ArrayList<Integer>();
    ArrayList<Integer> treasurePotAList = new ArrayList<Integer>();
    ArrayList<Integer> treasurePotBList = new ArrayList<Integer>();
    ArrayList<Integer> jStackList = new ArrayList<Integer>();
    ArrayList<Integer> emptyList = new ArrayList<Integer>();
    ArrayList<Integer> board = new ArrayList<Integer>();
    ArrayList<Double> scoring = new ArrayList<Double>();
    ArrayList<Integer> outgoing = new ArrayList<Integer>();
    ArrayList<Integer> incoming = new ArrayList<Integer>();
    
    /**
     * Constructor for objects of class Reader. Creates a scanner object reads through the 8 lines of the configuration file and 
     * stores the information on each line in an array list for each type of space. This information will be accessed by the board 
     * class.
     */
    public Reader(File config)
    {  
        
        try{
            //Reads through the file, mining the data needed to create the game. Reades for 5 lines
            //File config = new File(configg);
            Scanner scan = new Scanner(config);
            int counter = 0;
            
            while(counter<8){
                String current = scan.next();
                //Hold
                if(current.equals("Hold")){
                    holdList.add(scan.nextInt());
                    holdList.add(scan.nextInt());
                    holdList.add(scan.nextInt());
                    counter++;
                    if(counter<7){
                        scan.nextLine();
                    }
                }
                
                //Hold Q
                
                if(current.equals("HoldQ")){
                    holdQList.add(scan.nextInt());
                    holdQList.add(scan.nextInt());
                    holdQList.add(scan.nextInt());
                    counter++;
                    if(counter<7){
                        scan.nextLine();
                    }
                }
                
                //Treasure Pot A
                
                if(current.equals("treasurePotA")){
                    treasurePotAList.add(scan.nextInt());
                    treasurePotAList.add(scan.nextInt());
                    treasurePotAList.add(scan.nextInt());
                    counter++;
                    if(counter<7){
                        scan.nextLine();
                    }
                }
                
                 //PriorityHold
                
                if(current.equals("PriorityHold")){
                    priorityHoldList.add(scan.nextInt());
                    priorityHoldList.add(scan.nextInt());
                    priorityHoldList.add(scan.nextInt());
                    counter++;
                    if(counter<7){
                        scan.nextLine();
                    }
                }
                //TreasurePotB
                
                if(current.equals("treasurePotB")){
                    treasurePotBList.add(scan.nextInt());
                    treasurePotBList.add(scan.nextInt());
                    counter++;
                    if(counter<7){
                        scan.nextLine();
                    }
                }
                
                //JStack
                if(current.equals("JStack")){
                    jStackList.add(scan.nextInt());
                    counter++;
                    if(counter<7){
                        scan.nextLine();
                    }
                }
                
                //Scoring
                if(current.equals("values")){
                    Integer temp = scan.nextInt();
                    //scan.nextInt();
                    //Random random = new Random();
                    //Double temp = 100*random.nextGaussian() + 625;
                    scoring.add(temp.doubleValue());
                    scoring.add(scan.nextDouble());
                    counter++;
                    if(counter<7){
                        scan.nextLine();
                    }
                }
                
                //Board
                if(current.equals("board")){
                    board.add(scan.nextInt());
                    board.add(scan.nextInt());
                    board.add(scan.nextInt());
                    counter++;
                    if(counter<7){
                        scan.nextLine();
                    }
                }
               
            }
            Integer remaining = 100 - (treasurePotAList.get(0)+treasurePotBList.get(0)+holdList.get(0)+holdQList.get(0)+jStackList.get(0)+priorityHoldList.get(0));
            emptyList.add(remaining);
        }
        catch(Exception e){
            System.out.println("Something went wrong with the reader.");
        }
    }
    
    /**
     * getHoldQArray - returns the holdQ array storing the infromation from the configuration file. This includes 
     * the probability a space is of the type HoldQ, as well as the range of the multiplier for the HoldQ.
     * 
     * @return ArrayList<Integer> - first element is the probability, the second is the lower bound of the multiplier, the third 
     * is the upper bound of the multiplier
     */
    public ArrayList<Integer> getHoldQArray(){
        return holdQList;
    }

    /**
     * getHoldArray - returns the hold array storing the infromation from the configuration file. This includes 
     * the probability a space is of the type Hold, as well as the range of the multiplier for the Hold.
     * 
     * @return ArrayList<Integer> - first element is the probability, the second is the upper bound of the multiplier, the third 
     * is the lower bound of the multiplier
     */    
    public ArrayList<Integer> getHoldArray(){
        return holdList;
    }
    
    /**
     * getPriorityHoldArray - returns the priority hold array storing the infromation from the configuration file. This includes 
     * the probability a space is of the type PriorityHold, as well as the range of the multiplier for the PriorityHold.
     * 
     * @return ArrayList<Integer> - first element is the probability, the second is the lower bound of the multiplier, the third 
     * is the upper bound of the multiplier
     */    
    public ArrayList<Integer> getPriorityHoldArray(){
        return priorityHoldList;
    }
    
    /**
     * getTreasurePotAArray - returns the TreasurePotA array storing the infromation from the configuration file. This includes 
     * the probability a space is of the type TreasurePotA, as well as the number of treasure pieces to remove and the number of 
     * removals.
     * 
     * @return ArrayList<Integer> - first element is the probability, the second is the number of peices to remove, the third 
     * is the number of removals
     */    
    public ArrayList<Integer> getTreasurePotAArray(){
        return treasurePotAList;
    }
    
    /**
     * getTreasurePotBArray - returns the TreasurePotB array storing the infromation from the configuration file. This includes 
     * the probability a space is of the type TreasurePotA, as well as the number of treasure pieces     
     * 
     * @return ArrayList<Integer> - first element is the probability, the second is the number of peices 
     */        
    public ArrayList<Integer> getTreasurePotBArray(){
        return treasurePotBList;
    }
    
    /**
     * getEmptyArray - returns the Empty array storing the infromation from the configuration file. This includes 
     * the probability a space is of the type Empty
     * 
     * @return ArrayList<Integer> - first element is the probability
     */    
    public ArrayList<Integer> getEmptyArray(){
        return emptyList;
    }
    
    /**
     * getJStackArray - returns the JStack array storing the infromation from the configuration file. This includes 
     * the probability a space is of the type JStack
     * 
     * @return ArrayList<Integer> - first element is the probability
     */    
    public ArrayList<Integer> getJStackArray(){
        return jStackList;
    }
    
    /**
     * getBoardDimensions - returns the JStack array storing the infromation from the configuration file. This includes 
     * each dimension of the board
     * 
     * @return ArrayList<Integer> - first element the first dimension, second is the second dimension, etc
     */    
    public ArrayList<Integer> getBoardDimensions(){
        return board;
    }
    
    /**
     * getScoring - returns the Scoring array storing the infromation from the configuration file. This includes 
     * the value to finish and the value of each treasure pot
     * 
     * @return ArrayList<Double> - first element value added after winning, second is the value per treasure piece, etc
     */    
    public ArrayList<Double> getScoring(){
        return scoring;
    }
}
