package com.ksu.addressbook.tests;

import com.ksu.addressbook.model.GroupData;
import com.ksu.addressbook.model.Groups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.*;

public class GroupCreationTests extends TestBase{

    @DataProvider
    Iterator<Object[]> validGroups() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groupData.csv")));
        String line = reader.readLine();
        while(line != null){
            String[] splitedLine = line.split(";");
            list.add(new Object[]{new GroupData().withName(splitedLine[0]).withHeader(splitedLine[1]).withFooter(splitedLine[2])});
            line = reader.readLine();
        }
        return list.iterator();
    }

    @Test(dataProvider = "validGroups")
    public void testGroupCreation(GroupData group) {
        app.goTo().groupPage();
        Groups before = app.group().all();
        app.group().create(group);
        assertEquals(app.group().getGroupCount(), before.size() + 1);
        Groups after = app.group().all();
        assertThat(after, equalTo(
                before.withAdded(group.withId(after.stream().mapToInt(g -> g.getId()).max().getAsInt()))));
    }

}
