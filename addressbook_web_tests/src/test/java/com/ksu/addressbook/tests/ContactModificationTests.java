package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.ContactData;
import com.ksu.addressbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Ksu on 02.03.2016.
 */
public class ContactModificationTests extends TestBase{

    @Test
    public void testContactModification(){
        app.getNavigationHelper().goToHomePage();

        if (! app.getContactHelper().isThereAContact()){
            String groupForContact = "GroupForContact";
            app.getNavigationHelper().goToGroupPage();
            if(! app.getGroupHelper().isThereASpecificGroup(groupForContact)){
                app.getGroupHelper().createGroup(new GroupData(groupForContact, null, null));
            }
            app.getNavigationHelper().goToHomePage();
            app.getContactHelper().createContact(new ContactData("Fekla", "Pupyrkina", "FeklaP", "The Mars, 1st street", "000", "111", "222", "333", "fekla.pupyrkina@ino.planet", groupForContact));
        }

        int before = app.getContactHelper().getContactCount();
        app.getContactHelper().initContactModification(before - 1);
        app.getContactHelper().fillContactForm(new ContactData("Fekla2", "Pupyrkina2", "FeklaP", "The Mars, 1st street", "000", "111", "222", "333", "fekla.pupyrkina@ino.planet", null), false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();
        int after = app.getContactHelper().getContactCount();
        Assert.assertEquals(after, before);
    }
}
