package uaic.fii.MarvelMonPlay.managers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordManager {

    private final String encryptedPassword;

    public PasswordManager(String password) {
        encryptedPassword = getHashPassword(password);
    }
    private String getHashPassword(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public String getEncryptedPassword(){
        return encryptedPassword;
    }
    public boolean verifyPassword(String password) {
        return encryptedPassword.equals(getHashPassword(password));
    }
}
