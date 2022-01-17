package com.cinema.tickets.api.mapper;

import com.cinema.tickets.api.dto.PoltronaDTO;
import com.cinema.tickets.domain.collection.Poltrona;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public record PoltronaMapper(ModelMapper modelMapper) {

    public PoltronaDTO toDTO(Poltrona poltrona) {
        return modelMapper.map(poltrona, PoltronaDTO.class);
    }

    public Poltrona toEntity(PoltronaDTO poltronaDTO) {
        return modelMapper.map(poltronaDTO, Poltrona.class);
    }
}
