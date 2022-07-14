package group.project.dao;

import group.project.model.Attachment;
import group.project.model.Menu;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface MenuRepository {

    public long addMenu(String name, String description, double price,String available,
            List<MultipartFile> attachments) throws IOException;

    public List<Menu> getMenus();

    public List<Menu> getMenu(long id);

    public void updateMenu(long menu_id, String name, String description, double price,String available,
            List<MultipartFile> attachments) throws IOException;

    public void deleteMenu(long id);

    public void deleteAttachment(long menu_id, String name);

    public Attachment getAttachment(long menu_id, String name);
}
