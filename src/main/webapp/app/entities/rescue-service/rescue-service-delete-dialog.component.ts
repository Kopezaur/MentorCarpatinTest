import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRescueService } from 'app/shared/model/rescue-service.model';
import { RescueServiceService } from './rescue-service.service';

@Component({
    selector: 'jhi-rescue-service-delete-dialog',
    templateUrl: './rescue-service-delete-dialog.component.html'
})
export class RescueServiceDeleteDialogComponent {
    rescueService: IRescueService;

    constructor(
        protected rescueServiceService: RescueServiceService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rescueServiceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rescueServiceListModification',
                content: 'Deleted an rescueService'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rescue-service-delete-popup',
    template: ''
})
export class RescueServiceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rescueService }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RescueServiceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.rescueService = rescueService;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/rescue-service', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/rescue-service', { outlets: { popup: null } }]);
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
