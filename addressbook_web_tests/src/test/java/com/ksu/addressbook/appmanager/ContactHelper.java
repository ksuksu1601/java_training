package com.ksu.addressbook.appmanager;

import com.ksu.addressbook.model.ContactData;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ksu on 01.03.2016.
 */
public class ContactHelper extends HelperBase{

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void sumbitContactCreation() {
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

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void initContactModification(int index) {
        wd.findElements(By.xpath("//table[@id='maintable']/tbody/tr/td/a/img[@title='Edit']")).get(index).click();
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void initContactDeletion() {
        click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    }

    public void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public void createContact(ContactData contactData) {
        initContactCreation();
        fillContactForm(contactData, true);
        sumbitContactCreation();
        returnToHomePage();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.xpath("//table[@id='maintable']/tbody/tr[@name='entry']"));
        for(WebElement el: elements){
            String lastName = el.findElement(By.xpath("td[2]")).getText();
            String firstName = el.findElement(By.xpath("td[3]")).getText();
            String address = el.findElement(By.xpath("td[4]")).getText();
            String email = el.findElement(By.xpath("td[5]")).getText();
            int id = Integer.parseInt(el.findElement(By.tagName("input")).getAttribute("value"));
            ContactData contact = new ContactData(id, firstName, lastName, null, address, null, null, null, null, email, null);
            contacts.add(contact);
        }
        return contacts;
    }

//    public boolean isAGroupInSelectList(String groupName){
//        return isElementPresent(By.xpath("//select[@name='new_group']/option[normalize-space(text())='" + groupName + "']"));
//    }

}
