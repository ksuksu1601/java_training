package com.ksu.mantis.appmanager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ksu.mantis.model.Issue;
import com.ksu.mantis.model.Project;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

/**
 * Created by Ksu on 17.04.2016.
 */
public class RestHelper {

    private final ApplicationManager app;

    public RestHelper(ApplicationManager app){
        this.app = app;
    }

    public Issue getIssue(int issueId) throws IOException {
        String json = getExecutor().execute(Request.Get(String.format("http://demo.bugify.com/api/issues/%s.json", issueId)))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        JsonObject issue = issues.getAsJsonArray().get(0).getAsJsonObject();
        JsonObject project = issue.getAsJsonObject("project");
        return new Issue().withId(issueId)
                .withState(issue.get("state_name").getAsString())
                .withDescription(issue.get("description").getAsString())
                .withSummary(issue.get("subject").getAsString())
                .withProject(new Project()
                       .withId(project.get("id").getAsInt()).withName(project.get("name").getAsString()));
    }

    private Executor getExecutor() {
        return Executor.newInstance().auth("LSGjeU4yP1X493ud1hNniA==","");
    }
}
