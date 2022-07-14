package group.project.controller;

import group.project.dao.SystemUserRepository;
import group.project.model.SystemUser;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private SystemUserRepository gbUserRepo;

    @GetMapping
    public String index(ModelMap model) {
        return "AdminPage";
    }


    @GetMapping("/account/view")
    public String view(ModelMap model) {
        model.addAttribute("users", gbUserRepo.listUser());
        return "AllAccount";
    }

    public static class Form {

        private String username;
        private String password;
        private String fullname;
        private String phone;
        private String address;
        private String[] roles;

        public Form() {

        }

        public Form(String username, String password, String fullname, String phone, String address, String[] roles) {
            this.username = username;
            this.password = password;
            this.fullname = fullname;
            this.phone = phone;
            this.address = address;
            this.roles = roles;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

    }

    @GetMapping("/account/add")
    public ModelAndView addUserForm() {
        return new ModelAndView("AddUser", "systemuser", new Form());
    }

    @PostMapping("/account/add")
    public View addUserHandle(Form form) throws IOException {
        SystemUser user = new SystemUser(form.getUsername(), form.getPassword(), form.getFullname(), form.getPhone(), form.getAddress(), form.getRoles());
        gbUserRepo.addUser(user);
        return new RedirectView("/admin/account/view", true);
    }

    @GetMapping("/account/edit")
    public String editUserForm(@RequestParam("username") String username, ModelMap model) {
        List<SystemUser> user = gbUserRepo.getUserByUsername(username);
        user.get(0).setPassword(user.get(0).getPassword().replace("{noop}", ""));
        model.addAttribute("users", user.get(0));
        //model.addAttribute("role", role);
        return "EditAccount";
    }

    @PostMapping("/account/edit")
    public View editUserHandle(SystemUser entry) {
        gbUserRepo.updateUser(entry);
        return new RedirectView("/admin/account/view", true);
    }

    @GetMapping("/account/delete")
    public String deleteUser(@RequestParam("username") String username) {
        gbUserRepo.removeUserByUsername(username);
        return "redirect:/admin/account/view";
    }
}
