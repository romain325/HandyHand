package Testing.ListenerTest;

import Core.Daemon.CallLoop;
import Core.Daemon.Daemon;
import Core.Interaction.Interaction;
import Core.Listener.MainListener;
import Core.Listener.PositionListener;
import Core.Position.Position;
import Core.Script.Script;
import Testing.Tester;

/**
 * Class to test our listener
 */
public class ListenerTest implements Tester {
    @Override
    public void start() {
        listenerTesting();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void listenerTesting() {
        Interaction interaction = new Interaction();
        PositionListener listener = PositionListener.PositionListenerFactory("left", new Position[]{Position.LEFT, Position.FRONT});
        PositionListener listener2 = PositionListener.PositionListenerFactory("right", new Position[]{Position.RIGHT, Position.FRONT});
        interaction.addListener(new MainListener[]{listener}, new Script("/bin/python3", new String[]{} ,"/home/romain/test.py"));
        interaction.addListener(new MainListener[]{listener2}, new Script("/usr/bin/bash", new String[]{"-v"} ,"/home/romain/test.sh"));
        Daemon daemon = new Daemon("Positionlistener", new CallLoop(interaction));
        daemon.start();
    }
}
