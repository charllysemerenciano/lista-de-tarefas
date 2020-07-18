package com.acme.lfapi.controllers;

import com.acme.lfapi.dtos.TarefaDto;
import com.acme.lfapi.dtos.TarefaUpdateDto;
import com.acme.lfapi.entities.Tarefa;
import com.acme.lfapi.response.Response;
import com.acme.lfapi.services.TarefaService;
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
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "*")
public class TarefaController {
    private static final Logger log = LoggerFactory.getLogger(ListaTarefaController.class);


    @Autowired
    private TarefaService tarefaService;

    public TarefaController() {
    }


    /**
     * Retorna uma lista dado um id.
     *
     * @param id
     * @return ResponseEntity<Response < TarefaDto>>
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<ArrayList<TarefaDto>>> buscarPorId(@PathVariable("id") int id) {
        Response<ArrayList<TarefaDto>> response = new Response<>();
        List<Tarefa> tarefas = tarefaService.buscarPorListaId((long) id);

        if (tarefas.size() == 0) {
            response.getErrors().add("Lista não encontrada para ID " + id);
            return ResponseEntity.badRequest().body(response);
        }
        ArrayList<TarefaDto> listaTarefas = new ArrayList<>();
        for (Tarefa tarefa : tarefas) {
            listaTarefas.add(this.converterListaTarefaDto(tarefa));
        }
        response.setData(listaTarefas);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<TarefaUpdateDto>> atualizar(@PathVariable("id") int id,
                                                               @Valid @RequestBody TarefaUpdateDto tarefaUpdateDto, BindingResult result) throws NoSuchAlgorithmException {
        log.info("Atualizando funcionário: {}", tarefaUpdateDto.toString());
        Response<TarefaUpdateDto> response = new Response<>();

        Optional<Tarefa> tarefa = this.tarefaService.buscarPorId((long) id);

        if (!tarefa.isPresent()) {
            result.addError(new ObjectError("tarefa", "Tarena não encontrada."));
        }


        if (result.hasErrors()) {
            log.error("Erro validando funcionário: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.atualizarDadosTarefa(tarefa.get(), tarefaUpdateDto, result);

        this.tarefaService.persistir(tarefa.get());
        response.setData(this.converterTarefaDto(tarefa.get()));

        return ResponseEntity.ok(response);
    }

    private void atualizarDadosTarefa(Tarefa tarefa, TarefaUpdateDto tarefaUpdateDto, BindingResult result) {
        tarefa.setId(tarefaUpdateDto.getId());
        Optional<Tarefa> tarefa1 = tarefaService.buscarPorId(tarefa.getId());
        if (tarefa1.isPresent()) {
            tarefa.setTarefa(tarefa1.get().getTarefa());
            if (tarefaUpdateDto.getNomeTarefa().isPresent()) {
                tarefaUpdateDto.getNomeTarefa()
                        .ifPresent(tarefa::setTarefa);
            }

            tarefa.setStatus(tarefa1.get().getStatus());
            if (tarefaUpdateDto.getStatus().isPresent()) {
                tarefaUpdateDto.getStatus()
                        .ifPresent(tarefa::setStatus);
            }


        } else {
            result.addError(new ObjectError("Tarefa", "Tarefa não encontrada"));
        }
//        tarefa.setTarefa(null);

    }

    /**
     * Popula um DTO com os dados de uma lista.
     *
     * @param tarefa
     * @return ListaTarefaDto
     */
    private TarefaDto converterListaTarefaDto(Tarefa tarefa) {
        TarefaDto tarefaDto = new TarefaDto();
        tarefaDto.setId(tarefa.getId());
        tarefaDto.setNomeTarefa(tarefa.getTarefa());
        tarefaDto.setStatus(tarefa.getStatus());
        tarefaDto.setListaId(tarefa.getListaTarefa().getId());

        return tarefaDto;
    }


    /**
     * Retorna um DTO com os dados de um funcionário.
     *
     * @param tarefa
     * @return TarefaUpdateDto
     */
    private TarefaUpdateDto converterTarefaDto(Tarefa tarefa) {
        TarefaUpdateDto tarefaUpdateDto = new TarefaUpdateDto();
        tarefaUpdateDto.setId(tarefa.getId());
        tarefa.getTarefaOpt().ifPresent(
                nomeTarefa -> tarefaUpdateDto.setNomeTarefa(Optional.of(nomeTarefa)));
        tarefa.getStatusOpt().ifPresent(
                status -> tarefaUpdateDto.setStatus(Optional.of(status)));
        return tarefaUpdateDto;
    }

}
