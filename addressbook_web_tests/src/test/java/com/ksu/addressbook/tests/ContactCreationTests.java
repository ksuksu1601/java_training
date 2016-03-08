package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.ContactData;
import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase{

    @Test
    public void testContactCreation() {
        app.getNavigationHelper().goToHomePage();
        app.getContactHelper().initContactCreation();
        app.getContactHelper().fillContactForm(new ContactData("Fekla", "Pupyrkina", "FeklaP", "The Mars, 1st street", "000", "111", "222", "333", "fekla.pupyrkina@ino.planet", "NewName"), true);
        app.getContactHelper().sumbitContactCreation();
        app.getNavigationHelper().goToHomePage();
    }

}
