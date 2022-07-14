/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group.project.controller;

import group.project.dao.MenuRepository;
import group.project.model.Menu;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/admin/menu")
public class MenuController {

    @Resource
    private MenuRepository gbMenuRepo;

    @GetMapping({"", "/view"})
    public String index(ModelMap model) {
        model.addAttribute("menu", gbMenuRepo.getMenus());
        return "AllMenu";
    }

    public static class Form {

        private String name;
        private String description;
        private double price;
        private String available;
        private List<MultipartFile> attachments;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getAvailable() {
            return available;
        }

        public void setAvailable(String available) {
            this.available = available;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }

    }

    @GetMapping("/add")
    public ModelAndView addMenuForm() {
        return new ModelAndView("AddMenu", "menu", new Form());
    }

    @PostMapping("/add")
    public String create(Form form, Principal principal) throws IOException {
        if (form.getAvailable() == null) {
            form.setAvailable("No");
        }
        long menuId = gbMenuRepo.addMenu(form.getName(),
                form.getDescription(), form.getPrice(), form.getAvailable(), form.getAttachments());
        return "redirect:/menu/detail/" + menuId;
    }

    @GetMapping("/edit/{menuId}")
    public ModelAndView showEdit(@PathVariable("menuId") long menuId,
            Principal principal, HttpServletRequest request) {
        List<Menu> menus = gbMenuRepo.getMenu(menuId);
        if (menus.isEmpty() || !request.isUserInRole("ROLE_ADMIN")) {
            return new ModelAndView(new RedirectView("/", true));
        }
        Menu menu = menus.get(0);
        ModelAndView modelAndView = new ModelAndView("editMenu");
        modelAndView.addObject("menuId", menuId);
        modelAndView.addObject("menu", menu);
        Form menuForm = new Form();
        menuForm.setName(menu.getName());
        menuForm.setDescription(menu.getDescription());
        menuForm.setPrice(menu.getPrice());
        menuForm.setAvailable(menu.getAvailable());
        modelAndView.addObject("menuForm", menuForm);
        return modelAndView;
    }

    @PostMapping("/edit/{menuId}")
    public String edit(@PathVariable("menuId") long menuId, Form form,
            Principal principal, HttpServletRequest request) throws IOException {
        List<Menu> menus = gbMenuRepo.getMenu(menuId);
        if (menus.isEmpty() || (!request.isUserInRole("ROLE_ADMIN"))) {
            return "redirect:/";
        }
        if (form.getAvailable() == null) {
            form.setAvailable("No");
        }
        gbMenuRepo.updateMenu(menuId, form.getName(),
                form.getDescription(), form.getPrice(), form.getAvailable(), form.getAttachments());
        return "redirect:/menu/detail/" + menuId;
    }

    @GetMapping("/delete/{menuId}")
    public String deleteTicket(@PathVariable("menuId") long menuId) {

        gbMenuRepo.deleteMenu(menuId);
        return "redirect:/admin/menu/view";
    }
        @GetMapping("/detail/{menuId}/attachment/delete/{attachment:.+}")
    public String deleteAttachment(@PathVariable("menuId") long menuId,
            @PathVariable("attachment") String name) {
        gbMenuRepo.deleteAttachment(menuId, name);
        return "redirect:/admin/menu/edit/"+menuId;
    }
}
