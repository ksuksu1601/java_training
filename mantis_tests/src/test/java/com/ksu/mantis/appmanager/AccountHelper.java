package com.ksu.mantis.appmanager;

import org.openqa.selenium.By;

/**
 * Created by Ksu on 10.04.2016.
 */
public class AccountHelper extends HelperBase{


    public AccountHelper(ApplicationManager app) {
        super(app);
    }

    public void login(String username, String password){
        wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
        type(By.name("username"), username);
        type(By.name("password"), password);
        click(By.cssSelector("input[value='Login']"));
    }

    public void logout() {
        wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
        click(By.linkText("Logout"));
    }

    public void resetUserPassword(String username){
        click(By.linkText("Manage Users"));
        click(By.linkText(username));
        click(By.cssSelector("input[value='Reset Password']"));
    }
}
