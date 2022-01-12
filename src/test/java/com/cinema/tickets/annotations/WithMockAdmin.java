package com.cinema.tickets.annotations;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(value = "luanaantonellasantos_@trbvm.com", roles = {"ADMIN"})
public @interface WithMockAdmin {
}
