package com.ksu.mantis.tests;

import com.ksu.mantis.appmanager.ApplicationManager;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

/**
 * Created by Ksu on 29.02.2016.
 */
public class TestBase {

    protected final static ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
        app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.backup");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws IOException {
        app.ftp().restore("config_inc.php.backup", "config_inc.php");
        app.stop();
    }

    public boolean isIssueOpen(int issueId) throws RemoteException, ServiceException, MalformedURLException {
        if (!app.soap().getIssue(issueId).getState().equals("closed"))
            return true;
        return false;
    }

    public boolean isIssueOpenInBugify(int issueId) throws IOException {
        if (!app.rest().getIssue(issueId).getState().equals("Closed"))
            return true;
        return false;
    }

    public void skipIfNotFixed(int issueId) throws RemoteException, ServiceException, MalformedURLException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    public void skipIfNotFixedInBugify(int issueId) throws IOException {
        if (isIssueOpenInBugify(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }
}
