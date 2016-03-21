package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.ContactData;
import com.ksu.addressbook.model.Contacts;
import com.ksu.addressbook.model.GroupData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.assertEquals;

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
        Contacts before = app.contact().all();
        ContactData contact = new ContactData().withFirstname("Fekla").withLastname("Pupyrkina").withNickname("FeklaP").
                withAddress("The Mars, 1st street").withFax("000").withMobilePhone("111").withHomePhone("222").
                withWorkPhone("333").withEmail("fekla.pupyrkina@ino.planet").withGroup(groupForContact);
        app.contact().create(contact);
        Contacts after = app.contact().all();
        assertEquals(after.size(), before.size() + 1);
        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt()))));

    }

}
