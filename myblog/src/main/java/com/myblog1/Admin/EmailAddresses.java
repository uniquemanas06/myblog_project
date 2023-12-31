package com.myblog1.Admin;


public enum EmailAddresses {
    EMAIL_1("mike@gmail.com"),
    EMAIL_2("ram@gmail.com"),
    EMAIL_3("adam@gmail.com");

    private final String emailAddress;

    EmailAddresses(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}

