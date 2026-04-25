import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { Cliente } from '../../models/cliente.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './clientes.html',
  styleUrls: ['./clientes.css']
})
export class ClientesComponent implements OnInit {

  clientes: Cliente[] = [];
  filtro: string = '';
  mostrarFormulario: boolean = false;
  editando: boolean = false;

  nuevo: any = {
    nombre: '',
    genero: '',
    edad: 0,
    identificacion: '',
    direccion: '',
    telefono: '',
    contrasena: '',
    estado: true
  };

  constructor(private api: ApiService, private cd: ChangeDetectorRef) {}

  ngOnInit() {
    this.cargarClientes();
  }

  cancelar() {
    this.nuevo = {
      nombre: '',
      genero: '',
      edad: 0,
      identificacion: '',
      direccion: '',
      telefono: '',
      contrasena: '',
      estado: true
    };

    this.mostrarFormulario = false;
    this.editando = false;

    this.cd.detectChanges();
  }

  filtrarClientes() {
    if (!this.filtro || this.filtro.trim() === '') {
      this.cargarClientes();
    } else {
      this.clientes = this.clientes.filter(c =>
        c.nombre.toLowerCase().includes(this.filtro.toLowerCase())
      );
    }
  }

  cargarClientes() {
    this.api.getClientes().subscribe({
      next: (data) => {
        this.clientes = data;
        this.cd.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  crearCliente() {

    if (this.editando) {

      this.api.actualizarCliente(this.nuevo.clienteId, this.nuevo).subscribe({
        next: () => {
          this.cargarClientes();
          this.cancelar();
          this.cd.detectChanges();
        }
      });

    } else {

      this.api.crearCliente(this.nuevo).subscribe({
        next: () => {
          this.cargarClientes();
          this.cancelar();
          this.cd.detectChanges();
        }
      });

    }

  }

  eliminar(id: number) {
    this.api.eliminarCliente(id).subscribe({
      next: () => {
        this.cargarClientes();
        this.cd.detectChanges();
      }
    });
  }

  editar(cliente: Cliente) {
    this.nuevo = { ...cliente };
    this.mostrarFormulario = true;
    this.editando = true;

    this.cd.detectChanges();
  }
}