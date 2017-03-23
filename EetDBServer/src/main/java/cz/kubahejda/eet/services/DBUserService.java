package cz.kubahejda.eet.services;


import cz.kubahejda.eet.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import org.apache.commons.codec.binary.Hex;

/**
 * Created by Kuba on 23.3.2017.
 */
@Service
public class DBUserService implements UserService {

    private final Logger slf4jLogger = LoggerFactory.getLogger(DBUserService.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private String getKey;

    @Override
    public boolean authenticate(String username, String password) {
        slf4jLogger.info("Authenticating user: " + username + " " + password);
        slf4jLogger.info("key: " + getKey);
        String decryptedPassword = decryptMessage(password, getKey);
        boolean res = repository.authentificate(username, decryptedPassword);
        slf4jLogger.info("Got response" + res);
        return res;
    }

    public String decryptMessage(String encrypted, String key) {
        String result = null;
        try
        {
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] byteArray = Hex.decodeHex(encrypted.toCharArray());
            slf4jLogger.info(encrypted);

            result = new String(cipher.doFinal(byteArray));
            slf4jLogger.info(result);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            return result;
        }
    }
}
