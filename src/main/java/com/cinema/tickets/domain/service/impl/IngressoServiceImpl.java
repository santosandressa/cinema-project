package com.cinema.tickets.domain.service.impl;

import com.cinema.tickets.domain.collection.Exibicao;
import com.cinema.tickets.domain.collection.Ingresso;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.exception.NotFoundException;
import com.cinema.tickets.domain.repository.ExibicaoRepository;
import com.cinema.tickets.domain.repository.IngressoRepository;
import com.cinema.tickets.domain.service.IngressoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class IngressoServiceImpl implements IngressoService {

    private final  IngressoRepository ingressoRepository;

    private final ExibicaoRepository exibicaoRepository;

    final Logger log = Logger.getLogger(IngressoServiceImpl.class.getName());

    public IngressoServiceImpl(IngressoRepository ingressoRepository, ExibicaoRepository exibicaoRepository) {
        this.ingressoRepository = ingressoRepository;
        this.exibicaoRepository = exibicaoRepository;
    }

    @Override
    public Ingresso save(Ingresso ingresso) {

        if (ingresso.getMeiaEntrada()){
            log.info("Meia Entrada");
            ingresso.setValor(30.0/2);
        } else {
            ingresso.setValor(30.0);
        }


        if (ingresso.getQuantidade() > 5){
            log.info("Quantidade maior que 5, não será possível comprar");
            throw new BusinessException("Quantidade de ingressos não pode ser maior que 5");
        } else {
            ingresso.setValorTotal(ingresso.getValor() * ingresso.getQuantidade());
        }


        Optional<Exibicao> exibicao = exibicaoRepository.findById(ingresso.getExibicao().getId());

        exibicao.ifPresent(ingresso::setExibicao);

        ingresso = ingressoRepository.save(ingresso);

        log.info("Ingresso salvo com sucesso");

        return ingresso;
    }

    @Override
    public Optional<Ingresso> findById(String id) {
        Optional<Ingresso> ingresso = ingressoRepository.findById(id);

        if (ingresso.isEmpty()) {
            log.info("Ingresso não encontrado");
            throw new NotFoundException("Ingresso não encontrado, verifique o id");
        } else {
            log.info("Ingresso para o filme " + ingresso.get().getExibicao().getFilme().getTitulo() + " encontrado");
            ingresso = ingressoRepository.findById(id);

            return ingresso;
        }

    }

    @Override
    public void delete(String id) {
        Optional<Ingresso> ingresso = ingressoRepository.findById(id);

        if (ingresso.isEmpty()){
            log.info("Ingresso não encontrado");
            throw new NotFoundException("Ingresso não encontrado, verifique o id");
        } else {
            log.info("Ingresso para o filme " + ingresso.get().getExibicao().getFilme().getTitulo() + " encontrado");
            ingressoRepository.deleteById(id);
            log.info("Ingresso deletado com sucesso");
        }
    }

    @Override
    public List<Ingresso> findAll() {
        return ingressoRepository.findAll();
    }


}
