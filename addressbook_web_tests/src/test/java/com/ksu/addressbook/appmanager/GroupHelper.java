package com.ksu.addressbook.appmanager;

import com.ksu.addressbook.model.GroupData;
import com.ksu.addressbook.model.Groups;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ksu on 01.03.2016.
 */
public class GroupHelper extends HelperBase {

    private Groups groupsCache = null;

    public GroupHelper(WebDriver wd) {
        super(wd);
    }

    public void submitCreation() {
        click(By.name("submit"));
    }

    public void fillGroupForm(GroupData groupData) {
        type(By.name("group_name"), groupData.getName());
        type(By.name("group_header"), groupData.getHeader());
        type(By.name("group_footer"), groupData.getFooter());
    }

    public void initCreation() {
        click(By.name("new"));
    }

    public void deleteSelected() {
        click(By.name("delete"));
    }

    public void select(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    private void selectById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void initModification() {
        click(By.name("edit"));
    }

    public void submitModification() {
        click(By.name("update"));
    }

    public void returnToGroupPage(){
        click(By.linkText("group page"));
    }

    public void create(GroupData group) {
        initCreation();
        fillGroupForm(group);
        submitCreation();
        groupsCache = null;
        returnToGroupPage();;
    }

    public void modify(GroupData group) {
        selectById(group.getId());
        initModification();
        fillGroupForm(group);
        submitModification();
        groupsCache = null;
        returnToGroupPage();
    }

    public void delete(GroupData group) {
        selectById(group.getId());
        deleteSelected();
        groupsCache = null;
        returnToGroupPage();
    }

    public boolean isThereASpecificGroup(String groupName){
        return isElementPresent(By.xpath("//span[normalize-space(text())='" + groupName + "']"));
    }

    public int getGroupCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public Groups all() {
        if(groupsCache != null){
            return new Groups(groupsCache);
        }

        groupsCache = new Groups();
        List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
        for(WebElement el: elements){
            String name = el.getText();
            int id = Integer.parseInt(el.findElement(By.tagName("input")).getAttribute("value"));
            GroupData group = new GroupData().withId(id).withName(name);
            groupsCache.add(group);
        }
        return new Groups(groupsCache);
    }

}
