package com.acme.lfapi.dtos;

import com.acme.lfapi.enums.StatusTarefa;

public class TarefaDto {
    private Long id;
    private String nomeTarefa;
    private StatusTarefa status;
    private Long listaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

