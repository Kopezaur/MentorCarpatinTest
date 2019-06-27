import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMountainRegion, MountainRegion } from 'app/shared/model/mountain-region.model';
import { MountainRegionService } from './mountain-region.service';
import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from 'app/entities/country';
import { IRescueService } from 'app/shared/model/rescue-service.model';
import { RescueServiceService } from 'app/entities/rescue-service';
import { IRoute } from 'app/shared/model/route.model';
import { RouteService } from 'app/entities/route';

@Component({
    selector: 'jhi-mountain-region-update',
    templateUrl: './mountain-region-update.component.html'
})
export class MountainRegionUpdateComponent implements OnInit {
    isSaving: boolean;

    countries: ICountry[];

    rescueservices: IRescueService[];

    routes: IRoute[];

    editForm = this.fb.group({
        id: [],
        regionName: [],
        surface: [],
        topPeakName: [],
        topPeakHeight: [],
        country: [],
        rescueService: []
    });

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected mountainRegionService: MountainRegionService,
        protected countryService: CountryService,
        protected rescueServiceService: RescueServiceService,
        protected routeService: RouteService,
        protected activatedRoute: ActivatedRoute,
        private fb: FormBuilder
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ mountainRegion }) => {
            this.updateForm(mountainRegion);
        });
        this.countryService
            .query({ filter: 'mountainregion-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<ICountry[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICountry[]>) => response.body)
            )
            .subscribe(
                (res: ICountry[]) => {
                    if (!this.editForm.get('country').value || !this.editForm.get('country').value.id) {
                        this.countries = res;
                    } else {
                        this.countryService
                            .find(this.editForm.get('country').value.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ICountry>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ICountry>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ICountry) => (this.countries = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.rescueServiceService
            .query({ filter: 'mountainregion-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IRescueService[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRescueService[]>) => response.body)
            )
            .subscribe(
                (res: IRescueService[]) => {
                    if (!this.editForm.get('rescueService').value || !this.editForm.get('rescueService').value.id) {
                        this.rescueservices = res;
                    } else {
                        this.rescueServiceService
                            .find(this.editForm.get('rescueService').value.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IRescueService>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IRescueService>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IRescueService) => (this.rescueservices = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.routeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRoute[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRoute[]>) => response.body)
            )
            .subscribe((res: IRoute[]) => (this.routes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    updateForm(mountainRegion: IMountainRegion) {
        this.editForm.patchValue({
            id: mountainRegion.id,
            regionName: mountainRegion.regionName,
            surface: mountainRegion.surface,
            topPeakName: mountainRegion.topPeakName,
            topPeakHeight: mountainRegion.topPeakHeight,
            country: mountainRegion.country,
            rescueService: mountainRegion.rescueService
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        const mountainRegion = this.createFromForm();
        if (mountainRegion.id !== undefined) {
            this.subscribeToSaveResponse(this.mountainRegionService.update(mountainRegion));
        } else {
            this.subscribeToSaveResponse(this.mountainRegionService.create(mountainRegion));
        }
    }

    private createFromForm(): IMountainRegion {
        return {
            ...new MountainRegion(),
            id: this.editForm.get(['id']).value,
            regionName: this.editForm.get(['regionName']).value,
            surface: this.editForm.get(['surface']).value,
            topPeakName: this.editForm.get(['topPeakName']).value,
            topPeakHeight: this.editForm.get(['topPeakHeight']).value,
            country: this.editForm.get(['country']).value,
            rescueService: this.editForm.get(['rescueService']).value
        };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMountainRegion>>) {
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

    trackCountryById(index: number, item: ICountry) {
        return item.id;
    }

    trackRescueServiceById(index: number, item: IRescueService) {
        return item.id;
    }

    trackRouteById(index: number, item: IRoute) {
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
