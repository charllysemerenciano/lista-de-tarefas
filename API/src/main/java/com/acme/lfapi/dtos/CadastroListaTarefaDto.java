package com.acme.lfapi.dtos;

import com.acme.lfapi.enums.StatusListaTarefa;
import org.springframework.context.annotation.ComponentScan;

import javax.validation.constraints.NotEmpty;

@ComponentScan
public class CadastroListaTarefaDto {
	
	private Long id;
	private String nomeLista;
	private StatusListaTarefa status;

	public CadastroListaTarefaDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Nome da Lista é um parametro obrigatorio")
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

	@Override
	public String toString() {
		return "ListaTarefaDto [id=" + id + ", nomeLista=" + nomeLista + ", status=" + status + "]";
	}

}
