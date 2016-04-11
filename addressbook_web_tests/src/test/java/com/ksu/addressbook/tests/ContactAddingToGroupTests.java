package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.ContactData;
import com.ksu.addressbook.model.Contacts;
import com.ksu.addressbook.model.GroupData;
import com.ksu.addressbook.model.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

/**
 * Created by Ksu on 02.03.2016.
 */
public class ContactAddingToGroupTests extends TestBase{

    private ContactData contactToAddToGroup;
    private GroupData groupToAddTo;

    @BeforeMethod
    public void ensurePreconditions() {

        if (app.db().contacts().size() == 0){
            app.goTo().homePage();
            app.contact().create(new ContactData().withFirstname("Fekla").withLastname("Pupyrkina").withNickname("FeklaP")
                    .withAddress("The Mars, 1st street").withFax("000").withMobilePhone("111").withHomePhone("222")
                    .withWorkPhone("333").withEmail("fekla.pupyrkina@ino.planet"));
        }
        contactToAddToGroup = app.db().contacts().iterator().next();

        for (GroupData group : app.db().groups()){
            if (contactToAddToGroup.getGroups().stream().filter(g -> g.getName().equals(group.getName())).count() == 0){
                groupToAddTo = group;
                break;
            }
        }
        if ((app.db().groups().size() == 0) || (groupToAddTo == null)){
            GroupData group = new GroupData().withName("GroupForContact" + (System.currentTimeMillis()/1000)).withHeader("").withFooter("");
            app.goTo().groupPage();
            app.group().create(group);
            groupToAddTo = app.db().groups().stream().max((g1, g2) -> Integer.compare(g1.getId(), g2.getId())).get();
        }

        app.goTo().homePage();
    }

    @Test
    public void testContactAddingToGroup(){
        Contacts beforeContacts = groupToAddTo.getContacts();
        Groups beforeGroups = contactToAddToGroup.getGroups();

        app.contact().addToGroup(contactToAddToGroup, groupToAddTo);

        Contacts afterContacts = app.db().groupById(groupToAddTo.getId()).getContacts();
        Groups afterGroups = app.db().contactById(contactToAddToGroup.getId()).getGroups();

        assertEquals(afterContacts.size(), beforeContacts.size() + 1);
        assertEquals(afterGroups.size(), beforeGroups.size() + 1);
        assertThat(afterContacts, equalTo(beforeContacts.withAdded(contactToAddToGroup)));
        assertThat(afterGroups, equalTo(beforeGroups.withAdded(groupToAddTo)));
    }

}
