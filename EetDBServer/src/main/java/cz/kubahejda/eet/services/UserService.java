package cz.kubahejda.eet.services;

import cz.kubahejda.eet.model.Registration;
import cz.kubahejda.eet.model.User;

import java.util.List;

/**
 * Created by Kuba on 23.3.2017.
 */
public interface UserService {
    boolean authenticate(final String username, final String password);
    List<User> getInfo(final String username);
    public Registration registration(String username, String password, Long companyId, String email, String companyName, String vatId);
}
