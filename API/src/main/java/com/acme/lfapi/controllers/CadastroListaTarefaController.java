package com.acme.lfapi.controllers;

import com.acme.lfapi.dtos.CadastroListaTarefaDto;
import com.acme.lfapi.entities.ListaTarefa;
import com.acme.lfapi.enums.StatusListaTarefa;
import com.acme.lfapi.response.Response;
import com.acme.lfapi.services.ListaTarefaService;
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

@RestController
@RequestMapping("/api/cadastrar/lista")
@CrossOrigin(origins = "*")
@ComponentScan
public class CadastroListaTarefaController {

    private static final Logger log = LoggerFactory.getLogger(CadastroListaTarefaController.class);

    @Autowired
    private ListaTarefaService listaTarefaService;


    public CadastroListaTarefaController() {
    }

    /**
     * Cadastra uma lista de tarefas no sistema
     *
     * @param cadastroListaTarefaDto
     * @param result
     * @return ResponseEntity<Response < CadastroListaTarefaDto>>
     * @throws NoSuchAlgorithmException
     */
    @PostMapping
    public ResponseEntity<Response<CadastroListaTarefaDto>> cadastrar(@Valid @RequestBody CadastroListaTarefaDto cadastroListaTarefaDto,
                                                                      BindingResult result) throws NoSuchAlgorithmException {
        log.info("Cadastrando Lista de tarefas : {}", cadastroListaTarefaDto.toString());
        Response<CadastroListaTarefaDto> response = new Response<>();

        validarDadosExistentes(cadastroListaTarefaDto, result);
        ListaTarefa listaTarefa = this.converterDtoParaListaTarefa(cadastroListaTarefaDto);

        if (result.hasErrors()) {
            log.error("Erro validando dados de lista de tarefas: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.listaTarefaService.persistir(listaTarefa);
        response.setData(this.converterCadastroListaTarefaDtoto(listaTarefa));
        return ResponseEntity.ok(response);
    }

    /**
     * Verifica se já existe uma lista com este nome
     *
     * @param cadastroListaTarefaDto
     * @param result
     */
    private void validarDadosExistentes(CadastroListaTarefaDto cadastroListaTarefaDto, BindingResult result) {

        this.listaTarefaService.buscaPorNome(cadastroListaTarefaDto.getNomeLista())
                .ifPresent(emp -> result.addError(new ObjectError("lista", "Lista já existente.")));
    }

    /**
     * Converte os dados do DTO para ListaTarefa.
     *
     * @param cadastroListaTarefaDto
     * @return ListaTarefa
     */
    private ListaTarefa converterDtoParaListaTarefa(CadastroListaTarefaDto cadastroListaTarefaDto) {
        ListaTarefa listaTarefa = new ListaTarefa();
        listaTarefa.setNomeLista(cadastroListaTarefaDto.getNomeLista());
        listaTarefa.setStatus(StatusListaTarefa.VAZIA);
        return listaTarefa;
    }

    /**
     * Popula o DTO de cadastro com os dados da lista
     *
     * @param listaTarefa
     * @return CadastroListaTarefaDto
     */
    private CadastroListaTarefaDto converterCadastroListaTarefaDtoto(ListaTarefa listaTarefa) {
        CadastroListaTarefaDto cadastroLista = new CadastroListaTarefaDto();
        cadastroLista.setId(listaTarefa.getId());
        cadastroLista.setNomeLista(listaTarefa.getNomeLista());
        cadastroLista.setStatus(listaTarefa.getStatus());

        return cadastroLista;
    }

}
