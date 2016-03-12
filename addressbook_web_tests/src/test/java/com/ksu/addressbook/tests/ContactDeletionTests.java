package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.ContactData;
import com.ksu.addressbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

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
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size() - 1);
        app.getContactHelper().initContactDeletion();
        app.getContactHelper().acceptAlert();
        app.getNavigationHelper().goToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() - 1);
    }
}
