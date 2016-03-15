package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.zip.Inflater;

public class GroupCreationTests extends TestBase{

    @Test
    public void testGroupCreation() {

        app.getNavigationHelper().goToGroupPage();
        List<GroupData> before = app.getGroupHelper().getGroupList();
        GroupData group = new GroupData("Group1", "The first Group", "My first group");
        app.getGroupHelper().createGroup(group);
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size() + 1);

//        Way 1:
//        int max = 0;
//        for (GroupData g: after){
//            max = g.getId() > max ? g.getId() : max;
//        }

//        Way 2:
//        Comparator<? super GroupData> byId = new Comparator<GroupData>() {
//            @Override
//            public int compare(GroupData o1, GroupData o2) {
//                return Integer.compare(o1.getId(), o2.getId());
//            }
//        } ;
//        int max = after.stream().max(byId).get().getId();

//        Way 3:
//        Comparator<? super GroupData> byId = (o1, o2) -> Integer.compare(o1.getId(), o2.getId());

        group.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        before.add(group);
        Assert.assertEquals(new HashSet<GroupData>(before), new HashSet<GroupData>(after));
    }

}
