package com.ksu.addressbook.tests;

import com.ksu.addressbook.appmanager.ApplicationManager;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.*;

/**
 * Created by Ksu on 29.02.2016.
 */
public class TestBase {

    protected final static ApplicationManager app = new ApplicationManager(BrowserType.CHROME);

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite
    public void tearDown() {
        app.stop();
    }

}
