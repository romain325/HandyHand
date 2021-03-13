package Core.User;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * User connection token
 */
public class Token {
    private String id;
    private String token;

    /**
     * Create a token from a given id
     * @param id id
     */
    public Token(String id) {
        setId(id);
        generateToken();
    }

    public Token(){}

    /**
     * Generate a token from id
     */
    public void generateToken(){
        SecureRandom secureRandom = new SecureRandom();
        Base64.Encoder base64Encoder = Base64.getUrlEncoder();
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        this.token= base64Encoder.encodeToString(randomBytes);
    }

    /**
     * Get token's linked id
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Set the id of the current object
     * @param id new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get Token
     * @return connection token
     */
    public String getToken() {
        return token;
    }
}
