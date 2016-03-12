package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.GroupData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Ksu on 02.03.2016.
 */
public class GoupModificationTests extends TestBase{

    @Test
    public void testGroupModification(){
        app.getNavigationHelper().goToGroupPage();
        if (! app.getGroupHelper().isThereAGroup()){
            app.getGroupHelper().createGroup(new GroupData("GroupToModify", "The Group to be modified", "Hohoho"));
        }
        List<GroupData> before = app.getGroupHelper().getGroupList();
        app.getGroupHelper().selectGroup(before.size() - 1);
        app.getGroupHelper().initGroupModification();
        app.getGroupHelper().fillGroupForm(new GroupData(null, "NewHader2", "NewFooter"));
        app.getGroupHelper().submitGroupModification();
        app.getGroupHelper().returnToGroupPage();
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size());
    }
}
