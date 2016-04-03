package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.ContactData;
import com.ksu.addressbook.model.Contacts;
import com.ksu.addressbook.model.GroupData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.*;

/**
 * Created by Ksu on 02.03.2016.
 */
public class ContactDeletionTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0){
            GroupData groupForContact = new GroupData().withName("GroupForContact").withHeader("").withFooter("");
            if(! app.db().groups().stream().map(g -> g.withId(Integer.MAX_VALUE)).collect(Collectors.toList()).contains(groupForContact)){
                app.goTo().groupPage();
                app.group().create(groupForContact);
            }
            app.goTo().homePage();
            app.contact().create(new ContactData().withFirstname("Fekla").withLastname("Pupyrkina").withNickname("FeklaP")
                    .withAddress("The Mars, 1st street").withFax("000").withMobilePhone("111").withHomePhone("222")
                    .withWorkPhone("333").withEmail("fekla.pupyrkina@ino.planet").withGroup(groupForContact.getName()));
        }
    }

    @Test
    public void testContactDeletion(){
        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        assertEquals(app.contact().getContactCount(), before.size() - 1);
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(deletedContact)));
    }

}
