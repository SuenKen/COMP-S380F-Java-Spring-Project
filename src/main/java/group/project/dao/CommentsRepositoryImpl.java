/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group.project.dao;

import group.project.model.Comments;
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
public class CommentsRepositoryImpl implements CommentsRepository {

    private final JdbcOperations jdbcOp;

    @Autowired
    public CommentsRepositoryImpl(DataSource dataSource) {
        jdbcOp = new JdbcTemplate(dataSource);
    }

    private static final class CommentsRowMapper implements RowMapper<Comments> {

        @Override
        public Comments mapRow(ResultSet rs, int i) throws SQLException {
            Comments comment = new Comments();
            comment.setId(rs.getInt("id"));
            comment.setMenuId(rs.getInt("menu_id"));
            comment.setUsername(rs.getString("username"));
            comment.setContext(rs.getString("context"));

            return comment;
        }
    }
    private static final String SQL_INSERT_COMMENTS
            = "insert into menu_comments (menu_id,username,context) values (?, ?, ?)";

    @Override
    public void addComments(Comments comment) {
        jdbcOp.update(SQL_INSERT_COMMENTS,
                comment.getMenuId(),
                comment.getUsername(),
                comment.getContext()
        );
    }
    private static final String SQL_UPDATE_COMMENTS
            = "update menu_comments set menu_id = ?, username = ? ,context= ? where id = ?";

    @Override
    public void updateComments(Comments comment) {
        jdbcOp.update(SQL_UPDATE_COMMENTS,
                comment.getMenuId(),
                comment.getUsername(),
                comment.getContext(),
                comment.getId()
        );
    }

    private static final String SQL_SELECT_ALL_COMMENTS
            = "select id, menu_id,username,context from menu_comments";

    @Override
    public List<Comments> listComments() {
        return jdbcOp.query(SQL_SELECT_ALL_COMMENTS, new CommentsRowMapper());
    }
    private static final String SQL_SELECT_ALL_COMMENTS_BY_MENU
            = "select id, menu_id,username,context from menu_comments where menu_id = ?";

    @Override
    public List<Comments> listCommentsByMenuId(int id) {
        return jdbcOp.query(SQL_SELECT_ALL_COMMENTS_BY_MENU, new CommentsRowMapper(), id);
    }

    private static final String SQL_SELECT_COMMENTS
            = "select id, menu_id,username,context from menu_comments where id = ?";

    @Override
    public Comments getCommentsById(int id) {
        return jdbcOp.queryForObject(SQL_SELECT_COMMENTS, new CommentsRowMapper(), id);
    }
    private static final String SQL_DELETE_COMMENTS
            = "delete from menu_comments where id = ?";

    @Override
    public void removeCommentsById(int id) {
        jdbcOp.update(SQL_DELETE_COMMENTS, id);
    }
}
