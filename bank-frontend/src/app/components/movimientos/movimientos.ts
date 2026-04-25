import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-movimientos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './movimientos.html',
  styleUrls: ['./movimientos.css']
})
export class Movimientos implements OnInit {

  cuentas: any[] = [];

  movimiento: any = {
    numeroCuenta: '',
    valor: 0
  };

  resultado: any = null;

  constructor(
    private api: ApiService,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.cargarCuentas();
  }

  // cargar cuentas para el select
  cargarCuentas() {
    this.api.getCuentas().subscribe({
      next: (data) => {
        this.cuentas = data;
        this.cd.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  // ejecutar movimiento o transaccion
  ejecutarMovimiento() {
    this.api.crearMovimiento(this.movimiento).subscribe({
      next: (res) => {
        this.resultado = res;

        // limpiar formulario
        this.movimiento = {
          numeroCuenta: '',
          valor: 0
        };
        this.cd.detectChanges();
      },
      error: (err) => {
        console.error(err);
        alert(err.error?.message || 'Error en movimiento');
      }
    });
  }
}