import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMountainRegion } from 'app/shared/model/mountain-region.model';
import { MountainRegionService } from './mountain-region.service';

@Component({
    selector: 'jhi-mountain-region-delete-dialog',
    templateUrl: './mountain-region-delete-dialog.component.html'
})
export class MountainRegionDeleteDialogComponent {
    mountainRegion: IMountainRegion;

    constructor(
        protected mountainRegionService: MountainRegionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mountainRegionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'mountainRegionListModification',
                content: 'Deleted an mountainRegion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mountain-region-delete-popup',
    template: ''
})
export class MountainRegionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mountainRegion }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MountainRegionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.mountainRegion = mountainRegion;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/mountain-region', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/mountain-region', { outlets: { popup: null } }]);
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
