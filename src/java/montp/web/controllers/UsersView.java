package montp.web.controllers;

import montp.data.model.security.User;
import montp.services.UserService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named("users")
public class UsersView implements Serializable {
    @Inject private UserService userService;
    private List<User> users;

    @PostConstruct
    public void init() {
        users = userService.getUsers();
    }

    public void onCheckboxClick(User user){
        if (!isUserActive(user)) {
            userService.enable(user);
        } else {
            userService.disable(user);
        }
    }

    public boolean isUserActive(User user) { return userService.isActive(user); }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
