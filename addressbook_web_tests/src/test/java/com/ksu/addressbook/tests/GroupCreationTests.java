package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupCreationTests extends TestBase{

    @Test
    public void testGroupCreation() {

        app.getNavigationHelper().goToGroupPage();
        int before = app.getGroupHelper().getGroupCount();
        app.getGroupHelper().createGroup(new GroupData("Group1", "The first Group", "My first group"));
        int after = app.getGroupHelper().getGroupCount();
        Assert.assertEquals(after, before + 1);
    }

}
