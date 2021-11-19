package montp.web.controllers;

import montp.api.Company;
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

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@ViewScoped
@Named("index")
public class IndexView implements Serializable {

    @Inject private UserSession session;

    @Inject private UserService userService;
    @Inject private BasketLineService basketLineService;
    @Inject private EMailer eMailer;
    @Inject private Messages messages;
    @Inject private StockMarket stockMarket;

    private String emailTo;

    @PostConstruct
    public void init() {
        Logger.log(Logger.LogLevel.INFO, IndexView.class.getSimpleName(), "initializing view controller");
    }

    public String getHello() {
        return String.format(messages.get("basket"), session.getUser());
    }

    public Collection<BasketLine> getBasketLines() { return basketLineService.getUserBasket(session.getUser()); }

    public boolean isUserActive(User user) { return userService.isActive(user); }

    public void sendMail() {
        if (!emailTo.strip().isEmpty()) {
            try {
                eMailer.send(emailTo, messages.get("example.mailsubject"), messages.get("example.mailcontent"));
                FacesTools.addMessage(FacesMessage.SEVERITY_INFO, messages.get("example.mailsent"));
            } catch (MessagingException e) {
                FacesTools.addMessage(FacesMessage.SEVERITY_ERROR, messages.get("example.mailerror"), e.getMessage());
            }
        }
    }

    public String getEmailTo() { return emailTo;   }
    public void setEmailTo(String emailTo) {  this.emailTo = emailTo;  }
}
