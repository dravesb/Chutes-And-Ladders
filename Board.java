import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.util.Scanner;

/**
 * Board Class. Creates a two dimensional array list that represents the board
 * 
 * @author Benjamin Draves
 * @version March 4, 2016
 */
public class Board
{
    private ArrayList<ArrayList<ArrayList<Space>>> boardArray;
    private ArrayList<Integer> dimensions;
    private ArrayList<Space> graphExits;
    private ArrayList<Maze> graphs;
    private Random random;
    private Reader reader; 

    /**
     * Board - the constructor of the board. Processes the mazes and stores them. Builds board as previously.
     * @param graph_configurations - the configuration files for the mazes
     */
    public Board(ArrayList<File> graph_configurations)
    {
        reader = new Reader(Statics.getFile());
        dimensions = reader.getBoardDimensions();
        boardArray = new ArrayList<ArrayList<ArrayList<Space>>>();
        graphs = new ArrayList<Maze>();
        graphExits = new ArrayList<Space>();
        random = new Random();

        //Instantiate the boardArray
        for(int i = 0 ; i<dimensions.get(2); i++){
            //Instantiate inner arrays
            boardArray.add(new ArrayList<>());
            //Loops through the rows
            for(int j = 0 ; j<dimensions.get(1); j++){
                //Instantiate inner arrays
                boardArray.get(i).add(new ArrayList<Space>());
                //Loops through the spaces
                for(int k = 0 ; k<dimensions.get(0); k++){
                    boardArray.get(i).get(j).add(new Empty());
                }
            }
        }

        //Make the mazes 
        for(int i =0; i < graph_configurations.size(); i++){
            try{
                graphs.add(new Maze()); 
                Scanner scan = new Scanner(graph_configurations.get(i));
                while(scan.hasNextLine()){
                    int from = scan.nextInt();
                    scan.next();
                    int to = scan.nextInt();
                    int weight = scan.nextInt();
                    graphs.get(i).addNode(from);
                    graphs.get(i).addNode(to);
                    graphs.get(i).addEdge(from, to, weight);
                    if(scan.hasNextLine()){
                        scan.nextLine();
                    }
                }
            }

            catch(Exception e){
                System.out.println("Scanner went down.");
            }
        }
        
        //Set Entrances and Exits
        
        for(int i =0; i < graph_configurations.size(); i++){
            ArrayList<Integer> from = new ArrayList<Integer>();
            ArrayList<Integer> to = new ArrayList<Integer>();
            ArrayList<Integer> entrances = new ArrayList<Integer>();
            ArrayList<Integer> exits = new ArrayList<Integer>();
            
            
            try{
                Scanner scan = new Scanner(graph_configurations.get(i));
                while(scan.hasNextLine()){
                    int left = scan.nextInt();
                    if(!from.contains(left)){
                        from.add(left);
                    }
                    scan.next();
                    int right = scan.nextInt();
                    if(!to.contains(right)){
                        to.add(right);
                    }
                    
                    if(scan.hasNextLine()){
                        scan.nextLine();
                    }
                }
            }

            catch(Exception e){
                System.out.println("Scanner went down.");
            }
            
            for(int j = 0; j<from.size(); j++){
                if(!to.contains(from.get(j))){
                    entrances.add(from.get(j));
                }
            }
            
            for(int j = 0; j<to.size(); j++){
                if(!from.contains(to.get(j))){
                    exits.add(to.get(j));
                }
            }
            
            //If there are no exits, discard the graph
            if(exits.size()==0){
                continue;
            }
            setEntrances(entrances, i);
            setExits(exits, i);
            
        }

        //Loops through the "levels" dimension
        for(int i = 0 ; i<dimensions.get(2); i++){
            //Loops through the rows
            for(int j = 0 ; j<dimensions.get(1); j++){
                //Instantiate inner arrays
                //Loops through the spaces
                for(int k = 1 ; k<dimensions.get(0); k++){
                    //The last row is empty
                    if(i == dimensions.get(2)-1 &&  j== dimensions.get(1)-1 || boardArray.get(i).get(j).get(k).getType().equals("Entrance") || boardArray.get(i).get(j).get(k).getType().equals("TreasurePotC")){
                        continue;
                    }

                    else{
                        int categorize = random.nextInt(100);
                        int lower = 0;
                        int upper = reader.getTreasurePotAArray().get(0);
                        if(lower <= categorize && categorize<upper){
                            boardArray.get(i).get(j).set(k,new TreasurePotA());
                        }

                        lower = upper;
                        upper = upper + reader.getTreasurePotBArray().get(0);
                        if(lower <= categorize && categorize<upper){
                            boardArray.get(i).get(j).set(k,new TreasurePotB());
                        }

                        lower = upper;
                        upper = upper + reader.getHoldArray().get(0);
                        if(lower <= categorize && categorize<upper){
                            boardArray.get(i).get(j).set(k,new Hold());
                        }

                        lower = upper;
                        upper = upper + reader.getHoldQArray().get(0);
                        if(lower <= categorize && categorize<upper){
                            boardArray.get(i).get(j).set(k,new HoldQ());
                        }

                        lower = upper;
                        upper = upper + reader.getPriorityHoldArray().get(0);
                        if(lower <= categorize && categorize<upper){
                            boardArray.get(i).get(j).set(k,new PriorityHold());
                        }

                        lower = upper;
                        upper = upper + reader.getJStackArray().get(0);
                        if(lower <= categorize && categorize<upper){
                            boardArray.get(i).get(j).set(k,new JStack());
                        }

                        lower = upper;
                        upper = upper + reader.getEmptyArray().get(0);
                        if(lower <= categorize && categorize<upper){
                            boardArray.get(i).get(j).set(k,new Empty());
                        }

                    }

                }
            }
        }
        
        
        
    }    

