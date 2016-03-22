package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.GroupData;
import com.ksu.addressbook.model.Groups;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.*;

public class GroupCreationTests extends TestBase{

    @Test
    public void testGroupCreation() {

        app.goTo().groupPage();
        Groups before = app.group().all();
        GroupData group = new GroupData().withName("Group1").withHeader("The first Group").withFooter("My first group");
        app.group().create(group);
        assertEquals(app.group().getGroupCount(), before.size() + 1);
        Groups after = app.group().all();
        assertThat(after, equalTo(
                before.withAdded(group.withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt()))));
    }

}
