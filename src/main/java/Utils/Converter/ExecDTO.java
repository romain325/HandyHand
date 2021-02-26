package Utils.Converter;

import java.util.Base64;
import java.util.Map;

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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getExecPath() {
        return execPath;
    }
}
