package com.cinema.tickets.api.mapper;

import com.cinema.tickets.api.dto.IngressoDTO;
import com.cinema.tickets.domain.collection.Ingresso;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public record IngressoMapper(ModelMapper modelMapper){

    public IngressoDTO toDto(Ingresso ingresso) {
        return modelMapper.map(ingresso, IngressoDTO.class);
    }

    public Ingresso toEntity(IngressoDTO ingressoDto) {
        return modelMapper.map(ingressoDto, Ingresso.class);
    }
}
