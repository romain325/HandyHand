package Core.Script;

public class Script implements Runnable{
    private static String userOS;
    private final String path;
    private final String[] args;
    private final String type;

    public String getPath() {
        return path;
    }

    public String[] getArgs() {
        return args;
    }

    public String getType() {
        return type;
    }

    public Script(String path, String[] args, String type) {
        this.path = path;
        this.args = args;
        this.type = type;
    }

    @Override
    public void run() {
        // Do some shit
        // TODO see with @yoperiquoi for scripts
        System.out.println("Finally");
    }
}
