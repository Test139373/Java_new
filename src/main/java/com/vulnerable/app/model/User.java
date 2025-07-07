package com.vulnerable.app.model;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // VULNERABLE: Sensitive data in model without proper access control
    private String username;
    private String password;
    private String ssn;
    private boolean isAdmin;

    // VULNERABLE: Default constructor for deserialization exploits
    public User() {}

    public User(String username, String password, String ssn, boolean isAdmin) {
        this.username = username;
        this.password = password; // VULNERABLE: Plaintext password storage
        this.ssn = ssn; // VULNERABLE: Sensitive PII without encryption
        this.isAdmin = isAdmin;
    }

    // Getters and setters with no validation (vulnerable to mass assignment)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password; // VULNERABLE: Exposing password in clear text
    }

    public void setPassword(String password) {
        this.password = password; // VULNERABLE: No password strength validation
    }

    public String getSsn() {
        return ssn; // VULNERABLE: Exposing SSN without masking
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        // VULNERABLE: Exposing sensitive information in logs
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ssn='" + ssn + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}