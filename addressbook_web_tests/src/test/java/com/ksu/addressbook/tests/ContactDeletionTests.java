package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.ContactData;
import com.ksu.addressbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Ksu on 02.03.2016.
 */
public class ContactDeletionTests extends TestBase{

    @Test
    public void testContactDeletion(){
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
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactDeletion();
        app.getContactHelper().acceptAlert();
        app.getNavigationHelper().goToHomePage();
        int after = app.getContactHelper().getContactCount();
        Assert.assertEquals(after, before - 1);
    }
}
