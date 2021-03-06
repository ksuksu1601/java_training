package com.ksu.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import com.ksu.mantis.model.Issue;
import com.ksu.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Ksu on 17.04.2016.
 */
public class SoapHelper {

    private final ApplicationManager app;

    public SoapHelper(ApplicationManager app){
        this.app = app;
    }

    public Set<Project> getProjects() throws RemoteException, MalformedURLException, ServiceException {
        MantisConnectPortType mc = getMantisConnect();
        ProjectData[] projects = mc.mc_projects_get_user_accessible("administrator", "root");
        return Arrays.asList(projects).stream()
                .map((g) -> new Project().withId(g.getId().intValue()).withName(g.getName()))
                .collect(Collectors.toSet());
    }

    public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        String[] categories = mc.mc_project_get_categories("administrator", "root", BigInteger.valueOf(issue.getProject().getId()));
        IssueData issueData = new IssueData();
        issueData.setSummary(issue.getSummary());
        issueData.setDescription(issue.getDescription());
        issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
        issueData.setCategory(categories[0]);
        BigInteger issueId = mc.mc_issue_add("administrator", "root", issueData);
        IssueData createdIssueData = mc.mc_issue_get("administrator", "root", issueId);
        return new Issue().withId(createdIssueData.getId().intValue())
                          .withSummary(createdIssueData.getSummary()).withDescription(createdIssueData.getDescription())
                          .withProject(new Project().withId(createdIssueData.getProject().getId().intValue())
                                                    .withName(createdIssueData.getProject().getName()));
    }

    public MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
        return new MantisConnectLocator()
                .getMantisConnectPort(new URL("http://localhost/mantisbt-1.2.19/api/soap/mantisconnect.php"));
    }

    public Issue getIssue(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        IssueData issueData = mc.mc_issue_get("administrator", "root", BigInteger.valueOf(issueId));
        return new Issue().withId(issueData.getId().intValue())
                .withSummary(issueData.getSummary())
                .withDescription(issueData.getDescription())
                .withProject(new Project().withId(issueData.getProject().getId().intValue()).withName(issueData.getProject().getName()))
                .withState(issueData.getStatus().getName());
    }
}
