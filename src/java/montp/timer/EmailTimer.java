package montp.timer;

import montp.data.model.BasketLine;
import montp.data.model.security.User;
import montp.locale.Messages;
import montp.services.BasketLineService;
import montp.services.UserService;
import montp.tools.EMailer;
import montp.tools.Logger;
import montp.web.FacesTools;
import montp.web.controllers.IndexView;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.mail.MessagingException;
import java.util.List;

@Stateless
public class EmailTimer {
    @Inject private UserService userService;
    @Inject private BasketLineService basketLineService;
    @Inject private EMailer eMailer;
    @Inject private Messages messages;



    @Schedule(minute = "*/15", hour = "*")
    public void sendEmail() throws MessagingException {
        Logger.log(Logger.LogLevel.INFO, IndexView.class.getSimpleName(), "Email envoyé !");

        List<User> users = userService.getUsers();

        for(User user : users) {
            if(userService.isActive(user)) {
                if(basketLineService.getUserBasket(user).size() > 0) {
                    // TODO : Envoie de mail à l'utilisateur ici
                    //eMailer.send(user.getEmail(), messages.get("example.mailsubject"), messages.get("example.mailcontent"));
                    //FacesTools.addMessage(FacesMessage.SEVERITY_INFO, messages.get("example.mailsent"));
                }
            }

        }
    }

}
