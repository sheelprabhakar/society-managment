import {Component} from '@angular/core';
import { MatTabsModule } from '@angular/material/tabs';
import { SMMaterialModule } from 'src/app/sm.material.module';

@Component({
  selector: 'app-tabs',
  standalone: true,
  imports:[SMMaterialModule, MatTabsModule],
  templateUrl: './tabs.component.html',
  styleUrls: ['./tabs.component.scss']
})
export class TabsComponent {

}
