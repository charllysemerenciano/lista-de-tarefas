package com.acme.lfapi.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.lfapi.entities.ListaTarefa;
import com.acme.lfapi.repositories.ListaTarefaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ListaTarefaServiceTest {


    @MockBean
    private ListaTarefaRepository listaTarefaRepository;

    @Autowired
    private ListaTarefaService listaTarefaService;

    private static final String Nome = "Teste";

    @Before
    public void setUp() throws Exception {
        BDDMockito.given(this.listaTarefaRepository.findByNomeLista(Mockito.anyString())).willReturn(new ListaTarefa());
        BDDMockito.given(this.listaTarefaRepository.save(Mockito.any(ListaTarefa.class))).willReturn(new ListaTarefa());
    }

    @Test
    public void testBuscaListaPorNome() {
        Optional<ListaTarefa> taskList = this.listaTarefaService.buscaPorNome("Teste");
        assertTrue(taskList.isPresent());
    }

    @Test
    public void testPersistirLista() {
        ListaTarefa listaTarefa = this.listaTarefaService.persistir(new ListaTarefa());
        assertNotNull(listaTarefa);
    }
}
