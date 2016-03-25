package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.ContactData;
import com.ksu.addressbook.model.GroupData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.util.Strings;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Ksu on 22.03.2016.
 */
public class ContactDetailedInfoTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().homePage();
        if (app.contact().getContactCount() == 0){
            String groupForContact = "GroupForContact";
            app.goTo().groupPage();
            if(! app.group().isThereASpecificGroup(groupForContact)){
                app.group().create(new GroupData().withName(groupForContact));
            }
            app.goTo().homePage();
            app.contact().create(new ContactData().withFirstname("Fekla").withLastname("Pupyrkina").withNickname("FeklaP")
                    .withAddress("The Mars, 1st street").withFax("000").withMobilePhone("111").withHomePhone("222")
                    .withWorkPhone("333").withEmail("fekla.pupyrkina@ino.planet").withGroup(groupForContact));
        }
    }

    @Test
    public void testContactDetailedInfo(){
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        String info = app.contact().detailedInfo(contact);
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(info, equalTo(buildDetailedInfo(contactInfoFromEditForm)));
    }

    private String buildDetailedInfo(ContactData contact) {

        String fio = Arrays.asList(contact.getFirstname(), contact.getMiddlename(), contact.getLastname())
                .stream().filter(s -> !Strings.isNullOrEmpty(s)).collect(Collectors.joining(" "));

        String firstBlock = Arrays.asList(fio, contact.getNickname(), contact.getAddress())
                .stream().filter(s -> !Strings.isNullOrEmpty(s)).collect(Collectors.joining("\n"));

        String phones = Arrays.asList(Strings.isNullOrEmpty(contact.getHomePhone()) ? "" : "H: " + contact.getHomePhone(),
                Strings.isNullOrEmpty(contact.getMobilePhone()) ? "" : "M: " + contact.getMobilePhone(),
                Strings.isNullOrEmpty(contact.getWorkPhone()) ? "" : "W: " + contact.getWorkPhone(),
                Strings.isNullOrEmpty(contact.getFax()) ? "" : "F: " + contact.getFax())
                .stream().filter(s -> !Strings.isNullOrEmpty(s)).collect(Collectors.joining("\n"));

        String emails = Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter(s -> !Strings.isNullOrEmpty(s)).map(ContactDetailedInfoTests::fullEmailStr)
                .collect(Collectors.joining("\n"));

        String secondBlock = Arrays.asList(firstBlock, phones, emails)
                .stream().filter(s -> !Strings.isNullOrEmpty(s)).collect(Collectors.joining("\n\n"));

        //String groupInfo = Strings.isNullOrEmpty(contact.getGroup()) ? "" : "Member of: " + contact.getGroup();
        // TODO rework this when it's known how to get group info for contact
        String groupInfo = "Member of: NewName";

        return  Arrays.asList(secondBlock, groupInfo)
                .stream().filter(s -> !Strings.isNullOrEmpty(s)).collect(Collectors.joining("\n\n\n"));
    }

    public static String fullEmailStr(String email){
        int index = email.indexOf("@") < 0 ? 1 : email.indexOf("@") + 1;
        return email + " (www." + email.substring(index) + ")";
    }
}
