package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.ContactData;
import com.ksu.addressbook.model.Contacts;
import com.ksu.addressbook.model.GroupData;
import com.ksu.addressbook.model.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

/**
 * Created by Ksu on 02.03.2016.
 */
public class ContactDeletionFromGroupTests extends TestBase{

    private ContactData contactToDeleteFromGroup;
    private GroupData groupToDeleteFrom;

    @BeforeMethod
    public void ensurePreconditions() {

        if (app.db().groups().size() == 0){
            GroupData group = new GroupData().withName("GroupForContact" + (System.currentTimeMillis()/1000)).withHeader("").withFooter("");
            app.goTo().groupPage();
            app.group().create(group);
        }

        if (app.db().contacts().size() == 0){
            app.goTo().homePage();
            app.contact().create(new ContactData().withFirstname("Fekla").withLastname("Pupyrkina").withNickname("FeklaP")
                    .withAddress("The Mars, 1st street").withFax("000").withMobilePhone("111").withHomePhone("222")
                    .withWorkPhone("333").withEmail("fekla.pupyrkina@ino.planet").inGroup(app.db().groups().iterator().next()));
        }

        for (GroupData group : app.db().groups()){
            if (group.getContacts().size() != 0){
                groupToDeleteFrom = group;
                contactToDeleteFromGroup = group.getContacts().iterator().next();
                break;
            }
        }

        if ((contactToDeleteFromGroup == null) || (groupToDeleteFrom == null)){
            groupToDeleteFrom = app.db().groups().iterator().next();
            contactToDeleteFromGroup = app.db().contacts().iterator().next();
            app.contact().addToGroup(contactToDeleteFromGroup, groupToDeleteFrom);
            groupToDeleteFrom = groupToDeleteFrom.withContact(contactToDeleteFromGroup);
            contactToDeleteFromGroup = contactToDeleteFromGroup.inGroup(groupToDeleteFrom);
        }

        app.goTo().homePage();
    }

    @Test
    public void testContactDeletionFromGroup(){
        Contacts beforeContacts = groupToDeleteFrom.getContacts();
        Groups beforeGroups = contactToDeleteFromGroup.getGroups();

        app.contact().deleteFromGroup(contactToDeleteFromGroup, groupToDeleteFrom);

        Contacts afterContacts = app.db().groupById(groupToDeleteFrom.getId()).getContacts();
        Groups afterGroups = app.db().contactById(contactToDeleteFromGroup.getId()).getGroups();

        assertEquals(afterContacts.size(), beforeContacts.size() - 1);
        assertEquals(afterGroups.size(), beforeGroups.size() - 1);
        assertThat(afterContacts, equalTo(beforeContacts.without(contactToDeleteFromGroup)));
        assertThat(afterGroups, equalTo(beforeGroups.without(groupToDeleteFrom)));
    }

}
