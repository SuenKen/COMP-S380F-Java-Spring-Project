package group.project.controller;

import group.project.dao.CartRepository;
import group.project.dao.FavoriteRepository;
import group.project.dao.SystemUserRepository;
import group.project.model.Favorite;
import group.project.model.Order;
import group.project.model.SystemUser;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private SystemUserRepository gbUserRepo;
    
    @Resource
    private FavoriteRepository gbFavoriteRepo;
    
     @Resource
    private CartRepository gbOrderRepo;
     
   @GetMapping({"", "/change"})
    public String editUserForm(@RequestParam("username") String username, ModelMap model) {
        List<SystemUser> user = gbUserRepo.getUserByUsername(username);
        user.get(0).setPassword(user.get(0).getPassword().replace("{noop}", ""));
        model.addAttribute("users", user.get(0));
        //model.addAttribute("role", role);
        return "UpdateInformation";
    }

    @PostMapping("/change/{username}")
    public View editUserHandle(SystemUser user) {
        gbUserRepo.updateUser(user);
        return new RedirectView("/user/change?username="+user.getUsername()+"&status=success", true);
    }
   @GetMapping({"", "/favorite"})
    public String FavoriteMenu(@RequestParam("username") String username, ModelMap model) {
        List<Favorite> user = gbFavoriteRepo.listFavoriteByUsername(username);
        model.addAttribute("users", user);
        //model.addAttribute("role", role);
        return "AllFavorite";
    }
    
        @GetMapping("/favorite/deletefavorite")
    public String deleteFavoriteHandle(@RequestParam("username") String username, @RequestParam("id") int id) {
        gbFavoriteRepo.removeFavoriteByUsernameAndMenu(id,username);
        return "redirect:/user/favorite?username=" + username;
    }

        @GetMapping({"", "/orderHistory"})
    public String OrderHistoryDetail(@RequestParam("username") String username, ModelMap model) {
        List<Order> user = gbOrderRepo.listOrderByUsername(username);
        model.addAttribute("users", user);
        return "AllOrder";
    }
    
        @GetMapping({"", "/orderHistory/{orderid}"})
    public String OrderHistory(@PathVariable("orderid") long orderid, ModelMap model) {
        List<Order> user = gbOrderRepo.listOrderById(orderid);
        model.addAttribute("users", user);
        return "OrderHistoryDetail";
    }
}
