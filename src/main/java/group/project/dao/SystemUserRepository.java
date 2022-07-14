/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group.project.dao;

import group.project.model.SystemUser;
import java.util.List;

public interface SystemUserRepository {

    public void addUser(SystemUser e);

    public void updateUser(SystemUser e);

    public List<SystemUser> listUser();

    public List<SystemUser> getUserByUsername(String username);

    public void removeUserByUsername(String username);
}
