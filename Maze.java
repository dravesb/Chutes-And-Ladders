import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * DirectedGraph - a class that stores a directed, weighted graph with positive, integer edge weights 
 * with node values of type integer.
 * 
 * @author Benjamin Draves
 * @version April 27, 2016
 */
public class Maze extends Space
{
    private ArrayList<DirectedGraphNode> nodes;
    private int numberOfNodes;
    
    /**
     * DirectedGraph - a constructure for the DirectedGraph class. Sets number of nodes to zero.
     */
    public Maze(){
        nodes = new ArrayList<DirectedGraphNode>();
        numberOfNodes = 0;
    }

    /**
     * addNode - a method that adds a node to the graph.
     * @param k - the key value of the node
     * @return boolean - if the node was added successfully 
     */
    public boolean addNode(Integer k){
        for(int i =0; i<numberOfNodes; i++){
            if(k == nodes.get(i).getKey()){
                return false;
            }
        }
        nodes.add(new DirectedGraphNode(k));
        numberOfNodes++;
        return true;
    }

    /**
     * addEdge - a method that adds an edge between two nodes in the graph
     * @param k1 - the first node (the edge outgoing from this node)
     * @param k2 - the second node (the edge incoming to this node)
     * @param e - the weight of the edge
     * 
     */
    public boolean addEdge(Integer k1, Integer k2, Integer e){
        
        if(e.doubleValue()< 0){
            System.out.println("No negative weights allowed");
            return false;
        }
        
        for(int i = 0; i<numberOfNodes; i++){
            if(k1 == nodes.get(i).getKey()){
                for(int j = 0; j<numberOfNodes; j++){
                    if(k2 == nodes.get(j).getKey()){
                        Edge edge = new Edge(nodes.get(i), nodes.get(j), e);
                        nodes.get(i).addOutgoing(edge);
                        nodes.get(j).addIncoming(edge);
                        return true;
                    }         
                }
            }
        }
        System.out.println("One of these nodes do not exist.");
        return false;
    }
    
    /**
     * toString  - a method that prints all key values found in this graph
     * @return String - the string describing the graph
     */
    public String toString(){
        String s = "The nodes in this graph are:"; 
        for(int i = 0; i<numberOfNodes; i++){
            s = s+" "+nodes.get(i).getKey();
        }
        return s;
    }
    
    /**
     * getNumberOfNodes - a method that returns the number of nodes in the graph 
     * @return int - the number of nodes
     */
    public int getNumberOfNodes(){
        return numberOfNodes;
    }
    
    /**
     * getNodes - a method that returns the nodes in this graph
     * @return ArrayList<DirectedGraphNode<K,E>> - a list of the nodes in this graph
     */
    public ArrayList<DirectedGraphNode> getNodes(){
        return nodes; 
    }
    
    /**
     * getExits - a method that returns a list of all key values of the exits of the graph 
     * @return ArrayList<Integer> - the exits
     */
    public ArrayList<Integer> getExits(){
        ArrayList<Integer> exits = new ArrayList<Integer>();
        for(int i = 0; i<nodes.size(); i++){
            if(nodes.get(i).getOutgoing().size()==0){
                exits.add(nodes.get(i).getKey());
            }
        }
        return exits;
    }
    
    /**
     * getEntrances - a method that returns a list of all key values of the entrances of the graph 
     * @return ArrayList<Integer> - the entrances
     */
    public ArrayList<Integer> getEntrances(){
        ArrayList<Integer> entrances = new ArrayList<Integer>();
        for(int i = 0; i<nodes.size(); i++){
            if(nodes.get(i).getIncoming().size()==0){
                entrances.add(nodes.get(i).getKey());
            }
        }
        return entrances;
    }
    
    /**
     * getIncoming - a method that returns a list of all key values of the nodes associated with incoming edges to the node with key k
     * @param k - the key value of the node of interest
     * @return ArrayList<Integer> - the incoming edges
     */
    public ArrayList<Integer> getIncoming(Integer k){
        ArrayList<Integer> incoming = new ArrayList<Integer>();
        for(int i = 0; i<nodes.size(); i++){
            if(nodes.get(i).getKey()==k){
                for(int j = 0; j<nodes.get(i).getIncoming().size(); j++){
                    incoming.add(nodes.get(i).getIncoming().get(j).getStart().getKey());
                }
                break;
            }
        }
        return incoming;
    }
    
