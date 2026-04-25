import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Sidebar } from './layout/sidebar/sidebar';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, Sidebar],
  template: `
    <div style="display:flex; height:100vh;">
      <app-sidebar></app-sidebar>

      <div style="flex:1; padding:20px;">
        <router-outlet></router-outlet>
      </div>
    </div>
  `
})
export class App {}