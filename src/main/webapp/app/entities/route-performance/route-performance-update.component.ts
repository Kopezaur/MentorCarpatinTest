import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRoutePerformance, RoutePerformance } from 'app/shared/model/route-performance.model';
import { RoutePerformanceService } from './route-performance.service';
import { IRoute } from 'app/shared/model/route.model';
import { RouteService } from 'app/entities/route';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person';

@Component({
    selector: 'jhi-route-performance-update',
    templateUrl: './route-performance-update.component.html'
})
export class RoutePerformanceUpdateComponent implements OnInit {
    isSaving: boolean;

    routes: IRoute[];

    people: IPerson[];

    editForm = this.fb.group({
        id: [],
        time: [],
        pace: [],
        speed: [],
        performanceCoefficient: [],
        route: [],
        person: []
    });

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected routePerformanceService: RoutePerformanceService,
        protected routeService: RouteService,
        protected personService: PersonService,
        protected activatedRoute: ActivatedRoute,
        private fb: FormBuilder
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ routePerformance }) => {
            this.updateForm(routePerformance);
        });
        this.routeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRoute[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRoute[]>) => response.body)
            )
            .subscribe((res: IRoute[]) => (this.routes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.personService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPerson[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPerson[]>) => response.body)
            )
            .subscribe((res: IPerson[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    updateForm(routePerformance: IRoutePerformance) {
        this.editForm.patchValue({
            id: routePerformance.id,
            time: routePerformance.time,
            pace: routePerformance.pace,
            speed: routePerformance.speed,
            performanceCoefficient: routePerformance.performanceCoefficient,
            route: routePerformance.route,
            person: routePerformance.person
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        const routePerformance = this.createFromForm();
        if (routePerformance.id !== undefined) {
            this.subscribeToSaveResponse(this.routePerformanceService.update(routePerformance));
        } else {
            this.subscribeToSaveResponse(this.routePerformanceService.create(routePerformance));
        }
    }

    private createFromForm(): IRoutePerformance {
        return {
            ...new RoutePerformance(),
            id: this.editForm.get(['id']).value,
            time: this.editForm.get(['time']).value,
            pace: this.editForm.get(['pace']).value,
            speed: this.editForm.get(['speed']).value,
            performanceCoefficient: this.editForm.get(['performanceCoefficient']).value,
            route: this.editForm.get(['route']).value,
            person: this.editForm.get(['person']).value
        };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoutePerformance>>) {
        result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRouteById(index: number, item: IRoute) {
        return item.id;
    }

    trackPersonById(index: number, item: IPerson) {
        return item.id;
    }
}
