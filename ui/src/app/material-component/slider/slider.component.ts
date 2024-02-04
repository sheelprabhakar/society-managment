import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSliderModule } from '@angular/material/slider';
import { SMMaterialModule } from 'src/app/sm.material.module';

@Component({
  selector: 'app-slider',
  standalone: true,
  imports: [SMMaterialModule, FormsModule, ReactiveFormsModule, MatSliderModule],
  templateUrl: './slider.component.html',
  styleUrls: ['./slider.component.scss']
})
export class SliderComponent {

  demo: number = 0;
  val = 50;
  min = 0;
  max = 100;

  constructor() { }
}
