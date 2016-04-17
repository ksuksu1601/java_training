package com.ksu.mantis.tests;

import com.ksu.mantis.model.Issue;
import com.ksu.mantis.model.Project;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

/**
 * Created by Ksu on 17.04.2016.
 */
public class SoapTests extends TestBase{

    @Test
    public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
        skipIfNotFixed(1);
        Set<Project> projects = app.soap().getProjects();
        System.out.println(projects.size());
        for(Project project : projects){
            System.out.println(project.getName());
        }
    }

    @Test
    public void testCreateIssue() throws RemoteException, ServiceException, MalformedURLException {
        Set<Project> projects = app.soap().getProjects();
        Issue issue = new Issue().withSummary("Test issue").withDescription("Test issue desc").withProject(projects.iterator().next());
        Issue created = app.soap().addIssue(issue);
        Assert.assertEquals(issue.getSummary(), created.getSummary());
    }

}
