package Testing;

import Core.Listener.GestureListener;
import Testing.GestureTest.GestureTest;
import Testing.GestureTest.NormalisationTest;
import Testing.ListenerTest.ListenerTest;
import Testing.StubTest.StubTesting;
import Testing.VisualTest.CameraTest.CameraViewTest;
import Testing.VisualTest.SkeletonTest.SkeletonTest;

public class MainTest {
    public static void main(String[] args) {
//         new SkeletonTest().start();
        // new CameraViewTest().start();
        // new DaemonTest().start();
        // new ListenerTest().start();
        // new GestureTest().start();
//        new StubTesting().start();
        new NormalisationTest().start();
    }
}


