import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-cuentas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cuentas.html',
  styleUrls: ['./cuentas.css']
})
export class Cuentas implements OnInit {

  cuentas: any[] = [];
  cuentasOriginal: any[] = [];
  clientes: any[] = [];
  filtro: string = '';

  mostrarFormulario = false;

  nueva: any = {
    numeroCuenta: '',
    tipoCuenta: '',
    saldoInicial: 0,
    estado: true,
    clienteId: ''
  };

  constructor(
    private api: ApiService,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.cargarCuentas();
    this.cargarClientes();
  }

  cargarCuentas() {
    this.api.getCuentas().subscribe({
      next: (data) => {
        this.cuentas = data;
        this.cuentasOriginal = data;
        this.cd.detectChanges();
      },
      error: (err) => console.error(err)
    });
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

  filtrarCuentas() {
    if (!this.filtro || this.filtro.trim() === '') {
      this.cuentas = this.cuentasOriginal;
      return;
    }

    const texto = this.filtro.toLowerCase();

    this.cuentas = this.cuentasOriginal.filter(c =>
      c.numeroCuenta.toLowerCase().includes(texto) ||
      c.clienteNombre.toLowerCase().includes(texto)
    );
  }

  crearCuenta() {
    this.api.crearCuenta(this.nueva).subscribe({
      next: () => {
        this.cargarCuentas();

        this.nueva = {
          numeroCuenta: '',
          tipoCuenta: '',
          saldoInicial: 0,
          estado: true,
          clienteId: ''
        };

        this.mostrarFormulario = false;
      }
    });
  }

  eliminar(id: number) {
    this.api.eliminarCuenta(id).subscribe({
      next: () => {
        this.cargarCuentas();
      }
    });
  }

  cancelar() {
    this.mostrarFormulario = false;

    this.nueva = {
      numeroCuenta: '',
      tipoCuenta: '',
      saldoInicial: 0,
      estado: true,
      clienteId: ''
    };
  }
}