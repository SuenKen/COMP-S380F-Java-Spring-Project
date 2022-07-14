package group.project.dao;

import group.project.model.Attachment;
import group.project.model.Menu;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class MenuRepositoryImpl implements MenuRepository {

    private final JdbcOperations jdbcOp;

    @Autowired
    public MenuRepositoryImpl(DataSource dataSource) {
        jdbcOp = new JdbcTemplate(dataSource);
    }

    private static final class MenuExtractor implements ResultSetExtractor<List<Menu>> {

        @Override
        public List<Menu> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, Menu> map = new HashMap<>();
            while (rs.next()) {
                Long id = rs.getLong("id");
                Menu menu = map.get(id);
                if (menu == null) {
                    menu = new Menu();
                    menu.setId(id);
                    menu.setName(rs.getString("name"));
                    menu.setDescription(rs.getString("description"));
                    menu.setPrice(rs.getInt("price"));
                    menu.setAvailable(rs.getString("available"));
                    map.put(id, menu);
                }
                String filename = rs.getString("filename");
                if (filename != null) {
                    Attachment attachment = new Attachment();
                    attachment.setName(rs.getString("filename"));
                    attachment.setMimeContentType(rs.getString("content_type"));
                    attachment.setMenuId(id);
                    menu.addAttachment(attachment);
                }
            }
            return new ArrayList<>(map.values());
        }
    }

    @Override
    @Transactional
    public long addMenu(final String name, final String description, final double price, final String available, List<MultipartFile> attachments)
            throws IOException {
        final String SQL_INSERT_MENU
                = "insert into menu (name, description, price,available) values (?, ?, ?,?)";
        final String SQL_INSERT_ATTACHMENT
                = "insert into attachment (filename, content, content_type,"
                + " menu_id) values (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOp.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_MENU,
                        new String[]{"id"});
                ps.setString(1, name);
                ps.setString(2, description);
                ps.setDouble(3, price);
                ps.setString(4, available);
                return ps;
            }
        }, keyHolder);

        Long menu_id = keyHolder.getKey().longValue();
        System.out.println("MenuID " + menu_id + " inserted");

        for (MultipartFile filePart : attachments) {
            if (filePart.getOriginalFilename() != null && filePart.getSize() > 0) {
                LobHandler handler = new DefaultLobHandler();
                jdbcOp.update(SQL_INSERT_ATTACHMENT,
                        new Object[]{filePart.getOriginalFilename(),
                            new SqlLobValue(filePart.getInputStream(),
                                    (int) filePart.getSize(), handler),
                            filePart.getContentType(),
                            menu_id},
                        new int[]{Types.VARCHAR, Types.BLOB, Types.VARCHAR,
                            Types.INTEGER});
                System.out.println("Attachment " + filePart.getOriginalFilename()
                        + " of Menu " + menu_id + " inserted");
            }
        }
        return menu_id;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> getMenus() {
        final String SQL_SELECT_MENUS = "select m.*, a.filename, a.content_type,"
                + " a.content from menu as m left join attachment as a"
                + " on m.id = a.menu_id";
        return jdbcOp.query(SQL_SELECT_MENUS, new MenuExtractor());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> getMenu(long id) {
        final String SQL_SELECT_MENU = "select m.*, a.filename, a.content_type,"
                + " a.content from menu as m left join attachment as a"
                + " on m.id = a.menu_id where m.id = ?";
        return jdbcOp.query(SQL_SELECT_MENU, new MenuExtractor(), id);
    }

    @Override
    @Transactional
    public void updateMenu(long menu_id, String name, String description, double price, String available,
            List<MultipartFile> attachments) throws IOException {
        //"insert into menu (name, description, price,available) values (?, ?, ?,?)";
        final String SQL_UPDATE_MENU = "update menu set name=?, description=? , price=?, available=? where id=?";
        final String SQL_UPDATE_FAVORITE = "update users_favorite set name=? where menu_id=?";
        final String SQL_INSERT_ATTACHMENT
                = "insert into attachment (filename,content,content_type,menu_id) values (?,?,?,?)";

        jdbcOp.update(SQL_UPDATE_MENU, name, description, price, available, menu_id);
        jdbcOp.update(SQL_UPDATE_FAVORITE, name, menu_id);
        System.out.println("Menu " + menu_id + " updated");

        for (MultipartFile filePart : attachments) {
            if (filePart.getOriginalFilename() != null && filePart.getSize() > 0) {
                LobHandler handler = new DefaultLobHandler();
                jdbcOp.update(SQL_INSERT_ATTACHMENT,
                        new Object[]{filePart.getOriginalFilename(),
                            new SqlLobValue(filePart.getInputStream(),
                                    (int) filePart.getSize(), handler),
                            filePart.getContentType(),
                            menu_id},
                        new int[]{Types.VARCHAR, Types.BLOB, Types.VARCHAR, Types.INTEGER});
                System.out.println("Attachment " + filePart.getOriginalFilename()
                        + " of Menu " + menu_id + " inserted");
            }
        }
    }

    @Override
    @Transactional
    public void deleteMenu(long id) {
        final String SQL_DELETE_COMMENTS = "delete from menu_comments where menu_id = ?";
        final String SQL_DELETE_MENU = "delete from menu where id=?";
        final String SQL_DELETE_ATTACHMENTS = "delete from attachment where menu_id=?";
        final String SQL_DELETE_FAVORITE = "delete from users_favorite where menu_id=?";
        jdbcOp.update(SQL_DELETE_COMMENTS, id);
        jdbcOp.update(SQL_DELETE_ATTACHMENTS, id);
        jdbcOp.update(SQL_DELETE_MENU, id);
        System.out.println("Menu " + id + " deleted");
    }

    @Override
    @Transactional
    public void deleteAttachment(long menu_id, String name) {
        final String SQL_DELETE_ATTACHMENT
                = "delete from attachment where menu_id=? and filename=?";
        jdbcOp.update(SQL_DELETE_ATTACHMENT, menu_id, name);
        System.out.println("Attachment " + name + " of Menu " + menu_id + " deleted");
    }

    private static final class AttachmentRowMapper implements RowMapper<Attachment> {

        @Override
        public Attachment mapRow(ResultSet rs, int i) throws SQLException {
            Attachment entry = new Attachment();
            entry.setName(rs.getString("filename"));
            entry.setMimeContentType(rs.getString("content_type"));
            Blob blob = rs.getBlob("content");
            byte[] bytes = blob.getBytes(1l, (int) blob.length());
            entry.setContents(bytes);
            entry.setMenuId(rs.getInt("menu_id"));
            return entry;
        }
    }

    @Override
    @Transactional
    public Attachment getAttachment(long menu_id, String name) {
        final String SQL_SELECT_ATTACHMENT = "select * from attachment"
                + " where menu_id=? and filename=?";
        return jdbcOp.queryForObject(SQL_SELECT_ATTACHMENT,
                new AttachmentRowMapper(), menu_id, name);
    }
}
