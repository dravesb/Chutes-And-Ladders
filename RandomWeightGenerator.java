import java.util.*;

public class RandomWeightGenerator {

    Random gen;
    int maxWeight = Integer.MAX_VALUE;

    public RandomWeightGenerator() {
        gen = new Random();
    }

    public RandomWeightGenerator( int maxw ) {
        maxWeight = maxw;
        gen = new Random();
    }

    public RandomWeightGenerator( int seed, int maxw ) {
        maxWeight = maxw;
        gen = new Random( seed );
    }

    public int genNextWeight() {
        return( 1 + gen.nextInt( maxWeight-1 ));
    }
}