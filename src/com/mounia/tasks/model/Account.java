package com.mounia.tasks.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account implements Serializable {

    @Id
    private String accountId;
    private String email;
    private String name;
    private String given_name;
    private String family_name;
    private String link;
    private String picture;
    private String gender;
    private String locale;

    public Account(String accountId, String email, String name, String given_name, String family_name, String link, String picture, String gender, String locale) {
        this.accountId = accountId;
        this.email = email;
        this.name = name;
        this.given_name = given_name;
        this.family_name = family_name;
        this.link = link;
        this.picture = picture;
        this.gender = gender;
        this.locale = locale;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String name) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGivenName() {
        return given_name;
    }

    public void setGivenName(String given_name) {
        this.given_name = given_name;
    }

    public String getFamilyName() {
        return family_name;
    }

    public void setFamilyName(String family_name) {
        this.family_name = family_name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
