package group.project.controller;

import group.project.dao.MenuRepository;
import group.project.dao.SystemUserRepository;
import group.project.model.SystemUser;
import java.io.IOException;
import java.util.Arrays;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class IndexController {

    @Resource
    private MenuRepository gbMenuRepo;
    @Resource
    private SystemUserRepository gbUserRepo;

    @GetMapping("")
    public String index(ModelMap model) {
        model.addAttribute("menu", gbMenuRepo.getMenus());
        return "listMenu";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public ModelAndView RegisterUserForm() {
        return new ModelAndView("Register", "registeruser", new SystemUser());
    }

    @PostMapping("/register")
    public ModelAndView RegisterUserHandle(SystemUser user) throws IOException {
        //SystemUser us = new SystemUser(user.getUsername(),user.getPassword(),user.getFullname(),user.getPhone(),user.getAddress(), user.getRoles());
        if (gbUserRepo.getUserByUsername(user.getUsername()).size() > 0) {
            ModelAndView modelAndView =  new ModelAndView("redirect:/register?status=fail");
            return modelAndView;
        } else {
            user.setRoles(Arrays.asList("ROLE_USER"));
            gbUserRepo.addUser(user);
            ModelAndView modelAndView =  new ModelAndView("redirect:/login?status=success");
            return modelAndView;
        }
    }
}
