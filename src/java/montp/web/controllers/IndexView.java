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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@ViewScoped
@Named("index")
public class IndexView implements Serializable {

    @Inject Messages message;
    @Inject private UserSession session;
    @Inject private UserService userService;
    @Inject private BasketLineService basketLineService;
    @Inject private EMailer eMailer;
    @Inject private Messages messages;
    @Inject private StockMarket stockMarket;

    private Collection<BasketLine> lines;

    @PostConstruct
    public void init() {
        lines = basketLineService.getUserBasket(session.getUser());
        Logger.log(Logger.LogLevel.INFO, IndexView.class.getSimpleName(), "initializing view controller");
    }

    public String getVariation(Double currentQuote, Double oldQuote) {
        if(Objects.equals(currentQuote, oldQuote)) return "-";

        double percent = ((currentQuote - oldQuote)/oldQuote) * 100;

        percent = BigDecimal.valueOf(percent)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        String symbol = currentQuote > oldQuote ? "+" : "";

        return symbol + percent + "%";
    }

    public void deleteLine(BasketLine line) {

        if(line != null) {
            basketLineService.delete(line);
            lines.remove(line);
            FacesTools.addMessage(FacesMessage.SEVERITY_INFO, message.get("app.delete"));
        } else {
            FacesTools.addMessage(FacesMessage.SEVERITY_ERROR, message.get("app.delete.error"));
        }

    }

    public Collection<BasketLine> getLines() {
        return lines;
    }

    public void setLines(Collection<BasketLine> lines) {
        this.lines = lines;
    }
}
