package mate.academy.util;

import java.util.Random;

public class RandomGenerator {
    public static String generateCode() {
        Random random = new Random();
        int code = random.nextInt(9000) + 1000;
        return String.valueOf(code);
    }
}
