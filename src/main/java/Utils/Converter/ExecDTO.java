package Utils.Converter;

import java.util.Base64;
import java.util.Map;

/**
 * Data Transfer Object for the executable
 */
public class ExecDTO {
    private final String id;
    private final String name;
    private final String execPath;

    public ExecDTO(Map.Entry<String,String> val) {
        this(new String(Base64.getEncoder().encode(val.getKey().getBytes())), val.getKey(), val.getValue());
    }

    public ExecDTO(String id, String name, String path) {
        this.id = id;
        this.name = name;
        this.execPath = path;
    }

    /**
     * Get exec id
     * @return exec id
     */
    public String getId() {
        return id;
    }

    /**
     * Get exec name
     * @return exec name
     */
    public String getName() {
        return name;
    }

    /**
     * Get exec path
     * @return exec path
     */
    public String getExecPath() {
        return execPath;
    }
}
