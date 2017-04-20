package cz.kubahejda.eet.repository;

import com.google.common.base.Preconditions;
import cz.kubahejda.eet.model.User;
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
public class JdbcTemplateUserRepository implements UserRepository {


    private final Logger slf4jLogger = LoggerFactory.getLogger(JdbcTemplateUserRepository.class);

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
    @Override
    public List<User> getInfo(String username) {
        return (jdbcTemplate.query("SELECT * FROM users WHERE username = ?", new Object[]{username}, new BeanPropertyRowMapper<>(User.class)));
    }

    @Override
    public User getInfo(Long id) {
        return (jdbcTemplate.query("SELECT * FROM users WHERE company_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(User.class))).get(0);
    }

    @Override
    public User create(String username, String password, Long companyId, String email, String companyName, String vatId) {
        Preconditions.checkNotNull(username);
        Preconditions.checkNotNull(password);
        Preconditions.checkNotNull(companyId);
        Preconditions.checkNotNull(email);
        Preconditions.checkNotNull(companyId);
        Preconditions.checkNotNull(companyName);

        return jdbcTemplate.query("INSERT INTO users (username, password, company_id, email, company_name, vat_id)" +
                " values (?,?,?,?,?,?)", new Object[]{username, password, companyId, email, companyName, vatId},
                new BeanPropertyRowMapper<>(User.class)).get(0);
    }
}
