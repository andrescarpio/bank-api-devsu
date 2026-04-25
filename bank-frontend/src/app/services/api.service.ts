import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cliente } from '../models/cliente.model';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  /*getClientes() {
    return this.http.get<any[]>(`${this.baseUrl}/clientes`);
  }*/

  getClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.baseUrl}/clientes`);
  }

  crearCliente(cliente: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/clientes`, cliente);
  }

  actualizarCliente(id: number, cliente: any) {
    return this.http.put(`${this.baseUrl}/clientes/${id}`, cliente);
  }

  eliminarCliente(id: number) {
    return this.http.delete(`${this.baseUrl}/clientes/${id}`);
  }

  getCuentas() {
    return this.http.get<any[]>(`${this.baseUrl}/cuentas`);
  }

  crearCuenta(cuenta: any) {
    return this.http.post(`${this.baseUrl}/cuentas`, cuenta);
  }

  eliminarCuenta(id: number) {
    return this.http.delete(`${this.baseUrl}/cuentas/${id}`);
  }

  crearMovimiento(data: any) {
    return this.http.post(`${this.baseUrl}/movimientos`, data);
  }

  getReporte(url: string) {
    return this.http.get<any[]>(`${this.baseUrl}${url}`);
  }

}