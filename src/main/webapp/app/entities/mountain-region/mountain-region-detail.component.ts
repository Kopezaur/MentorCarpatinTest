import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMountainRegion } from 'app/shared/model/mountain-region.model';

@Component({
    selector: 'jhi-mountain-region-detail',
    templateUrl: './mountain-region-detail.component.html'
})
export class MountainRegionDetailComponent implements OnInit {
    mountainRegion: IMountainRegion;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mountainRegion }) => {
            this.mountainRegion = mountainRegion;
        });
    }

    previousState() {
        window.history.back();
    }
}
