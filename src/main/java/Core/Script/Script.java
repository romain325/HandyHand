package Core.Script;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Script implements Runnable {
    private static final int EXEC_TIME_COOLDOWN = 3000;
    private static String userOS = System.getProperty("os.name");
    private final String execType;
    private final String[] args;
    private final String file;

    public String getExecType() {
        return execType;
    }

    public String[] getArgs() {
        return args;
    }

    public String getFile() {
        return file;
    }

    public Script(String execPath, String[] args, String file) {
        this.execType = execPath;
        this.args = args;
        this.file = file;
    }

    @Override
    public void run() {
        try {

            Process process = new ProcessBuilder(this.execType, this.file, String.join(" ", this.args)).start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ( (line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            System.out.println(builder.toString());

            Thread.sleep(EXEC_TIME_COOLDOWN);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            // TODO exception ?
        }
    }


}
