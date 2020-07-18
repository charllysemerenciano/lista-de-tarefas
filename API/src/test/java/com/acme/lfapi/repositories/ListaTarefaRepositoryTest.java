package com.acme.lfapi.repositories;

import com.acme.lfapi.entities.ListaTarefa;
import com.acme.lfapi.enums.StatusListaTarefa;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ListaTarefaRepositoryTest {

    @Autowired
    private ListaTarefaRepository listaTarefaRepository;

    private static final String nomeLista = "Lista_01";

    @Before
    public void setUp() throws Exception {
        ListaTarefa listaTarefa = new ListaTarefa();
        listaTarefa.setNomeLista(nomeLista);
        listaTarefa.setStatus(StatusListaTarefa.VAZIA);
        this.listaTarefaRepository.save(listaTarefa);
    }

    @After
    public final void tearDown() {
        this.listaTarefaRepository.deleteAll();
    }

    @Test
    public void testBuscarPorNomeLista() {
        ListaTarefa listaTarefa = this.listaTarefaRepository.findByNomeLista(nomeLista);
        assertEquals(nomeLista, listaTarefa.getNomeLista());
    }


}
