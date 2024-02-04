import { Component, OnInit } from '@angular/core';
import { Activity, activities } from './activity-timeline-data';
import { SMMaterialModule } from 'src/app/sm.material.module';
import { NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-activity-timeline',
  standalone: true,
  imports: [SMMaterialModule, NgIf, NgFor],
  templateUrl: './activity-timeline.component.html'
})
export class ActivityTimelineComponent implements OnInit {

  activityData: Activity[];

  constructor() {

    this.activityData = activities;
  }


  ngOnInit(): void {
  }

}
