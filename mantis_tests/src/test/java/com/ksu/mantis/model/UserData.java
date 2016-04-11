package com.ksu.mantis.model;

import javax.persistence.*;

@Entity
@Table(name = "mantis_user_table")
public class UserData {

    @Id
    @Column(name = "id")
    private int id = Integer.MAX_VALUE;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    public String name() {
        return username;
    }

    public String email() {
        return email;
    }

    public int id() {
        return id;
    }

}