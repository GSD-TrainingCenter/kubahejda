package cz.kubahejda.eet.repository;

import cz.kubahejda.eet.model.Receipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kuba on 30.3.2017.
 */
@Repository
public class JdbcTemplateReceiptRepository implements ReceiptRepository {

    private final Logger slf4jLogger = LoggerFactory.getLogger(JdbcTemplateReceiptRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Receipt create(Receipt entity) {
        jdbcTemplate.update("insert into transactions (id, date, value, fik, bkp) values (?,?,?,?,?)",
                new Object[]{entity.getCompanyId(), entity.getDate(), entity.getValue(), entity.getFik(), entity.getBkp()});
        return entity;
    }

    @Override
    public List<Receipt> find(Long company_id) {
        List<Receipt> receipts;
        slf4jLogger.info("sql: {}", "select * from transactions where id=" + company_id.toString());
        receipts = jdbcTemplate.query("select * from transactions where id=?", new Object[]{company_id},
                new RowMapper<Receipt>() {
                    public Receipt mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Receipt receipt = new Receipt(
                                rs.getLong(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5)
                        );

                        return receipt;
                    }
                });
        return receipts;
    }

    @Override
    public Receipt findByCompanyIdAndDate(Long company_id, String date) {
        return  jdbcTemplate.query("select * from transactions where id=? and date=?", new Object[]{company_id, date},
                new RowMapper<Receipt>() {
                    public Receipt mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Receipt receipt = new Receipt(
                                rs.getLong(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5)
                        );

                        return receipt;
                    }
                }).get(0);
    }
}
