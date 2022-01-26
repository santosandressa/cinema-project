package com.cinema.tickets.api.dto;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public class EmailDTO {

    private String id;

    @NotEmpty
    private String emailFrom;

    @NotEmpty
    private String emailTo;

    @NotEmpty
    private String subject;

    @NotEmpty
    private String text;

    private LocalDateTime sendDateEmail;

    public EmailDTO(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getSendDateEmail() {
        return sendDateEmail;
    }

    public void setSendDateEmail(LocalDateTime sendDateEmail) {
        this.sendDateEmail = sendDateEmail;
    }
}
