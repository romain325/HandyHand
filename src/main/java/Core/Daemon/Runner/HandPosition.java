package Core.Daemon.Runner;

import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;

public class HandPosition extends ControllerRunner{

    @Override
    public void run() {
        System.out.println("Starting Daemon");
        while (true) {

            /*
            HandList hands = getController().frame().hands();
            switch (hands.count()) {
                case 0 -> System.out.println("No hands recognized");
                case 1 -> {
                    Hand hand = hands.get(0);
                    System.out.println(hand.isLeft() ? "Left hand :" : "Right hand :");
                    if (hand.palmPosition().getX() > 0) {
                        System.out.println("On the right of the Leap Motion");
                    } else {
                        System.out.println("On the left of the Leap Motion");
                    }
                    if (hand.palmPosition().getZ() > 0) {
                        System.out.println("Between you and the Leap Motion");
                    } else {
                        System.out.println("Between the screen and you");
                    }
                }
                case 2 -> {
                    Hand firstHand = hands.get(0);
                    Hand lastHand = hands.get(1);
                    if (firstHand.isLeft()) {
                        System.out.println("Left Hand :");
                        if (firstHand.palmPosition().getX() > 0) {
                            System.out.println("On the right of the Leap Motion");
                        } else {
                            System.out.println("On the left of the Leap Motion");
                        }
                        if (firstHand.palmPosition().getZ() > 0) {
                            System.out.println("Between you and the Leap Motion");
                        } else {
                            System.out.println("Between the screen and you");
                        }
                    } else {
                        System.out.println("Right Hand :");
                        if (firstHand.palmPosition().getX() > 0) {
                            System.out.println("On the right of the Leap Motion");
                        } else {
                            System.out.println("On the left of the Leap Motion");
                        }
                        if (firstHand.palmPosition().getZ() > 0) {
                            System.out.println("Between you and the Leap Motion");
                        } else {
                            System.out.println("Between the screen and you");
                        }
                    }
                    if (lastHand.isRight()) {
                        System.out.println("Right Hand :");
                        if (lastHand.palmPosition().getX() > 0) {
                            System.out.println("On the right of the Leap Motion");
                        } else {
                            System.out.println("On the left of the Leap Motion");
                        }
                        if (lastHand.palmPosition().getZ() > 0) {
                            System.out.println("Between you and the Leap Motion");
                        } else {
                            System.out.println("Between the screen and you");
                        }
                    } else {
                        System.out.println("Left Hand :");
                        if (lastHand.palmPosition().getX() > 0) {
                            System.out.println("On the right of the Leap Motion");
                        } else {
                            System.out.println("On the left of the Leap Motion");
                        }
                        if (lastHand.palmPosition().getZ() > 0) {
                            System.out.println("Between you and the Leap Motion");
                        } else {
                            System.out.println("Between the screen and you");
                        }
                    }
                }
            }*/
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
