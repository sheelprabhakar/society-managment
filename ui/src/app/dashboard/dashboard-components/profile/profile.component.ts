import { Component, OnInit } from '@angular/core';
import { SMMaterialModule } from 'src/app/sm.material.module';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [SMMaterialModule],
  templateUrl: './profile.component.html',
})
export class ProfileComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
