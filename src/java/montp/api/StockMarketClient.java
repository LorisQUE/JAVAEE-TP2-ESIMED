package tp.javaee.stockmarket;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Set;

@ApplicationScoped
public class StockMarketClient implements StockMarket {

    //private static final String API_URL = "http://localhost:8080/stockmarket/api/stockmarket/";
    private static final String API_URL = "https://extranet.esimed.fr/StockMarket/api/stockmarket/";

    private Client client = ClientBuilder.newClient();
    private WebTarget api = client.target(API_URL);

    @PreDestroy
    public void done() {
        client.close();
    }

    public Collection<Company> getCompanies() {
        return api
                .path("companies")
                .request()
                .get(new GenericType<Collection<Company>>() {});
    }

    public Company getCompany(String symbol) {
        return api
                .path("company")
                .path(symbol)
                .request()
                .get(Company.class);
    }

    public Collection<Company> getCompanies(String search) {
        return api
                .path("companies")
                .path(search)
                .request()
                .get(new GenericType<Collection<Company>>() {});
    }

    public Collection<Quote> getQuotes(Set<String> companiesSymbols) {
        return api
                .path("quotes")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(companiesSymbols), new GenericType<Collection<Quote>>() {});
    }

}
