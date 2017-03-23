package cz.kubahejda.eet.repository;

/**
 * Created by Kuba on 23.3.2017.
 */
public interface UserRepository {
    boolean authentificate(final String username, final String password);
}
