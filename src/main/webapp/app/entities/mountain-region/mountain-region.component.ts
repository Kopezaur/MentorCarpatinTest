import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMountainRegion } from 'app/shared/model/mountain-region.model';
import { AccountService } from 'app/core';
import { MountainRegionService } from './mountain-region.service';

@Component({
    selector: 'jhi-mountain-region',
    templateUrl: './mountain-region.component.html'
})
export class MountainRegionComponent implements OnInit, OnDestroy {
    mountainRegions: IMountainRegion[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected mountainRegionService: MountainRegionService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.mountainRegionService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IMountainRegion[]>) => res.ok),
                    map((res: HttpResponse<IMountainRegion[]>) => res.body)
                )
                .subscribe((res: IMountainRegion[]) => (this.mountainRegions = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.mountainRegionService
            .query()
            .pipe(
                filter((res: HttpResponse<IMountainRegion[]>) => res.ok),
                map((res: HttpResponse<IMountainRegion[]>) => res.body)
            )
            .subscribe(
                (res: IMountainRegion[]) => {
                    this.mountainRegions = res;
                    this.currentSearch = '';
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMountainRegions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMountainRegion) {
        return item.id;
    }

    registerChangeInMountainRegions() {
        this.eventSubscriber = this.eventManager.subscribe('mountainRegionListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
