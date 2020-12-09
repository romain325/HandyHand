package Core.Script;

import processing.data.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.prefs.Preferences;

public class Script implements Runnable {
    private static final int EXEC_TIME_COOLDOWN = 3000;
    private static String userOS = System.getProperty("os.name");
    private final String execPath;
    private final String[] args;
    private final String file;

    public String getExecPath() {
        return execPath;
    }

    public String[] getArgs() {
        return args;
    }

    public String getFile() {
        return file;
    }

    public Script(String execPath, String[] args, String file) {
        this.execPath = execPath;
        this.args = args;
        this.file = file;
    }

    @Override
    public void run() {
        try {
            // if you want to have access to a python script on Windows you have to specify the Path to your python .exe
            Process process = new ProcessBuilder(this.execPath,this.file).start();
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
            // TODO execption ?
        }
    }


}
