package montp.services;

import montp.locale.Messages;
import montp.tools.EMailer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EMailerService extends EMailer {

    @Inject private Messages messages;

    public EMailerService() {
        super("mail.esimed.fr",
               "exempletp@esimed.fr",
               "!Azerty123",
               587);
    }

    @Override
    public String getFrom() {
        return String.format("%s <%s>", messages.get("app.title"), getUser());
    }
}
