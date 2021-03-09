package Core.Daemon.Runner;

import Core.Position.HandPosition;

/**
 * A class Runnable that show some information about the position of hands returned by LeapMotion
 */
public class HandPositionRunner extends ControllerRunner{
    /**
     * The instance that will calculate the positions of hands
     */
    HandPosition handPosition = new HandPosition();

    /**
     * The instance of StringBuilder that will be used in the class
     */
    StringBuilder str = new StringBuilder();

    /**
     * The method that override the interface Runnable
     * It prints the position of the current hand above the LeapMotion
     */
    @Override
    public void run() {
        System.out.println("Starting Daemon");
        while (true) {
            var value = handPosition.getHandsPosition(getController().frame());
            for (var val: value.keySet()) {
                for (var test: value.get(val)) {
                    str.append(test).append("\t");
                }
                System.out.println(val + " Hand: " + str.toString());
                str.setLength(0);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
