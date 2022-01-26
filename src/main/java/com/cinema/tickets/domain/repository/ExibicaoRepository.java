package com.cinema.tickets.domain.repository;


import com.cinema.tickets.domain.collection.Exibicao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExibicaoRepository extends MongoRepository<Exibicao, String > {

    Boolean existsByDataExibicao(LocalDate dataExibicao);

}
