package com.acme.lfapi.dtos;

import com.acme.lfapi.enums.StatusTarefa;

import java.util.Optional;

public class TarefaUpdateDto {
    private Long id;
    private Optional<String> nomeTarefa;
    private Optional<StatusTarefa> status;
    private Long listaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Optional<String> getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(Optional<String> nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public Optional<StatusTarefa> getStatus() {
        return status;
    }

    public void setStatus(Optional<StatusTarefa> status) {
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
        return "TarefaUpdateDto [id=" + id + ", nomeTarefa=" + nomeTarefa + ", status=" + status + "]";
    }
}
