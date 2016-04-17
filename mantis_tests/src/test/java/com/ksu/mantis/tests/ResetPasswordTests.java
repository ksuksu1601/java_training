package com.ksu.mantis.tests;

import com.ksu.mantis.model.MailMessage;
import com.ksu.mantis.model.UserData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;

import javax.mail.MessagingException;
import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * Created by Ksu on 10.04.2016.
 */
public class ResetPasswordTests extends TestBase{

    @BeforeMethod
    public void startMailServer(){
        app.mail().start();
    }

    @Test
    public void testResetPassword() throws IOException, MessagingException, ServiceException {
        skipIfNotFixed(1);
        UserData user = app.db().users().iterator().next();
        String newPassword = "newpassword";
        app.account().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPswd"));
        app.account().resetUserPassword(user.name());
        app.account().logout();
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = findConfirmationLink(mailMessages, user.email());
        app.registration().finish(confirmationLink, newPassword);
        assertTrue(app.newSession().login(user.name(), newPassword));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findAny().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer(){
        app.mail().stop();
    }
}
