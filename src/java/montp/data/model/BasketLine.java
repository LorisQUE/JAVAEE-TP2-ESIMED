package montp.data.model;

import montp.data.model.security.User;
import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="BASKET_LINE")
public class BasketLine extends GenericEntity{

    public BasketLine() { }

    public BasketLine(User user, String company, Double baseQuote) {
        this.user = user;
        this.company = company;
        this.baseQuote = baseQuote;
        this.currentQuote = baseQuote;
    }

    @OneToOne
    private User user;
    private String company;
    private Double baseQuote;
    private Double currentQuote;

    @Override
    public String toString() {
        return company + "(" + currentQuote + "€)";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Double getBaseQuote() {
        return baseQuote;
    }

    public void setBaseQuote(Double baseQuote) {
        this.baseQuote = baseQuote;
    }

    public Double getCurrentQuote() {
        return currentQuote;
    }

    public void setCurrentQuote(Double currentQuote) {
        this.currentQuote = currentQuote;
    }
}
