package com.acme.lfapi.controllers;

import com.acme.lfapi.dtos.CadastroTarefaDto;
import com.acme.lfapi.entities.ListaTarefa;
import com.acme.lfapi.entities.Tarefa;
import com.acme.lfapi.enums.StatusTarefa;
import com.acme.lfapi.repositories.ListaTarefaRepository;
import com.acme.lfapi.response.Response;
import com.acme.lfapi.services.ListaTarefaService;
import com.acme.lfapi.services.TarefaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping("/api/cadastrar/tarefa")
@CrossOrigin(origins = "*")
@ComponentScan
public class CadastroTarefaController {
    private static final Logger log = LoggerFactory.getLogger(CadastroTarefaController.class);

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private ListaTarefaService listaTarefaService;

    @Autowired
    private ListaTarefaRepository listaTarefaRepository;

    public CadastroTarefaController() {
    }


    /**
     * Cadastra uma tarefa numa lista já existente no sistema
     *
     * @param cadastroTarefaDto
     * @param result
     * @return ResponseEntity<Response < CadastroTarefaDto>>
     * @throws NoSuchAlgorithmException
     */
    @PostMapping
    public ResponseEntity<Response<CadastroTarefaDto>> cadastrar(@Valid @RequestBody CadastroTarefaDto cadastroTarefaDto,
                                                                 BindingResult result) throws NoSuchAlgorithmException {
        log.info("Cadastrando Lista de tarefas : {}", cadastroTarefaDto.toString());
        Response<CadastroTarefaDto> response = new Response<>();

        validarDadosExistentes(cadastroTarefaDto, result);

        if (result.hasErrors()) {
            log.error("Erro validando dados da tarefa: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        ListaTarefa listaTarefa = this.converterDtoParaListaTarefa(cadastroTarefaDto, result);
        Tarefa tarefa = this.converterDtoParaTarefa(cadastroTarefaDto);

        tarefa.setListaTarefa(listaTarefa);
        this.tarefaService.persistir(tarefa);


        response.setData(this.converterCadastroTarefaDtoto(tarefa));
        return ResponseEntity.ok(response);
    }

    private ListaTarefa converterDtoParaListaTarefa(CadastroTarefaDto cadastroTarefaDto, BindingResult result) throws NoSuchAlgorithmException {
        ListaTarefa listaTarefa = new ListaTarefa();
        listaTarefa.setId(cadastroTarefaDto.getListaId());

        return listaTarefa;

    }

    private CadastroTarefaDto converterCadastroTarefaDtoto(Tarefa tarefa) {
        CadastroTarefaDto cadastroTarefaDto = new CadastroTarefaDto();
        cadastroTarefaDto.setId(tarefa.getId());
        cadastroTarefaDto.setNomeTarefa(tarefa.getTarefa());
        cadastroTarefaDto.setStatus(tarefa.getStatus());
        cadastroTarefaDto.setListaId(tarefa.getListaTarefa().getId());

        return cadastroTarefaDto;
    }

    private Tarefa converterDtoParaTarefa(CadastroTarefaDto cadastroTarefaDto) {

        Optional<ListaTarefa> listaTarefa = listaTarefaRepository.findById(cadastroTarefaDto.getListaId());

        Tarefa tarefa = new Tarefa();
        tarefa.setTarefa(cadastroTarefaDto.getNomeTarefa());
        tarefa.setStatus(StatusTarefa.INCOMPLETA);
        tarefa.setListaTarefa(listaTarefa.get());

        return tarefa;
    }

    /**
     * Verifica se já existe uma lista com este nome
     *
     * @param cadastroTarefaDto
     * @param result
     */
    private void validarDadosExistentes(CadastroTarefaDto cadastroTarefaDto, BindingResult result) {

        if(!this.listaTarefaRepository.findById(cadastroTarefaDto.getListaId()).isPresent()){
            result.addError(new ObjectError("lista", "Lista inválida"));
        }


    }


}
