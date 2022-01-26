package com.cinema.tickets.api.mapper;

import com.cinema.tickets.api.dto.EmailDTO;
import com.cinema.tickets.domain.collection.Email;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public record EmailMapper(ModelMapper mapper) {

    public EmailDTO toDto(Email email) {
        return mapper.map(email, EmailDTO.class);
    }

    public Email toEntity(EmailDTO emailDTO) {
        return mapper.map(emailDTO, Email.class);
    }
}
