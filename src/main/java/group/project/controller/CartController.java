/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group.project.controller;

import group.project.model.Cart;
import java.util.Map;
import java.util.Random;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import group.project.dao.CartRepository;
import group.project.dao.SystemUserRepository;
import java.util.Hashtable;
import java.util.Iterator;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author anson
 */
@Controller
public class CartController {

    @Resource
    private CartRepository gbCartRepo;
    @Resource
    private SystemUserRepository gbUserRepo;

    private Map<Integer, Cart> cartDatabase = new Hashtable<>();
    private Integer cartId = 0;

    @GetMapping({"/cart"})
    public String getCart(ModelMap model, @RequestParam("username") String username, @RequestParam("status") String status) {
        if ("clean".equals(status)) {
            cartDatabase.clear();
        }
        Map<Integer, Cart> user = new Hashtable<>();
        for (Map.Entry<Integer, Cart> entry : cartDatabase.entrySet()) {
            Cart list = entry.getValue();
            if (list.getUsername().equals(username)) {
                user.put(list.getId(), list);
            }
        }
        if (cartDatabase.size() > 0) {
            model.addAttribute("cartDatabase", user);
        }
        
        return "ShoppingCart";
    }

    @PostMapping({"/cart"})
    public String postCart(ModelMap model, Cart cart) {
        boolean isExist = false;
        cartId++;
        cart.setId(cartId);
        cart.setTotalPrice(cart.getPrice() * cart.getQty());
        if (cartDatabase.size() > 0) {
            for (Map.Entry<Integer, Cart> entry : cartDatabase.entrySet()) {
                Cart list = entry.getValue();
                System.out.println();
                if (list.getMenuId().equals(cart.getMenuId()) && list.getUsername().equals(cart.getUsername())) {
                    isExist = true;
                    if (isExist == true) {
                        list.setQty(list.getQty() + cart.getQty());
                        list.setTotalPrice(list.getPrice() * list.getQty());
                    }
                }
            }
            if (isExist == false) {
                this.cartDatabase.put(cartId, cart);
            }
        } else {
            this.cartDatabase.put(cartId, cart);
        }
        model.addAttribute("cartDatabase", cartDatabase);
        return "redirect:/cart?username=" + cart.getUsername()+"&status=";
    }

    @GetMapping({"/order"})
    public String getOrder(ModelMap model) {
        return "redirect:/";
    }

    @PostMapping({"/order"})
    public String postOrder(ModelMap model, String username) {
        long order_id = 0;
        if (cartDatabase.size() > 0) {
            String fullname = gbUserRepo.getUserByUsername(username).get(0).getFullname();
            String phone = gbUserRepo.getUserByUsername(username).get(0).getPhone();
            String address = gbUserRepo.getUserByUsername(username).get(0).getAddress();

            Random rand = new Random();
            order_id = rand.nextInt(100000000);
            while (gbCartRepo.getOrderById(order_id)) {
                order_id = rand.nextInt(100000000);
            }
            for (Map.Entry<Integer, Cart> entry : cartDatabase.entrySet()) {
                Cart list = entry.getValue();
                if (list.getUsername().equals(username)) {
                    gbCartRepo.addOrder(username, list.getMenuId(), list.getQty(), order_id, list.getMenuName(), fullname, phone, address, list.getPrice());
                }
            }

            

            for (Iterator<Map.Entry<Integer, Cart>> it = cartDatabase.entrySet().iterator(); it.hasNext();) {
                Map.Entry<Integer, Cart> item = it.next();
                if (item.getValue().getUsername().equals(username)) {
                    it.remove();
                }
            }

        }
        model.addAttribute("orderDatabase", gbCartRepo.listOrderById(order_id));
        return "Order";
    }

        @GetMapping({"/delcart"})
    public String DelCart(ModelMap model, @RequestParam("username") String username, @RequestParam("id") int id) {
        
        for (Iterator<Map.Entry<Integer, Cart>> it = cartDatabase.entrySet().iterator(); it.hasNext();) {
                Map.Entry<Integer, Cart> item = it.next();
                if (item.getValue().getUsername().equals(username)&&item.getValue().getId().equals(id)) {
                    it.remove();
                }
            }
        
        return "redirect:/cart?username=" + username+"&status=";
    }
    
            @GetMapping({"cart/change"})
    public String ChangeCart(ModelMap model, @RequestParam("username") String username, @RequestParam("id") int id,@RequestParam("qty") int qty) {
        boolean isExist = false;
        for (Map.Entry<Integer, Cart> entry : cartDatabase.entrySet()) {
                Cart list = entry.getValue();
                if (list.getId().equals(id) && list.getUsername().equals(username)) {
                    isExist = true;
                    if (isExist == true) {
                        list.setQty(qty);
                        list.setTotalPrice(list.getPrice() * list.getQty());
                    }
                }
            }
        
        return "redirect:/cart?username=" + username+"&status=";
    }
}
