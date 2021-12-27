package com.cinema.tickets.api.mapper;

import com.cinema.tickets.api.dto.ClienteDTO;
import com.cinema.tickets.domain.collection.Cliente;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente cliente) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(cliente, ClienteDTO.class);
    }

    public Cliente toEntity(ClienteDTO clienteDTO) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(clienteDTO, Cliente.class);
    }
}
