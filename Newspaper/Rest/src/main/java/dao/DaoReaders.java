package dao;

import JDBC.DBConnectionPool;
import dao.querysConstant.SQLQueries;
import domain.errores.ConnectionDBException;
import domain.errores.DataFailureException;
import jakarta.inject.Inject;

import modelo.utils.Reader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DaoReaders {

    private final DBConnectionPool pool;

    @Inject
    public DaoReaders(DBConnectionPool pool) {
        this.pool = pool;
    }

    public List<Reader> getAll() {
        List<Reader> readers = new ArrayList<>();

        try (Connection con = pool.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_readers_QUERY);
            return readRS(readers, rs);

        } catch (Exception ex) {
            throw new DataFailureException(ex.getMessage());
        }

    }

    public Reader getById(int id) {
        List<Reader> readers = new ArrayList<>();

        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READERS_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            List<Reader> readerList = readRS(readers, rs);
            return readerList.get(0);

        } catch (Exception ex) {
            throw new DataFailureException(ex.getMessage());
        }

    }

    public List<Reader> getAll(int type) {
        List<Reader> readers = new ArrayList<>();
        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.FILTER_READERS_BY_TYPE)) {
            preparedStatement.setInt(1, type);
            ResultSet rs = preparedStatement.executeQuery();
            return readRS(readers, rs);
        } catch (Exception ex) {
            throw new DataFailureException(ex.getMessage());
        }
    }

    public List<Reader> getAllNewpaper(int idNewspaper) {
        List<Reader> readers = new ArrayList<>();
        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.FILTER_READERS_BY_NEWSPAPER)) {
            preparedStatement.setInt(1, idNewspaper);
            ResultSet rs = preparedStatement.executeQuery();
            return readRS(readers, rs);

        } catch (Exception ex) {
            throw new DataFailureException(ex.getMessage());
        }
    }

    private List<Reader> readRS(List<Reader> readers, ResultSet rs) {
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name_reader");
                Date supplierID = rs.getDate("birth_reader");
                LocalDate dateOfBirth = supplierID.toLocalDate();
                Date.valueOf(dateOfBirth);
                if (id > 0) {
                    Reader reader = new Reader(id, name, dateOfBirth);
                    readers.add(reader);
                }
            }
            return readers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(int id) {
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(pool.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            JdbcTemplate jtm = new JdbcTemplate(transactionManager.getDataSource());
            jtm.update(SQLQueries.DELETE_READ_ARTICLE_BY_IDREADER, id);
            jtm.update(SQLQueries.DELETE_SUBSCRIPTIONS_BY_ID_READER, id);
            jtm.update(SQLQueries.DELETE_READER, id);
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            throw new DataFailureException(e.getMessage());
        }
    }

    public Reader save(Reader r) {
        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_READER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, r.getName());
            preparedStatement.setDate(2, Date.valueOf(r.getDateOfBirth()));
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                r.setId(rs.getInt(1));
            }
            return r;
        } catch (SQLException e) {
            throw new DataFailureException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectionDBException(e.getMessage());
        }
    }

    public Reader update(Reader r) {
        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_READER)) {
            preparedStatement.setString(1, r.getName());
            preparedStatement.setDate(2, Date.valueOf(r.getDateOfBirth()));
            preparedStatement.setInt(3, r.getId());
            preparedStatement.executeUpdate();
            return r;
        } catch (SQLException e) {
            throw new DataFailureException(e.getMessage());
        } catch (Exception e) {
            throw new ConnectionDBException(e.getMessage());
        }
    }

    public int counterReadersQuery(int id_article) {
        int count = 0;
        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_count_readers_by_article, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, id_article);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception ex) {
            throw new DataFailureException(ex.getMessage());
        }
    }

    public List<Reader> readersByNewspaperQuery() {
        List<Reader> readers = new ArrayList<>();
        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_5_READER_NW1_ORDERBY_SIGNING_DATE)) {
            ResultSet rs = preparedStatement.executeQuery();
            int count = 0;
            try {
                while (rs.next() && count < 5) {
                    Reader reader = new Reader();
                    reader.setId(rs.getInt("id"));
                    reader.setName(rs.getString("name_reader"));
                    reader.setDateOfBirth(rs.getDate("birth_reader").toLocalDate());
                    readers.add(reader);
                    count++;
                }
                return readers;
            } catch (SQLException ex) {
                throw new DataFailureException(ex.getMessage());
            }
        } catch (Exception e) {
            throw new ConnectionDBException(e.getMessage());
        }
    }
}
