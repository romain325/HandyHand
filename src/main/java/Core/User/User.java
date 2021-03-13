package Core.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

/**
 * Representation of a user
 */
public class User {
    private String id;
    private String mail;
    private String password;
    private List<String> scriptsId;

    public User(){}

    public User(String mail,String mdp) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.mail = mail;
        password = hash_password(mdp);
        scriptsId = new ArrayList<>();
        id = getId();
    }

    /**
     * Hash the password for saving
     * @param mdp raw password
     * @return hashed password
     */
    private String hash_password(String mdp) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = new byte[16];
        Random random = new Random(2);
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(mdp.toCharArray(), salt, 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = f.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();
        return enc.encodeToString(hash);
    }

    /**
     * Get user id
     * @return user id
     */
    public String getId() {
        return new String(Base64.getEncoder().encode((mail).toLowerCase().getBytes()));
    }

    /**
     * Get user's mail
      * @return user's mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * Get hashed Password
     * @return hashed password
     */
    public String getPassword() {
        return password;
    }

    /**
     * get script related to the user
     * @return scripts list of the user
     */
    public List<String> getScriptsId() {
        return scriptsId;
    }

    /**
     * Set the scripts' list
     * @param scriptsId scripts" list
     */
    public void setScriptsId(List<String> scriptsId) {
        this.scriptsId = scriptsId;
    }

    /**
     * Add a script to the scripts' list
     * @param scriptId new script
     */
    public void addScript(String scriptId) {
        scriptsId.add(scriptId);
    }

    /**
     * Remove a script from the user's script
     * @param s script to remove
     */
    public void removeScript(String s) {
        scriptsId.remove(s);
    }
}
