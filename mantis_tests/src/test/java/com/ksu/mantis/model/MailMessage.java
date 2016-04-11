package com.ksu.mantis.model;

/**
 * Created by Ksu on 10.04.2016.
 */
public class MailMessage {

    public String to;
    public String text;

    public MailMessage(String to, String text) {
        this.to = to;
        this.text = text;
    }
}
