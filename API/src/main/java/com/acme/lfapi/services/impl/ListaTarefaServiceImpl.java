package com.acme.lfapi.services.impl;

import com.acme.lfapi.entities.ListaTarefa;
import com.acme.lfapi.repositories.ListaTarefaRepository;
import com.acme.lfapi.services.ListaTarefaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ListaTarefaServiceImpl implements ListaTarefaService {

    private static final Logger log = LoggerFactory.getLogger(ListaTarefaServiceImpl.class);

    @Autowired
    private ListaTarefaRepository listaTarefaRepository;

    @Override
    public Optional<ListaTarefa> buscaPorNome(String nome) {
        log.info("Buscando por nome");
        return Optional.ofNullable(this.listaTarefaRepository.findByNomeLista(nome));
    }

    @Override
    public ListaTarefa persistir(ListaTarefa listaTarefa) {
        log.info("gravando lista");
        return this.listaTarefaRepository.save(listaTarefa);
    }

    public void remover(Long id) {
        log.info("Removendo o Lista de tarefa ID {}", id);
        this.listaTarefaRepository.deleteById(id);
    }
}
