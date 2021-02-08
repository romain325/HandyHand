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

public class User {
    private String id;
    private String mail;
    private String password;
    private List<String> scriptsId;

    public User(){}

    public User(String mail,String mdp) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.mail=mail;
        password=hash_password(mdp);
        scriptsId=new ArrayList<>();
        id=getId();
    }

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

    public String getId() {
        return new String(Base64.getEncoder().encode((mail).toLowerCase().getBytes())); }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getScriptsId() {
        return scriptsId;
    }

    public void addScript(String scriptId) {
        scriptsId.add(scriptId);
    }

    public void removeScript(String s) {
        scriptsId.remove(s);
    }
}
