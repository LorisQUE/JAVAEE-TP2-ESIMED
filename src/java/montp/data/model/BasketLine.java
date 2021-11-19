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

    @OneToOne
    private User user;
    private String company;
    private Double quote;

    @Override
    public String toString() {
        return company + "(" + quote + "€)";
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

    public Double getQuote() {
        return quote;
    }

    public void setQuote(Double quote) {
        this.quote = quote;
    }
}
