/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group.project.controller;

import group.project.dao.CommentsRepository;
import group.project.dao.FavoriteRepository;
import group.project.dao.MenuRepository;
import group.project.model.Attachment;
import group.project.model.Cart;
import group.project.model.Comments;
import group.project.model.Favorite;
import group.project.model.Menu;
import group.project.view.DownloadingView;
import java.io.IOException;
import java.util.Collection;
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
@RequestMapping("/menu")
public class MenuDetailController {

    @Resource
    private MenuRepository gbMenuRepo;

    @Resource
    private CommentsRepository gbCommentsRepo;

    @Resource
    private FavoriteRepository gbFavoriteRepo;

    @GetMapping("/detail/{menuId}")
    public String view(@PathVariable("menuId") long menuId, ModelMap model) {
        List<Menu> menus = gbMenuRepo.getMenu(menuId);
        if (menus.isEmpty()) {
            return "redirect:/";
        }
        Collection<Attachment> attachments = menus.get(0).getAttachments();

        model.addAttribute("menuId", menuId);
        model.addAttribute("menu", menus.get(0));
        model.addAttribute("attachments", attachments);
        model.addAttribute("Allcomments", gbCommentsRepo.listCommentsByMenuId((int) menuId));
        model.addAttribute("comments", new Comments());
        model.addAttribute("favorite", new Favorite());
        model.addAttribute("cart", new Cart());
        return "MenuDetail";
    }

    @PostMapping("/detail/{menuId}")
    public String CommentsHandle(Comments comments) throws IOException {
        gbCommentsRepo.addComments(comments);
        return "redirect:/menu/detail/" + comments.getMenuId();
    }

    @PostMapping("/detail/{menuId}/addfavorite")
    public String AddFavoriteHandle(Favorite favorite) throws IOException {
        int result = gbFavoriteRepo.addFavorite(favorite);
        return "redirect:/menu/detail/" + favorite.getMenu_id()+"?result="+result;
    }

    @GetMapping("/detail/{menuId}/deletefavorite")
    public String deleteFavoriteHandle(@RequestParam("username") String username, @PathVariable("menuId") int menuId) {
        if(gbFavoriteRepo.removeFavoriteByUsernameAndMenu(menuId,username) == 0){
        return "redirect:/menu/detail/" + menuId+"?status=fail";
        }
        gbFavoriteRepo.removeFavoriteByUsernameAndMenu(menuId,username);
        return "redirect:/menu/detail/" + menuId+"?status=success";
    }

    @GetMapping("/detail/{menuId}/comment/delete")
    public String deleteComments(@RequestParam("id") int id, @PathVariable("menuId") int menuId) {
        gbCommentsRepo.removeCommentsById(id);
        return "redirect:/menu/detail/" + menuId;
    }

    @GetMapping("/detail/{menuId}/attachment/{attachment:.+}")
    public View download(@PathVariable("menuId") long menuId,
            @PathVariable("attachment") String name) {
        Attachment attachment = gbMenuRepo.getAttachment(menuId, name);
        if (attachment != null) {
            return new DownloadingView(attachment.getName(),
                    attachment.getMimeContentType(), attachment.getContents());
        }
        return new RedirectView("/", true);
    }
}
