package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.ContactData;
import com.ksu.addressbook.model.GroupData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Ksu on 22.03.2016.
 */
public class ContactEmailTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
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
    public void testContactEmail(){
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    }

    public String mergeEmails(ContactData contact){
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter(s -> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }
}
