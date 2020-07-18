package com.acme.lfapi.dtos;

import com.acme.lfapi.entities.ListaTarefa;
import com.acme.lfapi.enums.StatusTarefa;
import org.springframework.context.annotation.ComponentScan;

import javax.validation.constraints.NotEmpty;

@ComponentScan
public class CadastroTarefaDto {

    private Long id;
    private String nomeTarefa;
    private StatusTarefa status;
    private Long listaId;

    public CadastroTarefaDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @NotEmpty(message = "Nome da tarefa é um parametro obrigatorio")
    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public StatusTarefa getStatus() {
        return status;
    }

    public void setStatus(StatusTarefa status) {
        this.status = status;
    }

    public Long getListaId() {
        return listaId;
    }

    public void setListaId(Long listaId) {
        this.listaId = listaId;
    }

    @Override
    public String toString() {
        return "TarefaDto [id=" + id + ", nomeTarefa=" + nomeTarefa + ", status=" + status + "]";
    }
}
