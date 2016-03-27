package com.ksu.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ksu.addressbook.model.GroupData;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ksu on 26.03.2016.
 */
public class GroupDataGenerator {

    @Parameter(names = "-c", description = "Group count")
    int count;

    @Parameter(names = "-f", description = "Target file path")
    String filePath;
    
    @Parameter(names = "-d", description = "Data format")
    String format;

    public static void main(String[] args) throws IOException {
        GroupDataGenerator generator = new GroupDataGenerator();
        JCommander jcmd = new JCommander(generator);
        try {
            jcmd.parse(args);
        }catch (ParameterException e){
            jcmd.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<GroupData> groups = generateGroups(count);
        if(format.equals("csv")){
            saveAsCsv(groups, new File(filePath));        
        } else if(format.equals("xml")){
            saveAsXml(groups, new File(filePath));
        }  else if(format.equals("json")){
            saveAsJson(groups, new File(filePath));
        }  else {
            System.out.println("Unrecognized format " + format);
        }
    }

    private void saveAsJson(List<GroupData> groups, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(groups);
        Writer writer = new FileWriter(file);
        writer.write(json);
        writer.close();
    }

    private void saveAsXml(List<GroupData> groups, File file) throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(GroupData.class);
        String xml = xstream.toXML(groups);
        Writer writer = new FileWriter(file);
        writer.write(xml);
        writer.close();
    }

    private static void saveAsCsv(List<GroupData> groups, File file) throws IOException {
        Writer writer = new FileWriter(file);
        for (GroupData group: groups){
           writer.write(String.format("%s;%s;%s\n", group.getName(), group.getHeader(), group.getFooter()));
        }
        writer.close();
    }

    private static List<GroupData> generateGroups(int count) {
        List<GroupData> groups= new ArrayList<GroupData>();
        for (int i = 0; i < count; i++){
            groups.add(new GroupData().withName(String.format("testGroup%s", i))
                    .withHeader(String.format("Header%s", i)).withFooter(String.format("Footer%s", i)));
        }
        return groups;
    }
}
