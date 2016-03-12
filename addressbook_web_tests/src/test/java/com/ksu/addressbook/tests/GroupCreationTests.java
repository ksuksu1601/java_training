package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class GroupCreationTests extends TestBase{

    @Test
    public void testGroupCreation() {

        app.getNavigationHelper().goToGroupPage();
        List<GroupData> before = app.getGroupHelper().getGroupList();
        app.getGroupHelper().createGroup(new GroupData("Group1", "The first Group", "My first group"));
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size() + 1);
    }

}
