package montp.web.controllers;

import montp.api.Quote;
import montp.api.StockMarket;
import montp.data.model.BasketLine;
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
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@ViewScoped
@Named("index")
public class IndexView implements Serializable {

    @Inject Messages message;
    @Inject private UserSession session;
    @Inject private UserSession userSession;
    @Inject private UserService userService;
    @Inject private BasketLineService basketLineService;
    @Inject private EMailer eMailer;
    @Inject private Messages messages;
    @Inject private StockMarket stockMarket;

    private Collection<BasketLine> lines;

    @PostConstruct
    public void init() {
        lines = basketLineService.getUserBasket(session.getUser());
        actualizeQuote();
    }

    public String getVariation(BasketLine line) {
        Double baseQuote = line.getBaseQuote();
        Double currentQuote = line.getCurrentQuote();
        if(Objects.equals(line.getCurrentQuote(), baseQuote)) return "-";

        double percent = ((currentQuote - baseQuote)/baseQuote) * 100;

        percent = BigDecimal.valueOf(percent)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        String symbol = currentQuote > baseQuote ? "+" : "";

        return symbol + percent + "%";
    }

    public void actualizeQuote() {
        Logger.log(Logger.LogLevel.INFO, IndexView.class.getSimpleName(), "Update and actualize quote");

        // Méthode pour le poll
        // TODO :
        //  On récupère toutes les lignes du panier
        //  On créer une liste de symbole UNIQUE de chaque compagnie
        //  Pour chaque symbole on récupère leurs cotation associé
        //  Et on update toutes lignes ayant ce symbole

        Set<String> symbols = basketLineService.getAllUniqueSymbols();
        Collection<Quote> quotes = stockMarket.getQuotes(symbols);

        for (Quote quote :quotes) {
            basketLineService.updateCurrentQuote(quote.getCompany().getSymbol(), quote.getQuote());
        }
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
