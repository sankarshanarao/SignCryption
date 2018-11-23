import java.io.*;
import java.math.*;
import java.util.LinkedList;

public class SignCryption {
    private static BigInteger p = BigInteger.ONE, q, g;
    
    public SignCryption() {
        if (!p.equals(BigInteger.ONE)) {
            p = new BigInteger("40347080716349062843759024331624072226452533649359"); // 50digit long prime number
        }
    }

    public static void main() {
        System.out.println("Hello World!");
    }
}