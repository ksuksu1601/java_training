package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.GroupData;
import org.testng.annotations.Test;

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
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().initGroupModification();
        app.getGroupHelper().fillGroupForm(new GroupData(null, "NewHader2", "NewFooter"));
        app.getGroupHelper().submitGroupModification();
        app.getGroupHelper().returnToGroupPage();
    }
}
