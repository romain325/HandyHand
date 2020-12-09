package Testing.GestureTest;

import Core.Daemon.CallLoop;
import Core.Daemon.Daemon;
import Core.Gesture.GestureMatcher;
import Core.Gesture.HandPoses.HandType;
import Core.Interaction.Interaction;
import Core.Listener.GestureListener;
import Core.Listener.MainListener;
import Core.Script.Script;
import Testing.Tester;
import com.leapmotion.leap.Controller;

public class GestureTest implements Tester {
    @Override
    public void start() {
        GestureMatcher.init();
        Controller controller = new Controller();
        GestureListener listener = GestureListener.GestureListenerFactory("rock", HandType.LEFT);
        controller.addListener(listener);


        gestureTesting(controller);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        controller.removeListener(listener);

    }

    public void gestureTesting(Controller controller){
        Interaction interaction = new Interaction();
        MainListener listener = GestureListener.GestureListenerFactory("rock");
        interaction.addListener(new MainListener[]{listener}, new Script("/usr/bin/bash", new String[]{} ,"/home/romain/test.sh"));
        Daemon daemon = new Daemon("GestureListener", new CallLoop(interaction));
        daemon.start();
    }
}
