package com.cinema.tickets.api.mapper;

import com.cinema.tickets.api.dto.SalaDTO;
import com.cinema.tickets.domain.collection.Sala;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public record SalaMapper(ModelMapper modelMapper) {

    public SalaDTO salaToDTO(Sala sala) {
        return modelMapper.map(sala, SalaDTO.class);
    }

    public Sala dtoToSala(SalaDTO salaDTO) {
        return modelMapper.map(salaDTO, Sala.class);
    }
}
