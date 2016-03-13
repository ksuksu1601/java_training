package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase{

    @Test
    public void testGroupCreation() {

        app.getNavigationHelper().goToGroupPage();
        List<GroupData> before = app.getGroupHelper().getGroupList();
        GroupData group = new GroupData("Group1", "The first Group", "My first group");
        app.getGroupHelper().createGroup(group);
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size() + 1);

        int max = 0;
        for (GroupData g: after){
            max = g.getId() > max ? g.getId() : max;
        }
        group.setId(max);
        before.add(group);
        Assert.assertEquals(new HashSet<GroupData>(before), new HashSet<GroupData>(after));
    }

}
