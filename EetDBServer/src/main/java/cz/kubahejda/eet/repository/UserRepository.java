package cz.kubahejda.eet.repository;

import cz.kubahejda.eet.model.User;

import java.util.List;

/**
 * Created by Kuba on 23.3.2017.
 */
public interface UserRepository {
    boolean authentificate(final String username, final String password);
    List<User> getInfo(String username);
}
