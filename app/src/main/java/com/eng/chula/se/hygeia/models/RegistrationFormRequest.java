package com.eng.chula.se.hygeia.models;

public class RegistrationFormRequest {

    private String accountId;
    private String password;
    private String firstname;
    private String lastname;
    private String citizenId;
    private String birthday;
    private String hometown;
    private String telephoneNumber;
    private String email;
    private String contact;

    public RegistrationFormRequest(String accountId, String password, String firstname, String lastname,
                                   String citizenId, String birthday, String hometown, String telephoneNumber,
                                   String email, String contact) {
        this.accountId = accountId;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.citizenId = citizenId;
        this.birthday = birthday;
        this.hometown = hometown;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.contact = contact;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCitizenId() {
        return citizenId;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getHometown() {
        return hometown;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    //ตรวจสอบการ Register
    public Boolean register() {
        return true;
    }
}
