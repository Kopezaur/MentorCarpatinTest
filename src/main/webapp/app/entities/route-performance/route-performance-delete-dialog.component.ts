import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRoutePerformance } from 'app/shared/model/route-performance.model';
import { RoutePerformanceService } from './route-performance.service';

@Component({
    selector: 'jhi-route-performance-delete-dialog',
    templateUrl: './route-performance-delete-dialog.component.html'
})
export class RoutePerformanceDeleteDialogComponent {
    routePerformance: IRoutePerformance;

    constructor(
        protected routePerformanceService: RoutePerformanceService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.routePerformanceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'routePerformanceListModification',
                content: 'Deleted an routePerformance'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-route-performance-delete-popup',
    template: ''
})
export class RoutePerformanceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ routePerformance }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RoutePerformanceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.routePerformance = routePerformance;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/route-performance', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/route-performance', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
