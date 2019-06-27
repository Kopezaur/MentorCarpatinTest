import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IReport, Report } from 'app/shared/model/report.model';
import { ReportService } from './report.service';
import { IMountainRegion } from 'app/shared/model/mountain-region.model';
import { MountainRegionService } from 'app/entities/mountain-region';
import { IRescueService } from 'app/shared/model/rescue-service.model';
import { RescueServiceService } from 'app/entities/rescue-service';

@Component({
    selector: 'jhi-report-update',
    templateUrl: './report-update.component.html'
})
export class ReportUpdateComponent implements OnInit {
    isSaving: boolean;

    mountainregions: IMountainRegion[];

    rescueservices: IRescueService[];

    editForm = this.fb.group({
        id: [],
        description: [],
        noOfVictims: [],
        severity: [],
        day: [],
        month: [],
        year: [],
        mountainRegion: [],
        author: []
    });

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected reportService: ReportService,
        protected mountainRegionService: MountainRegionService,
        protected rescueServiceService: RescueServiceService,
        protected activatedRoute: ActivatedRoute,
        private fb: FormBuilder
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ report }) => {
            this.updateForm(report);
        });
        this.mountainRegionService
            .query({ filter: 'report-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IMountainRegion[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMountainRegion[]>) => response.body)
            )
            .subscribe(
                (res: IMountainRegion[]) => {
                    if (!this.editForm.get('mountainRegion').value || !this.editForm.get('mountainRegion').value.id) {
                        this.mountainregions = res;
                    } else {
                        this.mountainRegionService
                            .find(this.editForm.get('mountainRegion').value.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IMountainRegion>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IMountainRegion>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IMountainRegion) => (this.mountainregions = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.rescueServiceService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRescueService[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRescueService[]>) => response.body)
            )
            .subscribe((res: IRescueService[]) => (this.rescueservices = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    updateForm(report: IReport) {
        this.editForm.patchValue({
            id: report.id,
            description: report.description,
            noOfVictims: report.noOfVictims,
            severity: report.severity,
            day: report.day,
            month: report.month,
            year: report.year,
            mountainRegion: report.mountainRegion,
            author: report.author
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        const report = this.createFromForm();
        if (report.id !== undefined) {
            this.subscribeToSaveResponse(this.reportService.update(report));
        } else {
            this.subscribeToSaveResponse(this.reportService.create(report));
        }
    }

    private createFromForm(): IReport {
        return {
            ...new Report(),
            id: this.editForm.get(['id']).value,
            description: this.editForm.get(['description']).value,
            noOfVictims: this.editForm.get(['noOfVictims']).value,
            severity: this.editForm.get(['severity']).value,
            day: this.editForm.get(['day']).value,
            month: this.editForm.get(['month']).value,
            year: this.editForm.get(['year']).value,
            mountainRegion: this.editForm.get(['mountainRegion']).value,
            author: this.editForm.get(['author']).value
        };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IReport>>) {
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

    trackRescueServiceById(index: number, item: IRescueService) {
        return item.id;
    }
}
