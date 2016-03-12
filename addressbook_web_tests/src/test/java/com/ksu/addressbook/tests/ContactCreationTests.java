package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.ContactData;
import com.ksu.addressbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase{

    @Test
    public void testContactCreation() {
        String groupForContact = "GroupForContact";
        app.getNavigationHelper().goToGroupPage();
        if(! app.getGroupHelper().isThereASpecificGroup(groupForContact)){
            app.getGroupHelper().createGroup(new GroupData(groupForContact, null, null));
        }
        app.getNavigationHelper().goToHomePage();
        int before = app.getContactHelper().getContactCount();
        app.getContactHelper().createContact(new ContactData("Fekla", "Pupyrkina", "FeklaP", "The Mars, 1st street", "000", "111", "222", "333", "fekla.pupyrkina@ino.planet", groupForContact));
        int after = app.getContactHelper().getContactCount();
        Assert.assertEquals(after, before + 1);
    }

}
