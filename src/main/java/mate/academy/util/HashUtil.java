package mate.academy.util;

import org.apache.log4j.Logger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class HashUtil {
    private static final Logger logger = Logger.getLogger(HashUtil.class);

    public static String getSha512SecurePassword(String passwordToHash, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i< bytes.length ; i++) {
                stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = stringBuilder.toString();
        }
        catch (NoSuchAlgorithmException e){
            logger.error("Can't find algorithm", e);
        }
        return generatedPassword;
    }

    public static String getRandomSalt() {
        return UUID.randomUUID().toString().substring(1, 7);
    }
}
