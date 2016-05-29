import java.util.Random;

/**
 * JStack - a class that holds a multiplier. This space acts as a trampoline. If the player lands on it
 * they are sprung downwards or upwards a certain level of dimensions.
 * 
 * @author Benjamin Draves 
 * @version March 22, 2016
 */
public class JStack extends Space
{
    Integer multiplier;
    
    /**
     * JStack() - a constructor for objects of type JStack. Randomly selects the multiplier to be 1 or -1
     */
    public JStack()
    {
        super();
        type="JStack";
        
        Random random = new Random();
        int temp = random.nextInt(2);
        if(temp==0){
            multiplier = -1;
        }
        else{
            multiplier = 1;
        }
        
    }
    
    /**
     * JStack() - a constructor for objects of type JStack. Randomly selects the multiplier to be 1 or -1
     */
    public JStack(long seed)
    {
        super();
        type="JStack";
        
        Random random = new Random(seed);
        int temp = random.nextInt(2);
        if(temp==0){
            multiplier = -1;
        }
        else{
            multiplier = 1;
        }
        
    }
    
    
    /**
     * toString - a to String summarizing the space
     * @return String - the summary of the space
     */
    public String toString(){
        return "A JStack with a multiplier of "+ multiplier;
    }
    
    /**
     * getMult - a method that retuns this stacks multiplier
     * @return int - the multplier
     */
    public int getMult(){
        return multiplier;
    }
    
}
