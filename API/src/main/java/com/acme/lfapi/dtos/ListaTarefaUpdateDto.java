package com.acme.lfapi.dtos;

import com.acme.lfapi.enums.StatusListaTarefa;
import com.acme.lfapi.enums.StatusTarefa;

import java.util.Optional;

public class ListaTarefaUpdateDto {
    private Long id;
    private Optional<String> nomeLista;
    private StatusListaTarefa status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Optional<String> getNomeLista() {
        return nomeLista;
    }

    public void setNomeLista(Optional<String> nomeLista) {
        this.nomeLista = nomeLista;
    }

    public StatusListaTarefa getStatus() {
        return status;
    }

    public void setStatus(StatusListaTarefa status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ListaTarefaUpdateDto [id=" + id + ", nomelista=" + nomeLista + ", status=" + status + "]";
    }
}
