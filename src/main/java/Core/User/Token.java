package Core.User;

import java.security.SecureRandom;
import java.util.Base64;

public class Token {
    private String id;
    private String token;

    public Token(String id) {
        setId(id);
        generateToken();
    }

    public Token(){}

    public void generateToken(){
        SecureRandom secureRandom = new SecureRandom();
        Base64.Encoder base64Encoder = Base64.getUrlEncoder();
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        this.token= base64Encoder.encodeToString(randomBytes);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token=token;
    }
}
