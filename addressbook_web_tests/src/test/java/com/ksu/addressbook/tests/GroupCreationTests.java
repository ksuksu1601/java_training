package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.GroupData;
import org.testng.annotations.Test;

public class GroupCreationTests extends TestBase{

    @Test
    public void testGroupCreation() {

        app.goToGroupPage();
        app.initGroupCreation();
        app.fillGroupForm(new GroupData("Group1", "The first Group", "My first group"));
        app.submitGroupCreation();
        app.goToGroupPage();
    }

}