    /**
     * Board - the constructor of the board. Processes the mazes and stores them. Builds board as previously.
     * @param graph_configurations - the configuration files for the mazes
     * @param seed  - the seed for the random number generator
     */
    public Board(ArrayList<File> graph_configurations, long seed)
    {
        reader = new Reader(Statics.getFile());
        dimensions = reader.getBoardDimensions();
        boardArray = new ArrayList<ArrayList<ArrayList<Space>>>();
        graphs = new ArrayList<Maze>();
        graphExits = new ArrayList<Space>();
        random = new Random(seed);

        //Instantiate the boardArray
        for(int i = 0 ; i<dimensions.get(2); i++){
            //Instantiate inner arrays
            boardArray.add(new ArrayList<>());
            //Loops through the rows
            for(int j = 0 ; j<dimensions.get(1); j++){
                //Instantiate inner arrays
                boardArray.get(i).add(new ArrayList<Space>());
                //Loops through the spaces
                for(int k = 0 ; k<dimensions.get(0); k++){
                    boardArray.get(i).get(j).add(new Empty());
                }
            }
        }

        //Make the mazes 
        for(int i =0; i < graph_configurations.size(); i++){
            try{
                graphs.add(new Maze()); 
                Scanner scan = new Scanner(graph_configurations.get(i));
                while(scan.hasNextLine()){
                    int from = scan.nextInt();
                    scan.next();
                    int to = scan.nextInt();
                    int weight = scan.nextInt();
                    graphs.get(i).addNode(from);
                    graphs.get(i).addNode(to);
                    graphs.get(i).addEdge(from, to, weight);
                    if(scan.hasNextLine()){
                        scan.nextLine();
                    }
                }
            }

            catch(Exception e){
                System.out.println("Scanner went down.");
            }
        }
        
        //Set Entrances and Exits
        
        for(int i =0; i < graph_configurations.size(); i++){
            ArrayList<Integer> from = new ArrayList<Integer>();
            ArrayList<Integer> to = new ArrayList<Integer>();
            ArrayList<Integer> entrances = new ArrayList<Integer>();
            ArrayList<Integer> exits = new ArrayList<Integer>();
            
            
            try{
                Scanner scan = new Scanner(graph_configurations.get(i));
                while(scan.hasNextLine()){
                    int left = scan.nextInt();
                    if(!from.contains(left)){
                        from.add(left);
                    }
                    scan.next();
                    int right = scan.nextInt();
                    if(!to.contains(right)){
                        to.add(right);
                    }
                    
                    if(scan.hasNextLine()){
                        scan.nextLine();
                    }
                }
            }

            catch(Exception e){
                System.out.println("Scanner went down.");
            }
            
            for(int j = 0; j<from.size(); j++){
                if(!to.contains(from.get(j))){
                    entrances.add(from.get(j));
                }
            }
            
            for(int j = 0; j<to.size(); j++){
                if(!from.contains(to.get(j))){
                    exits.add(to.get(j));
                }
            }
            
            setEntrances(entrances, i);
            setExits(exits, i);
            
        }

        //Loops through the "levels" dimension
        for(int i = 0 ; i<dimensions.get(2); i++){
            //Loops through the rows
            for(int j = 0 ; j<dimensions.get(1); j++){
                //Instantiate inner arrays
                //Loops through the spaces
                for(int k = 1 ; k<dimensions.get(0); k++){
                    //The last row is empty
                    if(i == dimensions.get(2)-1 &&  j== dimensions.get(1)-1 || boardArray.get(i).get(j).get(k).getType().equals("Entrance") || boardArray.get(i).get(j).get(k).getType().equals("TreasurePotC")){
                        continue;
                    }

                    else{
                        int categorize = random.nextInt(100);
                        int lower = 0;
                        int upper = reader.getTreasurePotAArray().get(0);
                        if(lower <= categorize && categorize<upper){
                            boardArray.get(i).get(j).set(k,new TreasurePotA());
                        }

                        lower = upper;
                        upper = upper + reader.getTreasurePotBArray().get(0);
                        if(lower <= categorize && categorize<upper){
                            boardArray.get(i).get(j).set(k,new TreasurePotB());
                        }

                        lower = upper;
                        upper = upper + reader.getHoldArray().get(0);
                        if(lower <= categorize && categorize<upper){
                            boardArray.get(i).get(j).set(k,new Hold());
                        }

                        lower = upper;
                        upper = upper + reader.getHoldQArray().get(0);
                        if(lower <= categorize && categorize<upper){
                            boardArray.get(i).get(j).set(k,new HoldQ());
                        }

                        lower = upper;
                        upper = upper + reader.getPriorityHoldArray().get(0);
                        if(lower <= categorize && categorize<upper){
                            boardArray.get(i).get(j).set(k,new PriorityHold());
                        }

                        lower = upper;
                        upper = upper + reader.getJStackArray().get(0);
                        if(lower <= categorize && categorize<upper){
                            boardArray.get(i).get(j).set(k,new JStack());
                        }

                        lower = upper;
                        upper = upper + reader.getEmptyArray().get(0);
                        if(lower <= categorize && categorize<upper){
                            boardArray.get(i).get(j).set(k,new Empty());
                        }

                    }

                }
            }
        }
        
        
        
    }    

