package Testing.DaemonTest;

import Core.Daemon.Daemon;
import Core.Daemon.Runner.ControllerRunner;
import Core.Daemon.Runner.HandPosition;
import Testing.Tester;

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
        Daemon daemon = new Daemon("Placement", new HandPosition());
        daemon.start();
    }
}
