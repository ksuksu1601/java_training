package com.ksu.addressbook.model;

public class ContactData {
    private final String firstname;
    private final String lastname;
    private final String nickname;
    private final String address;
    private final String homePhone;
    private final String mobilePhone;
    private final String workPhone;
    private final String fax;
    private final String email;
    private String group;
    private int id;

    public ContactData(String firstname, String lastname, String nickname, String address, String homePhone, String mobilePhone, String workPhone, String fax, String email, String group) {
        this(Integer.MAX_VALUE, firstname, lastname, nickname, address, homePhone, mobilePhone, workPhone, fax, email, group);
    }

    public ContactData(int id, String firstname, String lastname, String nickname, String address, String homePhone, String mobilePhone, String workPhone, String fax, String email, String group) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.nickname = nickname;
        this.address = address;
        this.homePhone = homePhone;
        this.mobilePhone = mobilePhone;
        this.workPhone = workPhone;
        this.fax = fax;
        this.email = email;
        this.group = group;
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAddress() {
        return address;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    public String getGroup() {
        return group;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        return lastname != null ? lastname.equals(that.lastname) : that.lastname == null;

    }

    @Override
    public int hashCode() {
        int result = firstname != null ? firstname.hashCode() : 0;
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                '}';
    }
}
