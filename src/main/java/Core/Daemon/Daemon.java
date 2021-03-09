package Core.Daemon;


import Core.Script.Script;

public class Daemon extends Thread implements Comparable{
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == Daemon.class && ((Daemon) obj).getDaemonName().equals(this.getDaemonName());
    }

    @Override
    public int compareTo(Object o) {
        if (o.getClass() != this.getClass()){return 1;}
        return ((Daemon) o).getDaemonName().compareTo(this.getDaemonName());
    }
}
