public class Main {
    public static void main(String []args) {
        SignCryption aliceSide = new SignCryption();
        // System.out.println(t.hello());

        System.out.println(aliceSide.getP());

        SignCryption bobSide = new SignCryption(aliceSide.getQ(), aliceSide.getG());

        aliceSide.setYo(bobSide.getY());

        bobSide.setYo(aliceSide.getY());

        aliceSide.calculateSharedSec();

        bobSide.calculateSharedSec();

        aliceSide.makeK();

        bobSide.makeK();
    }
}