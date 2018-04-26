package dev.fmayoral.customermanagement.api_1_0;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Set;

@JsonSerialize
public class Customer {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private ContactDetails contactDetails;
    private Status status;
    private String created;

    private Set<Note> notes;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Customer withId(final Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public Customer withFirstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }

    public Customer withMiddleName(final String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Customer withLastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ContactDetails getContactDetails() {
        return this.contactDetails;
    }

    public void setContactDetails(final ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public Customer withContactDetails(final ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
        return this;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public Customer withStatus(final Status status) {
        this.status = status;
        return this;
    }

    public String getCreated() {
        return this.created;
    }

    public void setCreated(final String created) {
        this.created = created;
    }

    public Customer withCreated(final String creationDateTime) {
        this.created = creationDateTime;
        return this;
    }

    public Set<Note> getNotes() {
        return this.notes;
    }

    public void setNotes(final Set<Note> notes) {
        this.notes = notes;
    }

    public Customer withNotes(final Set<Note> notes) {
        this.notes = notes;
        return this;
    }

}
