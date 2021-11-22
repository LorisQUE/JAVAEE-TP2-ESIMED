package montp.web.controllers;

import montp.data.model.security.User;
import montp.services.UserService;
import montp.tools.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named("register")
public class RegisterView implements Serializable {

    @Inject
    private UserService userService;
    private User user;

    @PostConstruct
    public void init() {
        this.user = new User();
    }

    public void register() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
