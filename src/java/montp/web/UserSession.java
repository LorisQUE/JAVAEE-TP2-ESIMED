package montp.web;

import montp.data.model.security.User;
import montp.services.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
public class UserSession implements Serializable {

    @Inject
    private UserService userService;
    private User user;
    

    @PostConstruct
    public void init() {
        // TODO : Réussir à récup le bon user après connexion
        //String userS = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        //String userName = FacesTools.getRequest().getUserPrincipal().getName();

        //if (userName != null) {
            //this.user = userService.getFromUsername(userName);
            //String test = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
            //String test2 = FacesTools.getRequest().getUserPrincipal().getName();
        //}

        //user = userService.getFromUsername(FacesTools.getRequest().getUserPrincipal().getName()); // si authentification activée
        user = userService.getFromUsername("admin"); // désactiver la sécurité dans web.xml pour l'autologin
    }
    
    public void logout() {
        FacesTools.getRequest().getSession().invalidate();
        FacesTools.redirect("index");
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public boolean isAdmin() {
        return FacesTools.getRequest().isUserInRole("ADMIN");
    }
}
