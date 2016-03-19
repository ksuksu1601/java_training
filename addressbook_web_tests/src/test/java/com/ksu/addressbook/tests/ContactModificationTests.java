package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.ContactData;
import com.ksu.addressbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Ksu on 02.03.2016.
 */
public class ContactModificationTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().homePage();
        if (app.contact().list().size() == 0){
            String groupForContact = "GroupForContact";
            app.goTo().groupPage();
            if(! app.group().isThereASpecificGroup(groupForContact)){
                app.group().create(new GroupData(groupForContact, null, null));
            }
            app.goTo().homePage();
            app.contact().create(new ContactData("Fekla", "Pupyrkina", "FeklaP", "The Mars, 1st street", "000", "111", "222", "333", "fekla.pupyrkina@ino.planet", groupForContact));
        }
    }

    @Test
    public void testContactModification(){
        List<ContactData> before = app.contact().list();
        int index = before.size() - 2;
        ContactData contact = new ContactData(before.get(index).getId(), "Fekla", "Pupyrkina2", "FeklaP", "The Mars, 1st street", "000", "111", "222", "333", "fekla.pupyrkina@ino.planet", null);
        app.contact().modify(index, contact);
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size());

        before.remove(index);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before);
    }


}
