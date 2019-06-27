import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRescueService } from 'app/shared/model/rescue-service.model';
import { AccountService } from 'app/core';
import { RescueServiceService } from './rescue-service.service';

@Component({
    selector: 'jhi-rescue-service',
    templateUrl: './rescue-service.component.html'
})
export class RescueServiceComponent implements OnInit, OnDestroy {
    rescueServices: IRescueService[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected rescueServiceService: RescueServiceService,
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
            this.rescueServiceService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IRescueService[]>) => res.ok),
                    map((res: HttpResponse<IRescueService[]>) => res.body)
                )
                .subscribe((res: IRescueService[]) => (this.rescueServices = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.rescueServiceService
            .query()
            .pipe(
                filter((res: HttpResponse<IRescueService[]>) => res.ok),
                map((res: HttpResponse<IRescueService[]>) => res.body)
            )
            .subscribe(
                (res: IRescueService[]) => {
                    this.rescueServices = res;
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
        this.registerChangeInRescueServices();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRescueService) {
        return item.id;
    }

    registerChangeInRescueServices() {
        this.eventSubscriber = this.eventManager.subscribe('rescueServiceListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
