package montp.timer;

import montp.data.model.BasketLine;
import montp.data.model.security.User;
import montp.locale.Messages;
import montp.services.BasketLineService;
import montp.services.UserService;
import montp.tools.EMailer;
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
    @Inject IndexView indexView;

    @Schedule(minute = "*/15", hour = "*")
    public void sendEmail() throws MessagingException {
        List<User> users = userService.getUsers();


        // Pour chaque user actif et ayant au moins une ligne dans leur panier, on envoie un mail
        for(User user : users) {
            if(userService.isActive(user)) {
                List<BasketLine> basketLines = basketLineService.getUserBasket(user);
                if(basketLines.size() > 0) {
                    String msg = "";
                    for(BasketLine line : basketLines) {
                        msg += line.getCompany() + " - " + line.getCurrentQuote() + " (" + indexView.getVariation(line) + ")";
                    }

                    eMailer.send("lquetglas@esimed.fr", messages.get("example.mailsubject"), user.getEmail());
                    FacesTools.addMessage(FacesMessage.SEVERITY_INFO, messages.get("example.mailsent"));
                }
            }
        }
    }

}