    /**
     * setEntrances - a method that creates and places the Entrance spaces
     * @param entrances - the keys of the entrance
     * @param mazeNumber - the maze number
     */
    private void setEntrances(ArrayList<Integer> entrances, int mazeNumber){
        for(int i = 0; i < entrances.size(); i++){
            boolean created = false;
            while(!created){
                int absoluteDistance = random.nextInt(dimensions.get(0)*dimensions.get(1)*dimensions.get(2) - dimensions.get(0));

                //Division algorithm to find space
                int count = 0;
                while(absoluteDistance>=dimensions.get(0)*dimensions.get(1)){
                    absoluteDistance =  absoluteDistance - dimensions.get(0)*dimensions.get(1);
                    count++;
                }
                int z = count;
                count = 0;
                while(absoluteDistance>=dimensions.get(0)){
                    absoluteDistance =  absoluteDistance - dimensions.get(0);
                    count++;
                }
                int y = count;
                int x = absoluteDistance;
                if(!boardArray.get(z).get(y).get(x).getType().equals("Entrance") || !boardArray.get(z).get(y).get(x).getType().equals("TreasurePotC")){
                    //Fix 

                    boardArray.get(z).get(y).set(x, new Entrance(mazeNumber, entrances.get(i)));
                    created = true;
                }
                
            }
        }
    }
    
