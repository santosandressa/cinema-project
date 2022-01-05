package com.cinema.tickets.api.mapper;

import com.cinema.tickets.api.dto.PoltronaDTO;
import com.cinema.tickets.domain.collection.Poltrona;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PoltronaMapper {

    @Autowired
    ModelMapper modelMapper;

    public PoltronaDTO toDTO(Poltrona poltrona) {
        return modelMapper.map(poltrona, PoltronaDTO.class);
    }

    public Poltrona toEntity(PoltronaDTO poltronaDTO) {
        return modelMapper.map(poltronaDTO, Poltrona.class);
    }
}
