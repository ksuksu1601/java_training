package com.ksu.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by Ksu on 10.04.2016.
 */
public class RegistrationHelper extends HelperBase{

    public RegistrationHelper(ApplicationManager app) {
        super(app);
    }

    public void start(String username, String email) {
        wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
        type(By.name("username"), username);
        type(By.name("email"), email);
        click(By.cssSelector("input[value='Signup']"));
    }

    public void finish(String confirmationLink, String newpassword) {
        wd.get(confirmationLink);
        type(By.name("password"), newpassword);
        type(By.name("password_confirm"), newpassword);
        click(By.cssSelector("input[value='Update User']"));
    }
}
