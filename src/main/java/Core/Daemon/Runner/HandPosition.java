package Core.Daemon.Runner;

import com.leapmotion.leap.Hand;

public class HandPosition extends ControllerRunner{
    @Override
    public void run() {
        System.out.println("Starting Daemon");
        while (true) {
            Hand hand = getController().frame().hands().frontmost();
            System.out.println(hand.isLeft() ? "Left hand" : "Right hand");
            if (hand.palmPosition().getX() > 0) {
                System.out.println("La main est à droite du leap motion");
            } else {
                System.out.println("La main est à gauche du leap motion");
            }
            if (hand.palmPosition().getZ() > 0) {
                System.out.println("La main est entre vous et le leap motion");
            } else {
                System.out.println("La main est entre l'écran est le Leap motion");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
