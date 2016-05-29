import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * ChutesAndLadders Class. The class that runs the game. The main method calls the run method that interacts with the user in the terminal. From here the 
 * game can be played round by round. The testPlay method allows for rapid game play to export data.
 * 
 * @author Benjamin Draves
 * @version March 5, 2016
 */
public class ChutesAndLadders
{
    public static ArrayList<Integer> results;
    public static Player winner, finisher;
    private int roundTotal;
    public static boolean isWinner;
    public Statics game;
    /**
     * main - the main method to play the game (run through the terminal)
     * @param String args[] - the arguments to be passed to the constructor. agrs[0] - the maximum value returned by the die, args[1] -the number of players,
     * args[2]- the number of tokens per player, args[3] - the configuration file
     */
    public static void main(String args[]){
        try{
            ChutesAndLadders cal = new ChutesAndLadders(args);   
            cal.run();
        }
        catch(Exception e){
            System.out.println("An exception occured.");
        }

    }

    /**
     * Constructor for objects of class ChutesAndLadders. Parses the strings to integers and creates a file object. These are passed to the statics constructor.
     *isWinner, roundTotal, and results are all instantiated. 
     *
     */
    public ChutesAndLadders(String args[])
    {
        WeightGenerator gen = new WeightGenerator();
        String a = args[0];
        String b = args[1];
        String c = args[2];
        String d = args[3];
        ArrayList<File> graph_configuration = new ArrayList<File>();

        //The highest number for a dice to roll, the number of players, the number of tokens per player
        int n,k,j;
        try{
            //Regenerate the graphs with appropriate edge weights
            gen.maxWeight = Integer.parseInt(a);
            for(int i = 4; i<args.length; i++){
                gen.transformFile(args[i], Integer.parseInt(a));
                graph_configuration.add(new File("new-"+args[i]));
            }
            
            if(Integer.parseInt(a)>1){
                n=Integer.parseInt(a);
            }
            else{
                System.out.println("Please enter an integer greater than 1.");
                IOException e = new IOException();
                throw e;
            }

            if(Integer.parseInt(b)>1){
                k=Integer.parseInt(b);
            }           
            else{
                System.out.println("Please enter an integer greater than 1.");
                IOException e = new IOException();
                throw e;
            }

            if(Integer.parseInt(c)>0){
                j=Integer.parseInt(c);
            }
            else{
                System.out.println("Please enter an integer greater than 0.");
                IOException e = new IOException();
                throw e;
            }
            File configuration = new File(d);
            game = new Statics(n,k,j, configuration, graph_configuration);
        }
        catch(Exception e){
            System.out.println("An exception occured here!");
        }

        winner = null;
        finisher = null;
        isWinner = false;
        results = new ArrayList<Integer>();
        roundTotal = 0;
    }

    /**
     * run - the method that runs the game. The method prints the board, then prompts the user for an operation. The operation is then executed and 
     * the user is prompted again. This continues until a winner emerges. The operations are as follows p - print the board, i - play the remaineder 
     * of the game, r - print a player summary, c - play a single round.
     */
    public void run()
    {
        System.out.println(Statics.getBoard().printBoard());
        while(true){
            String action = updateAction(); 

            if(action.equals("c")){//Play a round
                try{
                    Statics.getPlayerHolder().playRound();
                    roundTotal++;
                    if(isWinner){
                        return;
                    }
                }
                catch(StackOverflowError e){
                    System.out.println(Statics.getPlayerHolder().printPlayerSummary());
                    Statics.getPlayerHolder().calculateWinner();
                    isWinner = true;
                }

                if(isWinner){
                    break;
                }

            }
            else if(action.equals("i")){//Play the remainder of the game
                while(!isWinner){
                    try{
                        Statics.getPlayerHolder().playRound();
                    }
                    catch(StackOverflowError e){
                        System.out.println("\nThe game has concluded with no finisher due to a infinite stack.");
                        System.out.println(Statics.getPlayerHolder().printPlayerSummary());
                        Statics.getPlayerHolder().calculateWinner();
                        isWinner = true;
                    }
                    roundTotal++;

                    if(isWinner){
                        break;
                    }

                    if(roundTotal>100000){
                        System.out.println("The game has concluded with no finisher.");
                        System.out.println(Statics.getPlayerHolder().printPlayerSummary());
                        Statics.getPlayerHolder().calculateWinner();
                        isWinner = true;
                    }

                }

                return;
            }

            else if(action.equals("p")){//Print the board
                System.out.println(Statics.getBoard().printBoard());
            }
            else if(action.equals("r")){//Print the player summary
                System.out.println(Statics.getPlayerHolder().printPlayerSummary());
            }

            else{
                System.out.println("Please select one of c,i,p, or r.");
            }
        }
    }

    /**
     * updateAction - a method that reads in a string from the user to begin the desired operation.
     * @return String - the operation that needs be completed
     */
    public String updateAction(){
        BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
        String action="";
        try{
            System.out.print("What would you like to do? ");
            action = reader.readLine();
        }
        catch(Exception e){
            System.out.println("An exception occured");
        }
        return action;
    }

    /**
     * testPlay - a method that does not prompt the user. This method does not prompt the user and simply plays the game. Returns the results of the game 
     * including the roundTotal, boardDimension, numberOfPlayers, dieNumber, the winning players number.
     * @return ArrayList<Integer> - the results array 
     * 
     */
    public MyResults testPlay(){
        
        while(!isWinner){
            try{
                game.getPlayerHolder().playRound();
            }
            catch(StackOverflowError e){
                Statics.getPlayerHolder().calculateWinner();
                isWinner = true;
            }
            roundTotal++;

            if(isWinner){
                break;
            }

            if(roundTotal>1000000){
                Statics.getPlayerHolder().calculateWinner();
                isWinner = true;
            }

        }
        results.add(Statics.getDice().getHighNumber());
        results.add(Statics.getPlayerHolder().getNumOfPlayers());
        results.add(Statics.getPlayerHolder().getNumOfTokens());
        results.add(roundTotal);
        results.add(Statics.getBoard().getDimensions().get(0));
        results.add(Statics.getBoard().getDimensions().get(1));
        results.add(Statics.getBoard().getDimensions().get(2));
        results.add(winner.getTreasure());
        results.add(winner.getPoints());
        return new MyResults(results, winner.getPersonality());
    }
}
