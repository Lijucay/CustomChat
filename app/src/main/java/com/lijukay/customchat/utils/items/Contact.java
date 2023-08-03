package com.lijukay.customchat.utils.items;

public class Contact {

    private final String firstName;
    private final String lastName;
    private final String number;
    private final String id;

    public Contact(String firstName, String lastName, String number, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNumber() {
        return number;
    }

    public String getId() {
        return id;
    }
}
