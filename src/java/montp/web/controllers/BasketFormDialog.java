package montp.web.controllers;

import montp.api.Company;
import montp.api.Quote;
import montp.api.StockMarket;
import montp.data.model.BasketLine;
import montp.locale.Messages;
import montp.services.BasketLineService;
import montp.web.FacesTools;
import montp.web.UserSession;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ViewScoped
@Named("basketFormDialog")
public class BasketFormDialog implements Serializable {

    @Inject private UserSession session;
    @Inject private BasketLineService service;
    @Inject private StockMarket stockMarket;
    private Messages message;
    protected IndexView indexView;
    private List<String> companies;


    public void create(IndexView indexView) {
        this.indexView = indexView;
    }

    public void save() {

        // TODO : Vérifier que les compagnies reçu ne sont pas en doublon,
        //  et qu'elle ne soient pas déjà présentent sur le panier

        Set<String> quotesSet = new HashSet<>(companies);
        Collection<Quote> quoteCollection = stockMarket.getQuotes(quotesSet);

        for (String symbol : companies) {
            Company company = stockMarket.getCompany(symbol);

            Quote quote = quoteCollection.stream()
                    .filter(q -> company.equals(q.getCompany()))
                    .findFirst()
                    .orElse(null);

            BasketLine basketLine = new BasketLine(
                    session.getUser(),
                    company.getSymbol(),
                    quote.getQuote()
            );

            service.insert(basketLine);
        }

        indexView.init();
        
    }

    public List<String> getCompanies() {
        return companies;
    }

    public void setCompanies(List<String> companies) {
        this.companies = companies;
    }

    public Collection<Company> completeCompany(String query) {
        return stockMarket.getCompanies(query);
    }
}
