import { Component } from '@angular/core';
import { MatExpansionModule } from '@angular/material/expansion';
import { SMMaterialModule } from 'src/app/sm.material.module';

@Component({
  selector: 'app-expansion',
  standalone: true,
  imports: [SMMaterialModule],
  templateUrl: './expansion.component.html',
  styleUrls: ['./expansion.component.scss']
})
export class ExpansionComponent {
  panelOpenState = false;
  step = 0;

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }
}
