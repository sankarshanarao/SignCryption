public class Main {
    public static void main(String []args) {
        SignCryption aliceSide = new SignCryption();
        // System.out.println(t.hello());

        System.out.println(aliceSide.getP());

        SignCryption bobSide = new SignCryption(aliceSide.getQ(), aliceSide.getG());
    }
}