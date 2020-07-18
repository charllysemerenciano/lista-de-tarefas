package com.acme.lfapi.controllers;

import com.acme.lfapi.entities.ListaTarefa;
import com.acme.lfapi.enums.StatusListaTarefa;
import com.acme.lfapi.repositories.ListaTarefaRepository;
import com.acme.lfapi.services.ListaTarefaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ListaTarefaControllerTest {


	@Autowired
	private MockMvc mvc;

	@MockBean
	private ListaTarefaRepository listaTarefaSRepository;

	private static final String BUSCAR_LISTA_NOME_URL = "/api/listas/";
	private static final Long ID = 1L;
	private static final int ID_LISTA = 1;
	private static final String NOME_LISTA = "LISTA DE TESTE";

	@Test
	@WithMockUser
	public void testBuscarListaIdInvalido() throws Exception {
		BDDMockito.given(this.listaTarefaSRepository.findById(Mockito.anyLong())).willReturn(Optional.empty());

		mvc.perform(MockMvcRequestBuilders.get(BUSCAR_LISTA_NOME_URL + ID_LISTA).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Lista não encontrada para ID " + ID_LISTA));
	}

	@Test
	@WithMockUser
	public void testBuscarListaIdValido() throws Exception {
		BDDMockito.given(this.listaTarefaSRepository.findById(Mockito.anyLong()))
				.willReturn(Optional.of(this.obterDadosListaTarefa()));

		mvc.perform(MockMvcRequestBuilders.get(BUSCAR_LISTA_NOME_URL + ID_LISTA)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.nomeLista", equalTo(NOME_LISTA)))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	private ListaTarefa obterDadosListaTarefa() {
		ListaTarefa listaTarefa = new ListaTarefa();
		listaTarefa.setId((long) ID_LISTA);
		listaTarefa.setNomeLista(NOME_LISTA);
		listaTarefa.setStatus(StatusListaTarefa.VAZIA);
		return listaTarefa;
	}

}
