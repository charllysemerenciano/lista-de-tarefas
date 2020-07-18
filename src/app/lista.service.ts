import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ListaService {

  listasUrl = 'http://localhost:8080/api/listas/';
  cadastroListaUrl = 'http://localhost:8080/api/cadastrar/lista';

  constructor(private http: HttpClient) {
  }


  listar() {
    return this.http.get<any[]>(`${this.listasUrl}`);
  }

  criar(lista: any) {
    console.log(lista)
    return this.http.post(this.cadastroListaUrl, lista);
  }
}
