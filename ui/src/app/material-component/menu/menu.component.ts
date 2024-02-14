import { Component } from '@angular/core';
import { MatMenuModule } from '@angular/material/menu';
import { SMMaterialModule } from 'src/app/sm.material.module';
@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [SMMaterialModule, MatMenuModule],
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent { }
