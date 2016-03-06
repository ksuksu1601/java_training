package com.ksu.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


/**
 * Created by Ksu on 01.03.2016.
 */
public class SessionHelper extends HelperBase {

    public SessionHelper(WebDriver wd) {
        super(wd);
    }

    public void login(String username, String password) {
        click(By.id("LoginForm"));
        type(By.name("user"), username);
        type(By.name("pass"), password);
        click(By.xpath("//form[@id='LoginForm']/input[3]"));
    }

}
