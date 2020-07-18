package com.acme.lfapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.acme.lfapi.entities.ListaTarefa;

public interface ListaTarefaRepository extends JpaRepository<ListaTarefa, Long> {

    @Transactional(readOnly = true)
    ListaTarefa findByNomeLista(String name);

}
