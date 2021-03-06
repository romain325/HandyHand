package Core.Script;

import org.springframework.data.annotation.Id;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;

/**
 * Script object
 */
public class Script implements Runnable {
    @Id
    private String id;
    private static final int EXEC_TIME_COOLDOWN = 3000;
    private static String userOS = System.getProperty("os.name");
    private String execType;
    private String[] args;
    private String file;
    private String description;
    private String idGesture;

    /**
     * Script's Description
     * @return description
     */
    public String getDescription() { return description; }

    /**
     * Script's Exectype
     * @return exectype
     */
    public String getExecType() {
        return execType;
    }

    /**
     * script's args
     * @return args
     */
    public String[] getArgs() {
        return args;
    }

    /**
     * script's file
     * @return file content
     */
    public String getFile() {
        return file;
    }

    /**
     * set script's exectype
     * @param type execType
     */
    public void setExecType(String type){execType=type;}

    /**
     * set script's args
     * @param args args
     */
    public void setArgs(String[] args) { this.args = args; }

    /**
     * set script's content
     * @param file script content
     */
    public void setFile(String file) { this.file = file; }

    /**
     * set description
     * @param description description
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * set script id
     * @param id new id
     */
    public void setId(String id) { this.id = id; }

    public Script(String execPath, String[] args, String file) {
        this(execPath,args,file,"","");
    }

    public Script(String execPath, String[] args, String file, String description, String idGesture) {
        this.execType = execPath;
        this.args = args;
        this.file = file;
        this.description = description;
        id = createId();
        this.idGesture= idGesture;
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

    /**
     * Generate id for our script
     * @return generated script id
     */
    public String createId() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return new String(Base64.getEncoder().encode((generatedString).toLowerCase().getBytes()));
    }

    public String getId(){
        return id;
    }


    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == Script.class && ((Script) obj).getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * script's gesture id
     * @return gesture id linked
     */
    public String getIdGesture() {
        return idGesture;
    }

    /**
     * set script's gesture id
     * @param idGesture new gesture id
     */
    public void setIdGesture(String idGesture) {
        this.idGesture = idGesture;
    }

    /**
     * Decode file content
     * @return decoded script
     */
    public String getFileDecoded() {
        return new String(Base64.getDecoder().decode(file));
    }
}
