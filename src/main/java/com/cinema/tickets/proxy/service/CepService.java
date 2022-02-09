package com.cinema.tickets.proxy.service;

import com.cinema.tickets.proxy.model.Cep;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url= "https://viacep.com.br/ws/" , name = "viacep")
public interface CepService {

    @GetMapping("{cep}/json")
    @Headers("Content-Type: application/json")
    Cep buscaEnderecoPorCep(@PathVariable("cep") String cep);
}
