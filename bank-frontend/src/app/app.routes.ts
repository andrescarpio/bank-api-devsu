import { Routes } from '@angular/router';
import { ClientesComponent } from './components/clientes/clientes.component';

import { Cuentas } from './components/cuentas/cuentas';
import { Movimientos } from './components/movimientos/movimientos';
import { Reportes } from './components/reportes/reportes';

export const routes: Routes = [
  { path: '', redirectTo: 'clientes', pathMatch: 'full' },

  { path: 'clientes', component: ClientesComponent },
  { path: 'cuentas', component: Cuentas },
  { path: 'movimientos', component: Movimientos },
  { path: 'reportes', component: Reportes },

  { path: '**', redirectTo: 'clientes' }
];