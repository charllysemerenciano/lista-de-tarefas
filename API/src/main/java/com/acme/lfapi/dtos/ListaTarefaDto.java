package com.acme.lfapi.dtos;

import com.acme.lfapi.enums.StatusListaTarefa;

public class ListaTarefaDto {
    private Long id;
    private String nomeLista;
    private StatusListaTarefa status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeLista() {
        return nomeLista;
    }

    public void setNomeLista(String nomeLista) {
        this.nomeLista = nomeLista;
    }

    public StatusListaTarefa getStatus() {
        return status;
    }

    public void setStatus(StatusListaTarefa status) {
        this.status = status;
    }
}
