package com.ksu.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ksu.addressbook.model.ContactData;
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
public class ContactDataGenerator {

    @Parameter(names = "-c", description = "Contact count")
    int count;

    @Parameter(names = "-f", description = "Target file path")
    String filePath;
    
    @Parameter(names = "-d", description = "Data format")
    String format;

    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
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
        List<ContactData> contacts = generateContacts(count);
        if(format.equals("xml")){
            saveAsXml(contacts, new File(filePath));
        }  else if(format.equals("json")){
            saveAsJson(contacts, new File(filePath));
        }  else {
            System.out.println("Unrecognized format " + format);
        }
    }

    private List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++){
            contacts.add(new ContactData().withFirstname(String.format("Fekla%s", i))
                    .withMiddlename(String.format("Ivanovna%s", i))
                    .withLastname(String.format("Pupyrkina%s", i))
                    .withNickname(String.format("FeklaP%s", i))
                    .withAddress(String.format("The Mars, 1st street, apt. %s", i))
                    .withHomePhone(String.format("111-%s", i))
                    .withMobilePhone(String.format("+7(222)222-%s", i))
                    .withWorkPhone(String.format("333-%s", i))
                    .withFax(String.format("000-%s", i))
                    .withEmail(String.format("Fekla%s@mars.org", i))
                    .withGroup("GroupForContact"));
        }
        return contacts;
    }

    private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        Writer writer = new FileWriter(file);
        writer.write(json);
        writer.close();
    }

    private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(ContactData.class);
        String xml = xstream.toXML(contacts);
        Writer writer = new FileWriter(file);
        writer.write(xml);
        writer.close();
    }

}
