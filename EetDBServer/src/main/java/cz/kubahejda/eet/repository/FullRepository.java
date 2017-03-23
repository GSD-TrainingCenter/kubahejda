package cz.kubahejda.eet.repository;

import cz.kubahejda.eet.model.User;
import cz.kubahejda.eet.services.DBUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.*;

import java.util.List;

/**
 * Created by Kuba on 23.3.2017.
 */
@Repository
public class FullRepository implements UserRepository {


    private final Logger slf4jLogger = LoggerFactory.getLogger(FullRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean authentificate(String username, String password) {



        List<User> userList = jdbcTemplate.query("SELECT * FROM users WHERE username = ?", new Object[]{username}, new BeanPropertyRowMapper<>(User.class));

        if (userList.isEmpty())
            return false;

        for (User u : userList) {
            if (u.getPassword().compareTo(password) == 0)
                return true;

            slf4jLogger.info(u.getUsername());
        }
        return false;
    }
}
