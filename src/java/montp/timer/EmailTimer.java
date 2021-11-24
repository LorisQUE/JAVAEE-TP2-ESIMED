package montp.timer;

import montp.api.Quote;
import montp.api.StockMarket;
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
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Stateless
public class EmailTimer {
    @Inject private StockMarket stockMarket;
    @Inject private UserService userService;
    @Inject private BasketLineService basketLineService;
    @Inject private EMailer eMailer;
    @Inject private Messages messages;
    @Inject IndexView indexView;

    @Schedule(minute = "*/15", hour = "*")
    public void sendEmail() throws MessagingException {
        Logger.log(Logger.LogLevel.INFO, IndexView.class.getSimpleName(), "Sending Mails ...");
        List<User> users = userService.getUsers();

        // Pour chaque user actif et ayant au moins une ligne dans leur panier, on envoie un mail
        for(User user : users) {
            if(userService.isActive(user)) {
                List<BasketLine> basketLines = basketLineService.getUserBasket(user);
                if(basketLines.size() > 0) {
                    StringBuilder msg = new StringBuilder();
                    for(BasketLine line : basketLines) {
                        msg.append(line.getCompany()).append(" - ").append(line.getCurrentQuote()).append(" (").append(indexView.getVariation(line)).append(")");
                    }

                    eMailer.send(user.getEmail(), messages.get("example.mailsubject"), msg.toString());
                    FacesTools.addMessage(FacesMessage.SEVERITY_INFO, msg.toString());
                }
            }
        }
    }

}
