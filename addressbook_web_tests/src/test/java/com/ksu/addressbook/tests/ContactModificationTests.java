package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.ContactData;
import com.ksu.addressbook.model.Contacts;
import com.ksu.addressbook.model.GroupData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

/**
 * Created by Ksu on 02.03.2016.
 */
public class ContactModificationTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions(){
        if (app.db().contacts().size() == 0){
            if (app.db().groups().size() == 0){
                GroupData groupForContact = new GroupData().withName("GroupForContact").withHeader("").withFooter("");
                app.goTo().groupPage();
                app.group().create(groupForContact);
            }
            app.goTo().homePage();
            app.contact().create(new ContactData().withFirstname("Fekla").withLastname("Pupyrkina").withNickname("FeklaP")
                    .withAddress("The Mars, 1st street").withFax("000").withMobilePhone("111").withHomePhone("222")
                    .withWorkPhone("333").withEmail("fekla.pupyrkina@ino.planet").inGroup(app.db().groups().iterator().next()));
        }
    }

    @Test
    public void testContactModification(){
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstname("Fekla")
                .withMiddlename("Invanovna2").withLastname("Pupyrkina2").withNickname("FeklaP").withAddress("The Mars, 1st street").withFax("000")
                .withMobilePhone("111").withHomePhone("222").withWorkPhone("333").withEmail("fekla.pupyrkina@ino.planet");
        app.goTo().homePage();
        app.contact().modify(contact);
        assertEquals(app.contact().getContactCount(), before.size());
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }


}
