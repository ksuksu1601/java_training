package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.ContactData;
import com.ksu.addressbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

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

        List<ContactData> before = app.getContactHelper().getContactList();
        int index = before.size() - 2;
        ContactData contact = new ContactData(before.get(index).getId(), "Fekla", "Pupyrkina2", "FeklaP", "The Mars, 1st street", "000", "111", "222", "333", "fekla.pupyrkina@ino.planet", null);
        app.getContactHelper().initContactModification(index);
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(index);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before);
    }
}
