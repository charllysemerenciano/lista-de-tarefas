package com.acme.lfapi.services;

import com.acme.lfapi.entities.Tarefa;
import com.acme.lfapi.repositories.ListaTarefaRepository;
import com.acme.lfapi.repositories.TarefaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TarefaServiceTest {
    @MockBean
    private TarefaRepository tarefaRepository;

    @Autowired
    private TarefaService tarefaService;

    @Before
    public void setUp() throws Exception {
        BDDMockito
                .given(this.tarefaRepository.findByListaTarefaId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
                .willReturn(new PageImpl<Tarefa>(new ArrayList<Tarefa>()));
        BDDMockito.given(this.tarefaRepository.findById(Mockito.anyLong())).willReturn(java.util.Optional.of(new Tarefa()));
        BDDMockito.given(this.tarefaRepository.save(Mockito.any(Tarefa.class))).willReturn(new Tarefa());
    }

    @Test
    public void testBuscarTarefaPorListaId() {
        List<Tarefa> tarefas = this.tarefaService.buscarPorListaId(1L);

        assertNotNull(tarefas);
    }

    @Test
    public void testBuscarLancamentoPorId() {
        Optional<Tarefa> tarefa = this.tarefaService.buscarPorId(1L);

        assertTrue(tarefa.isPresent());
    }

    @Test
    public void testPersistirTarefa() {
        Tarefa tarefa = this.tarefaService.persistir(new Tarefa());

        assertNotNull(tarefa);
    }


}
