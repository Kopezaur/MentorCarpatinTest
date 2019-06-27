import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRoutePerformance } from 'app/shared/model/route-performance.model';

@Component({
    selector: 'jhi-route-performance-detail',
    templateUrl: './route-performance-detail.component.html'
})
export class RoutePerformanceDetailComponent implements OnInit {
    routePerformance: IRoutePerformance;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ routePerformance }) => {
            this.routePerformance = routePerformance;
        });
    }

    previousState() {
        window.history.back();
    }
}
