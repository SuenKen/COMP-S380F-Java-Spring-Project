/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group.project.dao;

import group.project.model.Order;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository {

    public void addOrder(String username, Integer menuId, Integer qty, long order_id, String menu_name, String fullname, String phone, String address, Double price);

    public boolean getOrderById(long id);

    public List<Order> listOrderByUsername(String username);
    
    public List<Order> listOrderById(long id);
}
