package com.acme.lfapi.services;

import com.acme.lfapi.entities.ListaTarefa;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ListaTarefaService {

    /**
     * retorna a lista pelo nome
     *
     * @param nome
     * @return Optional<TaskList>
     */
    Optional<ListaTarefa> buscaPorNome(String nome);

    /**
     * Criar nova lista de tarefas
     *
     * @param listaTarefa
     * @return TaskList
     */
    ListaTarefa persistir(ListaTarefa listaTarefa);

    void remover(Long id);
}
