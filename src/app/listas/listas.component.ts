import {Component, OnInit} from '@angular/core';
import {ListaService} from '../lista.service';
import {FormGroup} from '@angular/forms';


@Component({
  selector: 'app-listas',
  templateUrl: './listas.component.html',
  styleUrls: ['./listas.component.css']
})
export class ListasComponent implements OnInit {
  listas: Array<any>;
  lista: any;

  constructor(private listaService: ListaService) {
  }

  ngOnInit(): void {
    this.lista = {};
    this.listar();

  }

  listar() {
    this.listaService.listar().subscribe(dados => this.listas = dados.data);
  }

  criarLista(frm: FormGroup) {
    this.listaService.criar(this.lista).subscribe(dados => {
      this.listas.push(dados.data);
      frm.reset();
    });
  }
}
