package com.acme.lfapi.controllers;

import com.acme.lfapi.dtos.ListaTarefaDto;
import com.acme.lfapi.dtos.ListaTarefaUpdateDto;
import com.acme.lfapi.dtos.TarefaDto;
import com.acme.lfapi.dtos.TarefaUpdateDto;
import com.acme.lfapi.entities.ListaTarefa;
import com.acme.lfapi.entities.Tarefa;
import com.acme.lfapi.enums.StatusListaTarefa;
import com.acme.lfapi.enums.StatusTarefa;
import com.acme.lfapi.repositories.ListaTarefaRepository;
import com.acme.lfapi.response.Response;
import com.acme.lfapi.services.ListaTarefaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/listas")
@CrossOrigin(origins = "*")
public class ListaTarefaController {
    private static final Logger log = LoggerFactory.getLogger(ListaTarefaController.class);

    @Autowired
    private ListaTarefaService listaTarefaService;

    @Autowired
    private ListaTarefaRepository listaTarefaRepository;

    public ListaTarefaController() {
    }


    /**
     * Retorna uma lista dado um id.
     *
     * @param id
     * @return ResponseEntity<Response<ListaTarefaDto>>
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<ListaTarefaDto>> buscarPorId(@PathVariable("id") int id) {
        Response<ListaTarefaDto> response = new Response<>();
        Optional<ListaTarefa> listaTarefa = listaTarefaRepository.findById((long) id);

        if (!listaTarefa.isPresent()) {
            log.info("Lista não encontrada para ID: {}", id);
            response.getErrors().add("Lista não encontrada para ID " + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.converterListaTarefaDto(listaTarefa.get()));
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<Response<ArrayList<ListaTarefaDto>>> geral() {
        Response<ArrayList<ListaTarefaDto>> response = new Response<>();
        List<ListaTarefa> todasListas = listaTarefaRepository.findAll();

        ArrayList<ListaTarefaDto> listaTarefas = new ArrayList<>();
        for (ListaTarefa lista : todasListas) {
            listaTarefas.add(this.converterListaTarefaDto(lista));
        }
        response.setData(listaTarefas);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<ListaTarefaUpdateDto>> atualizar(@PathVariable("id") int id,
                                                                    @Valid @RequestBody ListaTarefaUpdateDto listaTarefaUpdateDto, BindingResult result) throws NoSuchAlgorithmException {
        log.info("Atualizando funcionário: {}", listaTarefaUpdateDto.toString());
        Response<ListaTarefaUpdateDto> response = new Response<>();

        Optional<ListaTarefa> listaTarefa = this.listaTarefaRepository.findById((long) id);

        if (!listaTarefa.isPresent()) {
            result.addError(new ObjectError("ListaTarefa", "Lista de tarefa não encontrada."));
        }


        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.atualizarDadosListaTarefa(listaTarefa.get(), listaTarefaUpdateDto, result);

        this.listaTarefaService.persistir(listaTarefa.get());
        response.setData(this.converterListaTarefaUpdateDto(listaTarefa.get()));

        return ResponseEntity.ok(response);
    }

    private void atualizarDadosListaTarefa(ListaTarefa listaTarefa, ListaTarefaUpdateDto listaTarefaUpdateDto, BindingResult result) {
        listaTarefa.setId(listaTarefaUpdateDto.getId());
        Optional<ListaTarefa> tarefa1 = listaTarefaRepository.findById(listaTarefa.getId());
        listaTarefa.setNomeLista(tarefa1.get().getNomeLista());
        if (listaTarefaUpdateDto.getNomeLista().isPresent()) {
            listaTarefaUpdateDto.getNomeLista()
                    .ifPresent(listaTarefa::setNomeLista);
        }
    }

    /**
     * Popula um DTO com os dados de uma lista.
     *
     * @param listaTarefa
     * @return ListaTarefaDto
     */
    private ListaTarefaDto converterListaTarefaDto(ListaTarefa listaTarefa) {
        ListaTarefaDto listaTarefaDto = new ListaTarefaDto();
        listaTarefaDto.setId(listaTarefa.getId());
        listaTarefaDto.setNomeLista(listaTarefa.getNomeLista());
        listaTarefaDto.setStatus(listaTarefa.getStatus());

        return listaTarefaDto;
    }
    /**
     * Popula um DTO com os dados de uma lista.
     *
     * @param listaTarefa
     * @return ListaTarefaDto
     */
    private ListaTarefaUpdateDto converterListaTarefaUpdateDto(ListaTarefa listaTarefa) {

        ListaTarefaUpdateDto listaTarefaUpdateDto = new ListaTarefaUpdateDto();
        listaTarefaUpdateDto.setId(listaTarefa.getId());
        listaTarefaUpdateDto.setStatus(listaTarefa.getStatus());
        listaTarefa.getListaOpt().ifPresent(
                nomeLista -> listaTarefaUpdateDto.setNomeLista(Optional.of(nomeLista)));

        return listaTarefaUpdateDto;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
        log.info("Removendo Lista: {}", id);
        Response<String> response = new Response<String>();
        Optional<ListaTarefa> lancamento = this.listaTarefaRepository.findById(id);

        if (!lancamento.isPresent()) {
            log.info("Erro ao remover devido ao lista de tarefa ID: {} ser inválido.", id);
            response.getErrors().add("Erro ao remover lista de tarefa. Registro não encontrado para o id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.listaTarefaService.remover(id);
        return ResponseEntity.ok(new Response<String>());
    }


}
