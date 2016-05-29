import java.io.*;
import java.util.ArrayList;

/**
 * OutputData Class. A class that compiles data from several different game plays and writes these results out to a csv file.
 * 
 * @author Benjamin Draves 
 * @version March 5, 2016
 */
public class OutputData
{
    private String comma = ",";
    private String newLine="\n";

    /**
     * main - the main method to call the outputData class
     */
    public static void main(String[] args){
        OutputData output = new OutputData();
        output.run(args);
    }

    /**
     * run - a method that writes all the data out to a final file.
     */
    public void run(String[] args){
        FileWriter writer=null;
        try{
            writer = new FileWriter("project3_experiment2.csv");
            ExperimentController experiment = new ExperimentController();
            theFinalTest(experiment, writer, args);
        }
        catch(Exception e){
            System.out.println("An exception occured.");
        }

        finally{
            try{
                writer.close();
            }
            catch(Exception e){
                System.out.println("An exception occured.");
            }
        }
    }

    /**
     * theFinalTest - a method that runs a set of games and records the data. Several games are played and the data is collected and written out to a file
     * @param experiment - an ExperimentController object that runs the experimetnt 
     * @param write - the FileWrite object that write out to the file
     */
    public void theFinalTest(ExperimentController experiment, FileWriter writer, String[] graphs){

        try{
            writer.append("High Number"+comma+ "Number of Players" + comma +"Number of Tokens"+ comma + "Round Total"+comma + "X"+comma +"Y"+comma +"Z"+comma +"Treasure Total"+comma + 
                "Points Total"+comma + "Personality"+comma + "Time "+newLine); 
        }
        catch(Exception e){
            System.out.println("OH NO.");
        }

        for(int k = 20; k<=20; k = k+2){//tokens
            System.out.println(k);
            for(int j = 5; j<=5; j = j+2){//players
                for(int i = 6; i<=6; i++){//dice
                    for(int w = 0; w<400;w++){
                        System.out.println(w);  
                        try{
                            //Play a game                            
                            String[] args = new String[4+graphs.length];
                            args[0] = Integer.toString(i);
                            args[1] = Integer.toString(j);
                            args[2] = Integer.toString(k);
                            args[3]="config.txt";
                            for(int m = 0; m<graphs.length; m++){
                                args[4+m] = graphs[m];
                            }

                            MyResults exp = experiment.initialTest(args);
                            //Write out the results to a file

                            for(int m  =0; m < exp.results.size(); m++){
                                writer.append(exp.results.get(m)+comma);
                            }
                            
                            writer.append(exp.winner + comma);
                            writer.append(exp.time + newLine);
                        }
                        catch(Exception e){
                            System.out.println("We crashed");
                        }   
                    }
                }
            }
        }
    }

}