    /**
     * setExits - a method that creates and places the Exits spaces
     * @param exits - the keys of the exits
     * @param mazeNumber - the maze number
     */
    private void setExits(ArrayList<Integer> exits, int mazeNumber){
        for(int i = 0; i < exits.size(); i++){
            boolean created = false;
            while(!created){
                int absoluteDistance = random.nextInt(dimensions.get(0)*dimensions.get(1)*dimensions.get(2) - dimensions.get(0));

                //Division algorithm to find space
                int count = 0;
                while(absoluteDistance>=dimensions.get(0)*dimensions.get(1)){
                    absoluteDistance =  absoluteDistance - dimensions.get(0)*dimensions.get(1);
                    count++;
                }
                int z = count;
                count = 0;
                while(absoluteDistance>=dimensions.get(0)){
                    absoluteDistance =  absoluteDistance - dimensions.get(0);
                    count++;
                }
                int y = count;
                int x = absoluteDistance;
                
                if(!boardArray.get(z).get(y).get(x).getType().equals("Entrance") || !boardArray.get(z).get(y).get(x).getType().equals("TreasurePotC")){
                    TreasurePotC newExit = new TreasurePotC(mazeNumber,i, x, y, z);
                    graphExits.add(newExit);
                    boardArray.get(z).get(y).set(x, newExit);
                    created = true;
                }
                
            }
        }
    }

    /**
     * getDimensions - a method that returns the board dimensions
     * @return ArrayList<Integer> - the board dimensions.
     */
    public ArrayList<Integer> getDimensions(){
        return dimensions;
    }
    
    /**
     * getGraphExits - a method that returns the exits of all mazes
     */
    public ArrayList<Space> getGraphExits(){
        return graphExits;
    }
    
    /**
     * getMazes - a method that returns the mazes of this object
     */
    public ArrayList<Maze> getMazes(){
        return graphs;
    }
    
    /**
     * getReader - a method that returns the reader of this object
     */
    public Reader getReader(){
        return reader;
    }

    /**
     * getSpace - a method that returns the space at space (a,b,c)
     * @param a - the x coordinate
     * @param b - the y corridnate 
     * @param b - the z corridnate 
     * @return Space - the space located at (a,b,c)
     */
    public Space getSpace(int a, int b, int c){
        return boardArray.get(c).get(b).get(a);
    }
    
    /**
     *printBoard - a method that builds a string summarizing the board. This will be used for the p operation.
     *@return String - the board summary
     */
    public String printBoard(){
        String s = "";
        for(int i = 0; i<dimensions.get(2); i++){
            for(int j = 0; j<dimensions.get(1); j++){
                for(int k = 0; k<dimensions.get(0); k++){
                    int n = numTokenHere(k,j,i);
                    s = s + " At space (" +k+","+j+","+i+"): "+boardArray.get(i).get(j).get(k).toString()+". With "+n+" tokens."+"\n";
                }
            }
        }
        return s;
    }

    /**
     * numTokenHere - a method that reports how many tokens are on a specific space. Used in the printBoard method
     * @param x - the x location
     * @param y - the y location
     * @param z - the z location
     * @return int - the number of tokens on (x,y)
     */
    public int numTokenHere(int x, int y, int z){
        int num=0;
        for(int i = 0; i<Statics.getPlayerHolder().getNumOfPlayers();i++){
            for(int j = 0; j < Statics.getPlayerHolder().getNumOfTokens(); j++){
                if(Statics.getPlayerHolder().getPlayer(i+1).getTokens().get(j).getX() == x && Statics.getPlayerHolder().getPlayer(i+1).getTokens().get(j).getY()==y && Statics.getPlayerHolder().getPlayer(i+1).getTokens().get(j).getZ()==z){
                    num++;
                }
            }
        }
        return num;
    }
    
    
}
