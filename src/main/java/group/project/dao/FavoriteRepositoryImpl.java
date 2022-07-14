package group.project.dao;

import group.project.model.Favorite;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FavoriteRepositoryImpl implements FavoriteRepository {

    private final JdbcOperations jdbcOp;

    @Autowired
    public FavoriteRepositoryImpl(DataSource dataSource) {
        jdbcOp = new JdbcTemplate(dataSource);
    }

    private static final class FavoriteRowMapper implements RowMapper<Favorite> {

        @Override
        public Favorite mapRow(ResultSet rs, int i) throws SQLException {
            Favorite favorite = new Favorite();
            favorite.setMenu_id(rs.getInt("menu_id"));
            favorite.setUsername(rs.getString("username"));
            favorite.setName(rs.getString("name"));
            return favorite;
        }
    }

    @Override
    public int addFavorite(Favorite favorite) {
        final String SQL_INSERT_FAVORITE
                = "insert into users_favorite (username, menu_id,name) values (?, ?,?) ";
        final String SQL_Search_FAVORITE = "select * from users_favorite where username = ? and menu_id = ?";
        if (jdbcOp.query(SQL_Search_FAVORITE, new FavoriteRowMapper(), favorite.getUsername(), favorite.getMenu_id()).size() == 0) {
            jdbcOp.update(SQL_INSERT_FAVORITE, favorite.getUsername(), favorite.getMenu_id(), favorite.getName());
            return 1;
        }else{
        return 0;
        }
    }

    @Override
    public List<Favorite> listFavoriteByUsername(String username) {
        final String SQL_SELECT_FAVORITE_BY_USERNAME = "select * from users_favorite where username = ?";
        return jdbcOp.query(SQL_SELECT_FAVORITE_BY_USERNAME, new FavoriteRowMapper(), username);
    }

    @Override
    public int removeFavoriteByUsernameAndMenu(int id, String username) {
        final String SQL_SELECT_FAVORITE_BY_MENU_ID = "select * from users_favorite where menu_id = ? and username = ?";
        int x = jdbcOp.query(SQL_SELECT_FAVORITE_BY_MENU_ID, new FavoriteRowMapper(), id, username).size();
        final String SQL_ADD_FAVORITE = "delete from users_favorite where menu_id = ? and username = ?";
        jdbcOp.update(SQL_ADD_FAVORITE, id, username);
        return x;
    }

}
