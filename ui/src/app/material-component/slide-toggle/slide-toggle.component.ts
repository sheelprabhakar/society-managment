import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatRadioModule } from '@angular/material/radio';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { SMMaterialModule } from 'src/app/sm.material.module';

@Component({
  selector: 'app-slide-toggle',
  standalone: true,
  imports: [SMMaterialModule, FormsModule, MatCardModule, MatRadioModule, MatCheckboxModule, MatSlideToggleModule],
  templateUrl: './slide-toggle.component.html',
  styleUrls: ['./slide-toggle.component.scss']
})
export class SlideToggleComponent {
  color = 'accent';
  checked = false;
  disabled = false;
}