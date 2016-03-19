package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.ContactData;
import com.ksu.addressbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase{

    String groupForContact;

    @BeforeMethod
    public void ensurePreconditions(){
        groupForContact = "GroupForContact";
        app.goTo().groupPage();
        if(! app.group().isThereASpecificGroup(groupForContact)){
            app.group().create(new GroupData(groupForContact, null, null));
        }
        app.goTo().homePage();
    }

    @Test(enabled = true)
    public void testContactCreation() {
        List<ContactData> before = app.contact().list();
        ContactData contact = new ContactData("Fekla", "Pupyrkina", "FeklaP", "The Mars, 1st street", "000", "111", "222", "333", "fekla.pupyrkina@ino.planet", groupForContact);
        app.contact().create(contact);
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() + 1);

        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before);

    }

}
