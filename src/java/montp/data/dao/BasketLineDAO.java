package montp.data.dao;

import montp.data.model.BasketLine;
import montp.data.model.security.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class BasketLineDAO extends GenericDAO<BasketLine>{

    public BasketLineDAO() {
        super(BasketLine.class);
    }

    public List<BasketLine> getUserBasket(User user) {
        return em.createQuery("SELECT b FROM BasketLine b WHERE b.user = :user ORDER BY b.company")
                .setParameter("user", user)
                .getResultList();
    }

    public Set<String> getAllUniqueSymbols() {
        List<String> result = em.createQuery("SELECT DISTINCT b.company FROM BasketLine b ORDER BY b.company")
                .getResultList();
        return new HashSet<>(result);
    }

    public void updateCurrentQuote(String company, Double currentQuote) {
        em.createQuery("UPDATE BasketLine b SET b.currentQuote = :currentQuote WHERE b.company = :company")
                .setParameter("currentQuote", currentQuote)
                .setParameter("company", company)
                .getResultList();
    }

}
