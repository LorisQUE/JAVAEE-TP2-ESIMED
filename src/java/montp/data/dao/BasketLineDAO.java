package montp.data.dao;

import montp.data.model.BasketLine;
import montp.data.model.security.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

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

}
