package Core.Daemon;


public class Daemon extends Thread{
    private final String daemonName;

    public Daemon(String name, Runnable runner) {
        super(runner);
        daemonName = name;
        System.out.println("Creating " + daemonName);
        setDaemon(true);
    }

    public String getDaemonName(){
        return daemonName;
    }
}
