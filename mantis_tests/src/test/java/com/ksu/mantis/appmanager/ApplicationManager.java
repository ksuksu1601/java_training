package com.ksu.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ksu on 29.02.2016.
 */
public class ApplicationManager {
    private final Properties properties;
    private WebDriver wd;
    private String browser;
    private AccountHelper accountHelper;
    private RegistrationHelper registrationHelper;
    private FtpHelper ftpHelper;
    private MailHelper mailHelper;
    private DbHelper dbHelper;
    private SoapHelper soapHelper;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    }

    public void stop() {
        if (wd != null){
            wd.quit();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public HttpSession newSession(){
        return new HttpSession(this);
    }

    public FtpHelper ftp(){
        if ( ftpHelper == null){
            ftpHelper = new FtpHelper(this);
        }
        return ftpHelper;
    }

    public DbHelper db(){
        if ( dbHelper == null){
            dbHelper = new DbHelper(this);
        }
        return dbHelper;
    }

    public RegistrationHelper registration() {
        if (registrationHelper == null){
            registrationHelper = new RegistrationHelper(this);
        }
        return registrationHelper;
    }

    public AccountHelper account(){
        if (accountHelper == null){
            accountHelper = new AccountHelper(this);
        }
        return accountHelper;
    }

    public WebDriver getDriver() {
        if (wd == null){
            if (browser.equals(BrowserType.FIREFOX)){
                wd = new FirefoxDriver();
            } else if (browser.equals(BrowserType.CHROME)){
                wd = new ChromeDriver();
            } else if (browser.equals(BrowserType.IE)){
                wd = new InternetExplorerDriver();
            }

            wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            wd.get(properties.getProperty("web.baseUrl"));
        }
        return wd;
    }

    public MailHelper mail(){
        if (mailHelper == null){
            mailHelper = new MailHelper(this);
        }
        return mailHelper;
    }

    public SoapHelper soap(){
        if (soapHelper == null){
            soapHelper = new SoapHelper(this);
        }
        return soapHelper;
    }
}
