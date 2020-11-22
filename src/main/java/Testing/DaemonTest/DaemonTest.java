package Testing.DaemonTest;

import Core.Daemon.Daemon;
import Core.Daemon.Runner.HandPosition;
import Testing.Tester;

public class DaemonTest implements Tester {
    @Override
    public void start() {
        DaemonTesting();
    }

    private void DaemonTesting() {
        Daemon daemon = new Daemon("Placement",new HandPosition());
        daemon.start();
        daemon.run();
    }
}
