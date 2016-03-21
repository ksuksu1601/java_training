package com.ksu.addressbook.appmanager;

import com.ksu.addressbook.model.ContactData;
import com.ksu.addressbook.model.Contacts;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;


/**
 * Created by Ksu on 01.03.2016.
 */
public class ContactHelper extends HelperBase{

    private Contacts contactsCache = null;

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void sumbitCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"),contactData.getFirstname());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("nickname"), contactData.getNickname());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("fax"), contactData.getFax());
        type(By.name("email"), contactData.getEmail());

        if (creation){
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initCreation() {
        click(By.linkText("add new"));
    }

    public void selectById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void initModificationById(int id) {
        wd.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']")).click();
    }

    public void submitModification() {
        click(By.name("update"));
    }

    public void initDeletion() {
        click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    }

    public void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public void goToHomePage(){
        click(By.linkText("home"));
    }

    public void create(ContactData contact) {
        initCreation();
        fillContactForm(contact, true);
        sumbitCreation();
        contactsCache = null;
        returnToHomePage();
    }

    public void delete(ContactData contact) {
        selectById(contact.getId());
        initDeletion();
        acceptAlert();
        contactsCache = null;
        goToHomePage();
    }

    public void modify(ContactData contact) {
        initModificationById(contact.getId());
        fillContactForm(contact, false);
        submitModification();
        contactsCache = null;
        returnToHomePage();
    }

    public Contacts all() {
        if(contactsCache != null){
            return new Contacts(contactsCache);
        }

        contactsCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.xpath("//table[@id='maintable']/tbody/tr[@name='entry']"));
        for(WebElement el: elements){
            String lastName = el.findElement(By.xpath("td[2]")).getText();
            String firstName = el.findElement(By.xpath("td[3]")).getText();
            String address = el.findElement(By.xpath("td[4]")).getText();
            String email = el.findElement(By.xpath("td[5]")).getText();
            int id = Integer.parseInt(el.findElement(By.tagName("input")).getAttribute("value"));
            ContactData contact = new ContactData().withId(id).withFirstname(firstName).withLastname(lastName)
                    .withAddress(address).withEmail(email);
            contactsCache.add(contact);
        }
        return new Contacts(contactsCache);
    }

}
