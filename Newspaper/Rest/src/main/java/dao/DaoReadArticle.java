package dao;

import JDBC.DBConnectionPool;
import modelo.utils.ReadArticles;
import dao.querysConstant.SQLQueries;
import domain.errores.DataFailureException;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoReadArticle {

    private final DBConnectionPool pool;

    @Inject
    public DaoReadArticle(DBConnectionPool pool) {
        this.pool = pool;
    }

    public List<ReadArticles> getAll() {
        List<ReadArticles> readers = new ArrayList<>();
        try (Connection con = pool.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_readarticles_QUERY);
            return readRS(readers, rs);

        } catch (SQLException ex) {
            throw new DataFailureException(ex.getMessage());
        }

    }

    private List<ReadArticles> readRS(List<ReadArticles> readers, ResultSet rs) {
        try {
            while (rs.next()) {
                int id_article = rs.getInt("id_article");
                int id_readers = rs.getInt("id_reader");
                ReadArticles reader = new ReadArticles(id_article, id_readers);
                readers.add(reader);
            }
            return readers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveReadArticle(int readerId, int articleId, Integer rating) {

        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_READ_ARTICLE)) {
            preparedStatement.setInt(1, articleId);
            preparedStatement.setInt(2, readerId);
            preparedStatement.setInt(3, rating);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataFailureException("Error al guardar el artículo");
        }

    }
}
