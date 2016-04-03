package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.GroupData;
import com.ksu.addressbook.model.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.*;

/**
 * Created by Ksu on 02.03.2016.
 */
public class GroupModificationTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0){
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("GroupToModify").withHeader("The Group to be modified").withFooter("Hohoho"));
        }
    }

    @Test
    public void testGroupModification(){
        Groups before = app.db().groups();
        GroupData modifiedGroup = before.iterator().next();
        GroupData group = new GroupData().withId(modifiedGroup.getId()).withName("NewName")
                .withHeader("NewHader2").withFooter("NewFooter");
        app.goTo().groupPage();
        app.group().modify(group);
        assertEquals(app.group().getGroupCount(), before.size());
        Groups after = app.db().groups();
        assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));


    }

}
