import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IRescueService, RescueService } from 'app/shared/model/rescue-service.model';
import { RescueServiceService } from './rescue-service.service';

@Component({
    selector: 'jhi-rescue-service-update',
    templateUrl: './rescue-service-update.component.html'
})
export class RescueServiceUpdateComponent implements OnInit {
    isSaving: boolean;

    editForm = this.fb.group({
        id: [],
        description: [],
        noOfEmployees: []
    });

    constructor(protected rescueServiceService: RescueServiceService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rescueService }) => {
            this.updateForm(rescueService);
        });
    }

    updateForm(rescueService: IRescueService) {
        this.editForm.patchValue({
            id: rescueService.id,
            description: rescueService.description,
            noOfEmployees: rescueService.noOfEmployees
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        const rescueService = this.createFromForm();
        if (rescueService.id !== undefined) {
            this.subscribeToSaveResponse(this.rescueServiceService.update(rescueService));
        } else {
            this.subscribeToSaveResponse(this.rescueServiceService.create(rescueService));
        }
    }

    private createFromForm(): IRescueService {
        return {
            ...new RescueService(),
            id: this.editForm.get(['id']).value,
            description: this.editForm.get(['description']).value,
            noOfEmployees: this.editForm.get(['noOfEmployees']).value
        };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRescueService>>) {
        result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
