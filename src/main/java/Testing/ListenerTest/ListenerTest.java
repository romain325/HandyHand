package Testing.ListenerTest;

import Core.Daemon.CallLoop;
import Core.Daemon.Daemon;
import Core.Interaction.Interaction;
import Core.Listener.MainListener;
import Core.Listener.PositionListener;
import Core.Position.Position;
import Core.Script.Script;
import Testing.Tester;

import java.util.Arrays;
import java.util.HashSet;

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
        interaction.addListener(new MainListener[]{listener}, new Script("Shit", new String[]{} ,"Another Piece of shit"));
        Daemon daemon = new Daemon("Positionlistener", new CallLoop(interaction));
        daemon.start();
    }
}
