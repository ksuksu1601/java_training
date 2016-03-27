package com.ksu.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ksu.addressbook.model.ContactData;
import com.ksu.addressbook.model.Contacts;
import com.ksu.addressbook.model.GroupData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.Provider;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase{

    @DataProvider
    Iterator<Object[]> validContactsFromJson() throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contactData.json")))){
            String json = "";
            String line = reader.readLine();
            while(line != null){
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType());
            return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
        }
    }

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

    @Test(dataProvider = "validContactsFromJson")
    public void testContactCreation(ContactData contact) {
        Contacts before = app.contact().all();
        app.contact().create(contact);
        assertEquals(app.contact().getContactCount(), before.size() + 1);
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt()))));

    }

}
