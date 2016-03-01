package com.ksu.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by Ksu on 01.03.2016.
 */
public class NavigationHelper {
    private FirefoxDriver wd;

    public NavigationHelper(FirefoxDriver wd) {
        this.wd = wd;
    }

    public void goToHomePage() {
        wd.findElement(By.linkText("home")).click();
    }

    public void goToGroupPage() {
        wd.findElement(By.linkText("groups")).click();
    }
}
