package Testing.DaemonTest;

import Core.Daemon.Daemon;
import Core.Daemon.Runner.HandPositionRunner;
import Testing.Tester;

/**
 * Test our daemon
 */
public class DaemonTest implements Tester {
    @Override
    public void start() {
        daemonTesting();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void daemonTesting() {
        Daemon daemon = new Daemon("Placement", new HandPositionRunner());
        daemon.start();
    }
}
