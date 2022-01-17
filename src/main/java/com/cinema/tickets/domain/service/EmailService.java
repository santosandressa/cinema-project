package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Email;

public interface EmailService {

    Email sendEmail(Email email);
}
