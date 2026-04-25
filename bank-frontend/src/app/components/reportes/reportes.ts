import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
// Para generar PDF
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reportes.html',
  styleUrls: ['./reportes.css']
})
export class Reportes implements OnInit {

  clientes: any[] = [];
  reportes: any[] = [];

  filtro = {
    clienteId: null,
    desde: '',
    hasta: ''
  };

  constructor(
    private api: ApiService,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.cargarClientes();
  }

  // cargar clientes para el select
  cargarClientes() {
    this.api.getClientes().subscribe({
      next: (data) => {
        this.clientes = data;
        this.cd.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  // consultar reporte
  consultar() {

  // validar SOLO fechas
  if (!this.filtro.desde || !this.filtro.hasta) {
    alert('Debe seleccionar rango de fechas');
    return;
  }

  let url = `/movimientos/reportes?desde=${this.filtro.desde}&hasta=${this.filtro.hasta}`;

  // agregar cliente solo si existe
  if (this.filtro.clienteId) {
    url += `&clienteId=${this.filtro.clienteId}`;
  }

  this.api.getReporte(url).subscribe({
    next: (data) => {

      this.reportes = data.sort(
        (a: any, b: any) =>
          new Date(a.fecha).getTime() - new Date(b.fecha).getTime()
      );

      this.cd.detectChanges();
    },
    error: (err) => {
      console.error(err);
      alert('Error al consultar reporte');
    }
  });
}

  // generar reporte
  generarPDF() {
  const doc = new jsPDF();

  // título
  doc.text('Reporte de Movimientos', 14, 10);

  // columnas
  const columns = [
    'Fecha',
    'Cliente',
    'Cuenta',
    'Tipo',
    'Saldo Inicial',
    'Estado',
    'Movimiento',
    'Saldo'
  ];

  // filas
  const rows = this.reportes.map(r => [
    new Date(r.fecha).toLocaleString(),
    r.cliente,
    r.numeroCuenta,
    r.tipoCuenta,
    r.saldoInicial,
    r.estado ? 'Activo' : 'Inactivo',
    r.movimiento,
    r.saldoDisponible
  ]);

  // tabla
  autoTable(doc, {
    head: [columns],
    body: rows,
    startY: 20
  });

  doc.save('reporte_movimientos.pdf');
}
}