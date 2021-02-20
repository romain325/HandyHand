package Core.Script;

import org.springframework.data.annotation.Id;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;

public class Script implements Runnable {
    @Id
    private String id;
    private static final int EXEC_TIME_COOLDOWN = 3000;
    private static String userOS = System.getProperty("os.name");
    private  String execType;
    private  String[] args;
    private  String file;
    private  String description;

    public String getDescription() { return description; }

    public String getExecType() {
        return execType;
    }

    public String[] getArgs() {
        return args;
    }

    public String getFile() {
        return file;
    }

    public void setExecType(String type){execType=type;}

    public void setArgs(String[] args) { this.args = args; }

    public void setFile(String file) { this.file = file; }

    public void setDescription(String description) { this.description = description; }

    public void setId(String id) { this.id = id; }

    public Script(String execPath, String[] args, String file) {
        this(execPath,args,file,"");
    }

    public Script(String execPath, String[] args, String file, String description) {
        this.execType = execPath;
        this.args = args;
        this.file = file;
        this.description = description;
        id = getId();
    }
    public Script(){}

    @Override
    public void run() {
        try {

            Process process = new ProcessBuilder(this.execType, this.file, String.join(" ", this.args)).start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ( (line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            System.out.println(builder.toString());

            Thread.sleep(EXEC_TIME_COOLDOWN);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return new String(Base64.getEncoder().encode((execType + "_" + file).toLowerCase().getBytes()));
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == Script.class && ((Script) obj).getId().equals(this.getId());
    }



}
