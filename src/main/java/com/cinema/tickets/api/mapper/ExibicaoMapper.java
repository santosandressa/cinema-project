package com.cinema.tickets.api.mapper;

import com.cinema.tickets.api.dto.ExibicaoDTO;
import com.cinema.tickets.domain.collection.Exibicao;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public record ExibicaoMapper(ModelMapper mapper) {

    public ExibicaoDTO toDTO(Exibicao exibicao) {
        return mapper.map(exibicao, ExibicaoDTO.class);
    }

    public Exibicao toEntity(ExibicaoDTO exibicaoDTO) {
        return mapper.map(exibicaoDTO, Exibicao.class);
    }
}
