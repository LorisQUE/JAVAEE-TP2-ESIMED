package montp.web.controllers;

import montp.data.model.security.Group;
import montp.data.model.security.User;
import montp.services.UserService;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named("register")
public class RegisterView implements Serializable {

    @Inject private HttpServletRequest httpServletRequest;
    @Inject private UserService userService;
    private User user;

    @PostConstruct
    public void init() {
        this.user = new User();
    }

    public void register() throws Exception {

        // TODO : Après inscription, autoconnect et redirect sur l'index
        if(user != null) {
            String email = user.getEmail();
            String password = user.getPassword();

            List<Group> groupes = new ArrayList<>();
            groupes.add(userService.getGroup("USER"));
            user.setGroups(groupes);
            userService.insert(user);

            httpServletRequest.login(email, password);
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
