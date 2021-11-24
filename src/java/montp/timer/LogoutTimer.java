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
import montp.web.UserSession;
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
public class LogoutTimer {
    @Inject private UserSession userSession;
    @Inject private UserService userService;

    @Schedule(second = "*/5", minute = "*", hour = "*")
    public void loggingOut() {
        Logger.log(Logger.LogLevel.INFO, IndexView.class.getSimpleName(), "updateQuotesAndSendMail");
        User user = userSession.getUser();

        if(user != null && !userService.isActive(user)) {
            userSession.logout();
            FacesTools.redirect("index");
        }

    }

}
