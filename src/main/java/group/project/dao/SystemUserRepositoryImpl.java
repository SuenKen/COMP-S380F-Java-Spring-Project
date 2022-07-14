/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group.project.dao;

import group.project.model.SystemUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SystemUserRepositoryImpl implements SystemUserRepository {

    private final JdbcOperations jdbcOp;

    @Autowired
    public SystemUserRepositoryImpl(DataSource dataSource) {
        jdbcOp = new JdbcTemplate(dataSource);
    }

    private static final class UserExtractor implements ResultSetExtractor<List<SystemUser>> {

        @Override
        public List<SystemUser> extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            Map<String, SystemUser> map = new HashMap<>();
            while (rs.next()) {
                String username = rs.getString("username");
                SystemUser user = map.get(username);
                if (user == null) {
                    user = new SystemUser();
                    user.setUsername(username);
                    user.setPassword(rs.getString("password"));
                    user.setFullname(rs.getString("fullname"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));
                    map.put(username, user);
                }
                user.getRoles().add(rs.getString("role"));
            }
            return new ArrayList<>(map.values());
        }
    }

    @Override
    @Transactional
    public void addUser(SystemUser user) {
        final String SQL_INSERT_USER
                = "insert into users (username, password,fullname,phone,address) values (?, ?, ?, ?, ?)";
        final String SQL_INSERT_ROLE
                = "insert into user_roles (username, role) values (?, ?)";
        jdbcOp.update(SQL_INSERT_USER, user.getUsername(), "{noop}"+user.getPassword(), user.getFullname(), user.getPhone(), user.getAddress());
        System.out.println("User " + user.getUsername() + " inserted");

        for (String role : user.getRoles()) {
            jdbcOp.update(SQL_INSERT_ROLE, user.getUsername(), role);
            System.out.println("User_role " + role + " of user "
                    + user.getUsername() + " inserted");
        }
    }

    @Override
    @Transactional
    public void updateUser(SystemUser user) {
        final String SQL_UPDATE_USER
                = "update users set username = ?, password = ? ,fullname= ? ,phone= ? ,address= ? where username = ?";
        final String SQL_DELETE_ROLES = "delete from user_roles where username=?";
        final String SQL_INSERT_ROLE
                = "insert into user_roles (username, role) values (?, ?)";
        jdbcOp.update(SQL_UPDATE_USER,
                user.getUsername(),
                "{noop}" + user.getPassword(),
                user.getFullname(),
                user.getPhone(),
                user.getAddress(),
                user.getUsername());
        jdbcOp.update(SQL_DELETE_ROLES, user.getUsername());
        for (String role : user.getRoles()) {
            jdbcOp.update(SQL_INSERT_ROLE, user.getUsername(), role);
            System.out.println("User_role " + role + " of user "
                    + user.getUsername() + " inserted");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<SystemUser> listUser() {
        final String SQL_SELECT_USERS
                = "select users.*, user_roles.role from users, user_roles"
                + " where users.username = user_roles.username";
        return jdbcOp.query(SQL_SELECT_USERS, new UserExtractor());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SystemUser> getUserByUsername(String username) {
        final String SQL_SELECT_USER
                = "select users.*, user_roles.role from users, user_roles"
                + " where users.username = user_roles.username"
                + " and users.username = ?";
        return jdbcOp.query(SQL_SELECT_USER, new UserExtractor(), username);
    }

    @Override
    @Transactional
    public void removeUserByUsername(String username) {
        final String SQL_DELETE_USER = "delete from users where username=?";
        final String SQL_DELETE_ROLES = "delete from user_roles where username=?";
        jdbcOp.update(SQL_DELETE_ROLES, username);
        jdbcOp.update(SQL_DELETE_USER, username);
    }

}