    /**
     * getOutgoing - a method that returns a list of all key values of the nodes associated with outgoing edges to the node with key k
     * @param k - the key value of the node of interest
     * @return ArrayList<Integer> - the outgoing nodes
     */
    public ArrayList<Integer> getOutgoing(Integer k){
        ArrayList<Integer> outgoing = new ArrayList<Integer>();
        for(int i = 0; i<nodes.size(); i++){
            if(nodes.get(i).getKey()==k){
                for(int j = 0; j<nodes.get(i).getOutgoing().size(); j++){
                    outgoing.add(nodes.get(i).getOutgoing().get(j).getStop().getKey());
                }
                break;
            }
        }
        return outgoing;
    }
    
    /**
     * getOutgoingWeights - a method that returns a list of all weight values of the edges incident to the node with key k
     * @param k - the key value of the node of interest
     * @return ArrayList<Integer> - the outgoing edge weights
     */
    public ArrayList<Integer> getOutgoingWeights(Integer k){
        ArrayList<Integer> outgoing = new ArrayList<Integer>();
        for(int i = 0; i<nodes.size(); i++){
            if(nodes.get(i).getKey()==k){
                for(int j = 0; j<nodes.get(i).getOutgoing().size(); j++){
                    int newInt = nodes.get(i).getOutgoing().get(j).getWeight().intValue();
                    Integer toAdd = new Integer(newInt);
                    outgoing.add(toAdd);
                }
                break;
            }
        }
        return outgoing;
    }
    
    /**
     * DirectedGraphNode - an inner class for the nodes in the graph.
     *   
     * @author Benjamin Draves
     * @version April 27, 2016
     */
    public class DirectedGraphNode{
        private Integer key;
        private LinkedList<Edge> incoming, outgoing;
        
        /**
         * DirectedGraphNode - a constructor for the Directed Graph node class 
         * @param k - the key value of that node
         */
        public DirectedGraphNode(Integer k){
            key = k;
            incoming = new LinkedList<Edge>();
            outgoing = new LinkedList<Edge>();
        }

        /**
         * addIncoming - adds an incoming edge
         * @param e - the edge to be added
         * @return boolean - if the edge was added successfully
         */
        public boolean addIncoming(Edge e){
            incoming.add(e);
            return true;
        }
        
        /**
         * addOutgoing - adds an outgoing edge
         * @param e - the edge to be added
         * @return boolean - if the edge was added successfully
         */
        public boolean addOutgoing(Edge e){
            outgoing.add(e);
            return true;
        }
        
        /**
         * getOutgoing - a method that returns the list of all outgoing edges
         * @return LinkedList<Edge> - the list of all outgoing edges
         */
        public LinkedList<Edge> getOutgoing(){
            return outgoing;
        }
        
        /**
         * getIncoming - a method that returns the list of all incoming edges
         * @return LinkedList<Edge> - the list of all incoming edges
         */
        public LinkedList<Edge> getIncoming(){
            return incoming;
        }
        
        /**
         * getKey - a method to get the key of the node 
         * @return K - the key valye
         */
        public Integer getKey(){
            return key;
        }
        
        /**
         * toString - a too string that returns the key value as a string 
         * @return String - the key value as a string
         */
        public String toString(){
            return ""+key;
        }
    }
    
    /**
     * Edge - an inner class for the edges in the graph.
     *   
     * @author Benjamin Draves
     * @version April 27, 2016
     */
    public class Edge{
        DirectedGraphNode start, stop;
        Integer weight;
        /**
         * Edge- a constructor for the class Edge 
         * @param - n1, the starting node
         * @param - n2, the ending node 
         * @param e - the weight
         */
        public Edge(DirectedGraphNode n1, DirectedGraphNode n2, Integer e){
            start = n1;
            stop = n2;
            weight = e;
        }
        
        public DirectedGraphNode getStart(){
            return start;
        }
        
        public DirectedGraphNode getStop(){
            return stop;
        }

        /**
         * getWeight - returns the weight of the edge
         * @return Integer - the weight
         */
        public Integer getWeight(){
            return weight; 
        }
    
    }
}
