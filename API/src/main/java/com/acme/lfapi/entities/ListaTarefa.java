package com.acme.lfapi.entities;

import com.acme.lfapi.enums.StatusListaTarefa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "lista_tarefa")
public class ListaTarefa implements Serializable {

    private static final long serialVersionUID = 3960436649365666213L;


    private Long id;
    private String nomeLista;
    private Date dataCriacao;
    private Date dataAtualizacao;
    private StatusListaTarefa status;
    private List<Tarefa> tarefas;

    public ListaTarefa() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "nome_tarefa", nullable = false)
    public String getNomeLista() {
        return nomeLista;
    }

    public void setNomeLista(String name_tasklist) {
        this.nomeLista = name_tasklist;
    }

    @Transient
    public Optional<String> getListaOpt() {
        return Optional.ofNullable(nomeLista);
    }

    @Column(name = "data_criacao", nullable = false)
    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date date_create) {
        this.dataCriacao = date_create;
    }

    @Column(name = "data_atualizacao")
    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date date_updated) {
        this.dataAtualizacao = date_updated;
    }

    @Column(name = "status", nullable = false)
    public StatusListaTarefa getStatus() {
        return status;
    }

    public void setStatus(StatusListaTarefa status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "listaTarefa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    @PreUpdate
    public void preUpdate() {
        dataAtualizacao = new Date();
    }

    @PrePersist
    public void prePersist() {
        Date dataAtual = new Date();
        dataCriacao = dataAtual;
        dataAtualizacao = dataAtual;
    }

    @Override
    public String toString() {
        return "ListaTarefa [id=" + id + ", nomeLista=" + nomeLista + ", dataCriacao=" + dataCriacao
                + ", dataAtualizacao=" + dataAtualizacao + "]";
    }
}
