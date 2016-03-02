package com.ksu.addressbook.tests;

import org.testng.annotations.Test;

/**
 * Created by Ksu on 02.03.2016.
 */
public class ContactDeletionTests extends TestBase{

    @Test
    public void testContactDeletion(){
        app.getNavigationHelper().goToHomePage();
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactDeletion();
        app.getContactHelper().acceptAlert();
        app.getNavigationHelper().goToHomePage();
    }
}
