package Core.Daemon.Runner;

import Core.Position.HandPosition;

public class HandPositionRunner extends ControllerRunner{
    HandPosition handPosition = new HandPosition();
    StringBuilder str = new StringBuilder();
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
