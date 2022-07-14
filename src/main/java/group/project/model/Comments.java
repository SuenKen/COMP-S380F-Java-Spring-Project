package group.project.model;

public class Comments {

    private Integer id;
    private Integer menu_id;
    private String context;
    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMenuId() {
        return menu_id;
    }

    public void setMenuId(Integer menu_id) {
        this.menu_id = menu_id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
