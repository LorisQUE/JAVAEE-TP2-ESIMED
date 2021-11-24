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
public class UpdateQuotesTimer {
    @Inject private StockMarket stockMarket;
    @Inject private BasketLineService basketLineService;

    @Schedule(minute = "*/5", hour = "*")
    public void updateQuotes() {
        Logger.log(Logger.LogLevel.INFO, IndexView.class.getSimpleName(), "Updating quotes...");

        Set<String> symbols = basketLineService.getAllUniqueSymbols();
        Collection<Quote> quotes = stockMarket.getQuotes(symbols);

        for (Quote quote :quotes) {
            basketLineService.updateCurrentQuote(quote.getCompany().getSymbol(), quote.getQuote());
        }
    }
}
