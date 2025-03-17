package gaussian_bars;

import java.util.Random;

public class GaussGenerator {
    static Random gen = new Random();
    private final static double MEAN = 50.0;
    private final static double STDDEV = 25.0;

    public static int getGaussInt() {
        double rand_double = gen.nextGaussian();
        double modified_value = Math.floor((rand_double * STDDEV) + MEAN);
        int val = (int) modified_value;
        return val;
    }
}
