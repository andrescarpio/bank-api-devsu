import { Component } from '@angular/core'; 
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  template: `
    <div style="width:200px; background:#eee; height:100vh; padding:20px;">
      <h3>BANCO</h3>

      <ul style="list-style:none; padding:0;">
        <li (click)="go('clientes')" style="cursor:pointer;">Clientes</li>
        <li (click)="go('cuentas')" style="cursor:pointer;">Cuentas</li>
        <li (click)="go('movimientos')" style="cursor:pointer;">Movimientos</li>
        <li (click)="go('reportes')" style="cursor:pointer;">Reportes</li>
      </ul>
    </div>
  `
})
export class Sidebar {

  constructor(private router: Router) {}

  go(path: string) {
    this.router.navigate([`/${path}`]);
  }
}