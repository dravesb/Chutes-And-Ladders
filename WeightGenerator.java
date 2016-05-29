import java.io.*;
import java.util.*;
/**
 * Reads a graph and generates random weights for the edges up to a maximum specified by a command line parameter
 *
 * Created: 04/14/2016
 * Author: C.W. Liew
 *
 */
public class WeightGenerator {
    int maxWeight;
    String fName;
    File inFile;
    FileWriter outFile;
    RandomWeightGenerator wgen;

    /**
     * constructor - empty for now
     */
    public WeightGenerator() {}

    /**
     * regenerate the graph with new weights
     */
    public void regenGraph() {
        openSourceFile();
        createDestFile();
        rewriteGraph();
    }

    /**
     * open the input file, checking to make sure it exists
     */
    public void openSourceFile() {
        inFile = new File( fName );
        if ( !inFile.exists() ) {
            System.err.println( "Input file does not exist" );
            System.exit( -1 );
        }
    }

    /**
     * create the output file (if necessary)
     */
    public void createDestFile() {
        try {
            outFile = new FileWriter( new File( "new-" + fName ));
        }
        catch ( Exception e ) {
            System.err.println( "Could not create output file" );
            System.exit( -1 );
        }
    }

    /**
     * now rewrite the graph
     */
    public void rewriteGraph() {
        wgen = new RandomWeightGenerator( maxWeight );
        Scanner scanner;
        Scanner scanL;

        try {
            scanner = new Scanner(inFile);
            while (scanner.hasNextLine()) {
                scanL = new Scanner(scanner.nextLine());
                genNextLine(scanL);
            }
            outFile.close();
        } catch( Exception e ) {
            System.err.println("Error in processing file");
        }
    }

    /**
     * generate the next line of the graph description
     * @param scanL - scanne for the line
     */
    public void genNextLine( Scanner scanL ) {
        int weight = wgen.genNextWeight();

        String source = scanL.next();
        scanL.next();
        String dest = scanL.next();

        try {
            outFile.write(source + " - " + dest + " " + ( new Integer( weight ).toString()) + "\n" );
        } catch( Exception e ) {
            System.err.println( "Error in output" );
        }

    }

    public void transformFile(String fileName, Integer maxWeight){
        fName = fileName;
        maxWeight = maxWeight;
        regenGraph();
    }

    /**
     * main program
     * @param args - input filename, max weight
     */
    public static void main( String args[] ) {
        WeightGenerator gen = new WeightGenerator();

        if ( args.length != 2 ) {
            System.out.println( "Usage: java WeightGenerator <filename> <max weight>" );
            System.exit( 0 );
        }

        gen.fName = args[ 0 ];
        gen.maxWeight = Integer.parseInt( args[ 1 ] );
        gen.regenGraph();
    }

    class Node {
        String label;
        int incoming;
        int outgoing;

        public Node() {
            incoming = 0;
            outgoing = 0;
        }
    }
}
