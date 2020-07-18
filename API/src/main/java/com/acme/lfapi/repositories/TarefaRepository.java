package com.acme.lfapi.repositories;

import com.acme.lfapi.entities.ListaTarefa;
import com.acme.lfapi.entities.Tarefa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@NamedQueries({
        @NamedQuery(name = "TarefaRepository.findByListaTarefaId",
                query = "SELECT T FROM Tarefa T WHERE T.id = :listaTarefaId")
})
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByListaTarefaId(@Param("listaTarefaId") Long listaTarefaId);

    Page<Tarefa> findByListaTarefaId(@Param("listaTarefaId") Long listaTarefaId, Pageable pageable);

    Optional<Tarefa> findById(Long id);


    @Override
    void deleteById(Long id);
}
