import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRoute, Route } from 'app/shared/model/route.model';
import { RouteService } from './route.service';
import { IMountainRegion } from 'app/shared/model/mountain-region.model';
import { MountainRegionService } from 'app/entities/mountain-region';

@Component({
    selector: 'jhi-route-update',
    templateUrl: './route-update.component.html'
})
export class RouteUpdateComponent implements OnInit {
    isSaving: boolean;

    mountainregions: IMountainRegion[];

    editForm = this.fb.group({
        id: [],
        name: [],
        length: [],
        difficulty: [],
        mountainRegions: []
    });

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected routeService: RouteService,
        protected mountainRegionService: MountainRegionService,
        protected activatedRoute: ActivatedRoute,
        private fb: FormBuilder
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ route }) => {
            this.updateForm(route);
        });
        this.mountainRegionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMountainRegion[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMountainRegion[]>) => response.body)
            )
            .subscribe((res: IMountainRegion[]) => (this.mountainregions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    updateForm(route: IRoute) {
        this.editForm.patchValue({
            id: route.id,
            name: route.name,
            length: route.length,
            difficulty: route.difficulty,
            mountainRegions: route.mountainRegions
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        const route = this.createFromForm();
        if (route.id !== undefined) {
            this.subscribeToSaveResponse(this.routeService.update(route));
        } else {
            this.subscribeToSaveResponse(this.routeService.create(route));
        }
    }

    private createFromForm(): IRoute {
        return {
            ...new Route(),
            id: this.editForm.get(['id']).value,
            name: this.editForm.get(['name']).value,
            length: this.editForm.get(['length']).value,
            difficulty: this.editForm.get(['difficulty']).value,
            mountainRegions: this.editForm.get(['mountainRegions']).value
        };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoute>>) {
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

    trackMountainRegionById(index: number, item: IMountainRegion) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
