import java.math.*;
import java.util.*;
import java.util.LinkedList;
import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 

public class SignCryption {
    private BigInteger p, q, g, x, y, yo, shSc;
    private String k,k1,k2;

	// // Constructors // // 

    public SignCryption() {
        p = new BigInteger("62496229288368844351282174568366209836365424367831"); // 50 digit long prime
        System.out.println("Prime setup");

        BigInteger t = p.subtract(BigInteger.ONE);
        LinkedList <BigInteger> l = rhoFactors(t);

        Random randomIndex = new Random();
        
        System.out.println(l.size());

        int indexOfFactor = randomIndex.nextInt(l.size()/2) + l.size()/2;

        System.out.println(indexOfFactor);

        q = l.get(l.size()-1);

        System.out.print(q);
        System.out.println(","+l.size());

        g = q.add(BigInteger.ONE);

        g.add(BigInteger.ONE);

        System.out.println(g);
        System.out.println(q);

        x = q.subtract(BigInteger.ONE);

        y = g.modPow(x, p);

        System.out.println(y);
    }

    public SignCryption(String qs, String gs) {
        p = new BigInteger("62496229288368844351282174568366209836365424367831");
        q = new BigInteger(qs);
        g = new BigInteger(gs);
        System.out.println("p, q, g setup");

		Random numb = new Random();

		x = q.subtract(BigInteger.valueOf(numb.nextInt(10)));

		y = g.modPow(x, p);

    }

	// Getters and Setters

	public String getP() {
        return p.toString();
    }

    public String getQ() {
        return q.toString();
    }

    public String getG() {
        return g.toString();
    }

    public String getY() {
        return y.toString();
    }

	public void setYo(String Yo) {
		yo = new BigInteger(Yo);
	}

	public void calculateSharedSec() {
		shSc = yo.modPow(x, p);

		System.out.print("Sec:");
		System.out.println(shSc);
	}

	// // // // // // // // Logic // // // // // // // //
	public static String getMd5(String input) 
    { 
        try { 
  
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    } 
	
    private static Boolean isSpsp(BigInteger n, BigInteger a) {
		BigInteger two = BigInteger.valueOf(2);
		BigInteger n1 = n.subtract(BigInteger.ONE);
		BigInteger d = n1;
		int s = 0;
		while (d.mod(two).equals(BigInteger.ZERO))
		{
			d = d.divide(two);
		s += 1;
		}
		BigInteger t = a.modPow(d, n);
		if (t.equals(BigInteger.ONE) || t.equals(n1))
		{
			return true;
		}
		while (--s > 0)
		{
			t = t.multiply(t).mod(n);
			if (t.equals(n1))
			{
				return true;
			}
		}
		return false;
	}


    public static Boolean isPrime(BigInteger n) {
        Random r = new Random();
        BigInteger two = BigInteger.valueOf(2);
        BigInteger n3 = n.subtract(BigInteger.valueOf(3));
        BigInteger a;
        int k = 25;
        if (n.compareTo(two) < 0) {
            return false;
        }

        if (n.mod(two).equals(BigInteger.ZERO)) {
            return n.equals(two);
        }
        while (k > 0) {
            a = new BigInteger(n.bitLength(), r).add(two);
            while (a.compareTo(n) >= 0) {
                a = new BigInteger(n.bitLength(), r).add(two);
            }
            if (! isSpsp(n, a)) {
                return false;
            }
            k -= 1;
        }
        return true;
	}

	private static BigInteger rhoFactor(BigInteger n, BigInteger c) {
	
		BigInteger t = BigInteger.valueOf(2);
		BigInteger h = BigInteger.valueOf(2);
		BigInteger d = BigInteger.ONE;
		while (d.equals(BigInteger.ONE))
		{
			t = t.multiply(t).add(c).mod(n);
			h = h.multiply(h).add(c).mod(n);
			h = h.multiply(h).add(c).mod(n);
			d = t.subtract(h).gcd(n);
		}
		if (d.equals(n)) /* cycle */
		{
			return rhoFactor(n, c.add(BigInteger.ONE));
		}
		else if (isPrime(d)) /* success */
		{
			return d;
		}
		else /* found composite factor */
		{
			return rhoFactor(d, c.add(BigInteger.ONE));
		}
	}

	public static LinkedList rhoFactors(BigInteger n) {
		BigInteger f;
		BigInteger two = BigInteger.valueOf(2);
		LinkedList fs = new LinkedList();
		if (n.compareTo(two) < 0)
		{
			return fs;
		}
		while (n.mod(two).equals(BigInteger.ZERO))
		{
			n = n.divide(two);
			fs.add(two);
		}
		if (n.equals(BigInteger.ONE))
		{
			return fs;
		}
		while (! n.isProbablePrime(25))
		{
			f = rhoFactor(n, BigInteger.ONE);
			n = n.divide(f);
			fs.add(f);
		}
		fs.add(n);
		Collections.sort(fs);
		return fs;
	}

	// public static BigInteger getGenerator(BigInteger primeNumber) {
	// 	if (isPrime(primeNumber)==false)
	// 		return -1;

	// 	BigInteger phi = primeNumber-1;

	// 	LinkedList primeFactors = rhoFactors(phi);
	// 	/*Still Working on it*/
	// 	for () {
	// 		Boolean false
	// 	}
	// }

}