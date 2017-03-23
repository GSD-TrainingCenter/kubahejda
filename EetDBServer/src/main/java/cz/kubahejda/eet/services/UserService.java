package cz.kubahejda.eet.services;

/**
 * Created by Kuba on 23.3.2017.
 */
public interface UserService {
    boolean authenticate(final String username, final String password);
}
