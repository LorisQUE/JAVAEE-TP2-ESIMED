package montp.services;

import montp.data.dao.BasketLineDAO;
import montp.data.model.BasketLine;
import montp.data.model.security.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class BasketLineService extends GenericService<BasketLine, BasketLineDAO> {

    public List<BasketLine> getUserBasket(User user) {
        return dao.getUserBasket(user);
    }

    public Set<String> getAllUniqueSymbols() {
        return dao.getAllUniqueSymbols();
    }

    public void updateCurrentQuote(String company, Double currentQuote) { dao.updateCurrentQuote(company, currentQuote); }
}
