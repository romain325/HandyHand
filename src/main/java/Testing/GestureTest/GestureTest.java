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

        gestureTesting();
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void gestureTesting(){
        Interaction interaction = new Interaction();
        MainListener listener = GestureListener.GestureListenerFactory("rock");
        interaction.addListener(new MainListener[]{listener}, new Script("test.bat", new String[]{} ,""));
        Daemon daemon = new Daemon("GestureListener", new CallLoop(interaction));
        daemon.start();
    }
}
