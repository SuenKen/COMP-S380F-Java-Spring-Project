/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group.project.dao;

import group.project.model.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepositoryImpl implements CartRepository {

    private final JdbcOperations jdbcOp;

    @Autowired
    public CartRepositoryImpl(DataSource dataSource) {
        jdbcOp = new JdbcTemplate(dataSource);
    }

    private static final class OrderExtractor implements ResultSetExtractor<List<Order>> {

        @Override
        public List<Order> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Long, Order> map = new HashMap<>();
            while (rs.next()) {
                Long id = rs.getLong("id");
                Order order = map.get(id);
                if (order == null) {
                    order = new Order();
                    order.setId(id);
                    order.setOrderId(rs.getInt("order_id"));
                    order.setUsername(rs.getString("username"));
                    order.setMenu_id(rs.getInt("menu_id"));
                    order.setMenu_name(rs.getString("menu_name"));
                    order.setQty(rs.getInt("qty"));
                    order.setPrice(rs.getDouble("price"));
                    order.setFullname(rs.getString("fullname"));
                    order.setPhone(rs.getString("phone"));
                    order.setAddress(rs.getString("address"));
                    map.put(id, order);
                }

            }
            return new ArrayList<>(map.values());
        }
    }

    @Override
    public void addOrder(final String username, final Integer menuId, final Integer qty, final long order_id, final String menu_name, final String fullname, final String phone, final String address, final Double price) {
        final String SQL_INSERT_ORDER
                = "insert into order_table (username,menu_Id,qty,order_id,menu_name,fullname,phone,address,price) values (?,?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOp.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_INSERT_ORDER,
                        new String[]{"id"});
                ps.setString(1, username);
                ps.setInt(2, menuId);
                ps.setInt(3, qty);
                ps.setLong(4, order_id);
                ps.setString(5, menu_name);
                ps.setString(6, fullname);
                ps.setString(7, phone);
                ps.setString(8, address);
                ps.setDouble(9, price);
                return ps;
            }
        }, keyHolder);
    }
    private static final String SQL_SELECT_ORDER
            = "select * from order_table where id = ?";

    @Override
    public boolean getOrderById(long id) {
        if (jdbcOp.query(SQL_SELECT_ORDER, new OrderExtractor(), id).size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Order> listOrderByUsername(String username) {
        final String SQL_SELECT_ORDER_BY_USERNAME = "select distinct order_id,id,username,menu_id,menu_name,qty,price,fullname,phone,address from order_table where username = ?";
        return jdbcOp.query(SQL_SELECT_ORDER_BY_USERNAME, new OrderExtractor(), username);
    }

    @Override
    public List<Order> listOrderById(long id) {
        final String SQL_SELECT_ORDER_BY_USERNAME = "select * from order_table where order_id = ?";
        return jdbcOp.query(SQL_SELECT_ORDER_BY_USERNAME, new OrderExtractor(), id);
    }
}
