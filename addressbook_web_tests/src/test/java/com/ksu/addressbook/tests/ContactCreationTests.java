package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.ContactData;
import com.ksu.addressbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ContactCreationTests extends TestBase{

    @Test
    public void testContactCreation() {
        String groupForContact = "GroupForContact";
        app.getNavigationHelper().goToGroupPage();
        if(! app.getGroupHelper().isThereASpecificGroup(groupForContact)){
            app.getGroupHelper().createGroup(new GroupData(groupForContact, null, null));
        }
        app.getNavigationHelper().goToHomePage();
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().createContact(new ContactData("Fekla", "Pupyrkina", "FeklaP", "The Mars, 1st street", "000", "111", "222", "333", "fekla.pupyrkina@ino.planet", groupForContact));
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() + 1);
    }

}
