package group.project.dao;

import group.project.model.Comments;
import java.util.List;

public interface CommentsRepository {

    public void addComments(Comments comment);

    public void updateComments(Comments comment);

    public List<Comments> listComments();
    
        public List<Comments> listCommentsByMenuId(int id);

    public Comments getCommentsById(int id);

    public void removeCommentsById(int id);
}
