import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRescueService } from 'app/shared/model/rescue-service.model';

@Component({
    selector: 'jhi-rescue-service-detail',
    templateUrl: './rescue-service-detail.component.html'
})
export class RescueServiceDetailComponent implements OnInit {
    rescueService: IRescueService;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rescueService }) => {
            this.rescueService = rescueService;
        });
    }

    previousState() {
        window.history.back();
    }
}
