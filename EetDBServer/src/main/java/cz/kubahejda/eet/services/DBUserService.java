package cz.kubahejda.eet.services;


import com.google.common.base.Preconditions;
import cz.kubahejda.eet.model.Registration;
import cz.kubahejda.eet.model.User;
import cz.kubahejda.eet.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Kuba on 23.3.2017.
 */
@Service
public class DBUserService implements UserService {

    public static final Logger slf4jLogger = LoggerFactory.getLogger(DBUserService.class);

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

    @Override
    public List<User> getInfo(String username) {
        slf4jLogger.info("Getting info of: {}", username);
        return repository.getInfo(username);
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

    public String encryptMessage(String message, String key) {
        String result = "";
        try
        {
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(message.getBytes("UTF-8"));

            char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            result = new  String(encodeHex(encrypted, DIGITS_LOWER));

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            return result;
        }
    }

    public static char[] encodeHex(final byte[] data, final char[] toDigits) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }


    @Transactional
    public Registration registration(String username, String password, Long companyId, String email, String companyName, String vatId) {
        User u = repository.create(username,password,companyId,email,companyName,vatId);
        if (u == null)
            return new Registration(1, null);
        else
            return new Registration(0, u.getEmail());
    }
}
