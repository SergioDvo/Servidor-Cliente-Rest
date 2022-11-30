package dao;

import JDBC.DBConnectionPool;
import modelo.utils.Newspaper;
import dao.querysConstant.SQLQueries;
import domain.errores.ConnectionDBException;
import domain.errores.DataFailureException;
import jakarta.inject.Inject;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class DaoNewspaper {

    private final DBConnectionPool pool;

    @Inject
    public DaoNewspaper(DBConnectionPool pool) {
        this.pool = pool;
    }


    public List<Newspaper> getAll() {
        try {
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            return jtm.query(SQLQueries.SELECT_ALL_NEWSPAPERS, BeanPropertyRowMapper.newInstance(Newspaper.class));
        } catch (Exception e) {
            throw new ConnectionDBException(e.getMessage());
        }
    }


    public Newspaper save(Newspaper newspaper) {
        try {
            KeyHolder id = new GeneratedKeyHolder();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(pool.getDataSource());
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQLQueries.INSERT_NEWSPAPER,Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, newspaper.getNameNewspaper());
                ps.setDate(2, Date.valueOf(newspaper.getReleaseDate()));
                return ps;
            }, id);
            if (rowsAffected == 0) {
                throw new DataFailureException("No se ha podido insertar el periódico");
            } else {
                newspaper.setId(Objects.requireNonNull(id.getKey()).intValue());
                return newspaper;
            }
        } catch (DataAccessException e) {
            throw new ConnectionDBException(e.getMessage());
        }
    }


    public void delete(int newspaperId) {
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(pool.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            JdbcTemplate jtm = new JdbcTemplate(transactionManager.getDataSource());
            jtm.update(SQLQueries.DELETE_ARTICLE_BY_ID_NEWSPAPER, newspaperId);
            jtm.update(SQLQueries.DELETE_SUBSCRIPTIONS_BY_ID_NEWSPAPER, newspaperId);
            jtm.update(SQLQueries.DELETE_READ_ARTICLE_BY_IDNEWSPAPER, newspaperId);
            jtm.update(SQLQueries.DELETE_NEWSPAPER_BY_ID, newspaperId);
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            throw new DataFailureException(e.getMessage());
        }
    }

    public Newspaper update(Newspaper newspaper) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            if (jtm.update(SQLQueries.UPDATE_NEWSPAPER, newspaper.getNameNewspaper(), newspaper.getReleaseDate(), newspaper.getId()) ==0) {
                throw new DataFailureException("No se ha podido actualizar el periódico");
            } else {
                return newspaper;
            }
        } catch (Exception e) {
            throw new DataFailureException(e.getMessage());
        }

    }


}
