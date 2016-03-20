package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.ContactData;
import com.ksu.addressbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;

public class ContactCreationTests extends TestBase{

    String groupForContact;

    @BeforeMethod
    public void ensurePreconditions(){
        groupForContact = "GroupForContact";
        app.goTo().groupPage();
        if(! app.group().isThereASpecificGroup(groupForContact)){
            app.group().create(new GroupData().withName(groupForContact));
        }
        app.goTo().homePage();
    }

    @Test(enabled = true)
    public void testContactCreation() {
        Set<ContactData> before = app.contact().all();
        ContactData contact = new ContactData().withFirstname("Fekla").withLastname("Pupyrkina").withNickname("FeklaP").
                withAddress("The Mars, 1st street").withFax("000").withMobilePhone("111").withHomePhone("222").
                withWorkPhone("333").withEmail("fekla.pupyrkina@ino.planet").withGroup(groupForContact);
        app.contact().create(contact);
        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size() + 1);

        contact.withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt());
        before.add(contact);
        Assert.assertEquals(after, before);

    }

}
