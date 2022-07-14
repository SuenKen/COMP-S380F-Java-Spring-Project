package group.project.dao;

import group.project.model.SystemUser;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class SystemUserService implements UserDetailsService {

    @Resource
    SystemUserRepository SystemUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        List<SystemUser> users = SystemUserRepo.getUserByUsername(username);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        SystemUser user = users.get(0);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
