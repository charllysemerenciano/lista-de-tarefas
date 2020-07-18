package com.acme.lfapi.repositories;

import com.acme.lfapi.entities.ListaTarefa;
import com.acme.lfapi.entities.Tarefa;
import com.acme.lfapi.enums.StatusListaTarefa;
import com.acme.lfapi.enums.StatusTarefa;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TarefaRepositoryTest {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ListaTarefaRepository listaTarefaRepository;

    private static final String Tarefa = "Tarefa de Teste";
    private static final Long idTarefa1 = 1L;
    private static final Long idTarefa2 = 2L;

    private static final Long id = 1L;

    @After
    public final void tearDown() {
        this.tarefaRepository.deleteAll();
    }

    @Before
    public void setUp() throws Exception {

        ListaTarefa listaTarefa = this.listaTarefaRepository.save(obterDadosListaTarefa());

        this.tarefaRepository.save(obterDadosTarefa(listaTarefa));
        this.tarefaRepository.save(obterDadosTarefa(listaTarefa));
    }

    private Tarefa obterDadosTarefa(ListaTarefa listaTarefa) {

        Tarefa tarefa = new Tarefa();
        tarefa.setTarefa(Tarefa);
        tarefa.setStatus(StatusTarefa.INCOMPLETA);
        tarefa.setListaTarefa(listaTarefa);

        return tarefa;
    }

    private ListaTarefa obterDadosListaTarefa() {

        ListaTarefa listaTarefa = new ListaTarefa();
        listaTarefa.setId(id);
        listaTarefa.setNomeLista("Lista de tarefas de teste");
        listaTarefa.setStatus(StatusListaTarefa.VAZIA);

        return listaTarefa;

    }


    @Test
    public void testBuscaPorListaId() {
        List<Tarefa> tarefas = this.tarefaRepository.findByListaTarefaId(id);
        assertEquals(2, tarefas.size());
    }

}
