package group.project.dao;

import group.project.model.Favorite;
import java.util.List;

public interface FavoriteRepository {

    public int addFavorite(Favorite comment);

    public List<Favorite> listFavoriteByUsername(String username);

    public int removeFavoriteByUsernameAndMenu(int id,String username);
}
