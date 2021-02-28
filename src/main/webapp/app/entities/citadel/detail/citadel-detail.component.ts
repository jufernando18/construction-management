import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICitadel } from '../citadel.model';

@Component({
  selector: 'jhi-citadel-detail',
  templateUrl: './citadel-detail.component.html',
})
export class CitadelDetailComponent implements OnInit {
  citadel: ICitadel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ citadel }) => {
      this.citadel = citadel;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
