import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { SMMaterialModule } from 'src/app/sm.material.module';

@Component({
  selector: 'app-buttons',
  standalone: true,
  imports: [SMMaterialModule, MatButtonModule],
  templateUrl: './buttons.component.html',
  styleUrls: ['./buttons.component.scss']
})
export class ButtonsComponent {
  constructor() { }
}
