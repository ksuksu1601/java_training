package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.ContactData;
import org.testng.annotations.Test;

/**
 * Created by Ksu on 02.03.2016.
 */
public class ContactModificationTests extends TestBase{

    @Test
    public void testContactModification(){
        app.getNavigationHelper().goToHomePage();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Fekla2", "Pupyrkina2", "FeklaP", "The Mars, 1st street", "000", "111", "222", "333", "fekla.pupyrkina@ino.planet", null), false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().goToHomePage();
    }
}
