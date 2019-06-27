import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICountry, Country } from 'app/shared/model/country.model';
import { CountryService } from './country.service';

@Component({
    selector: 'jhi-country-update',
    templateUrl: './country-update.component.html'
})
export class CountryUpdateComponent implements OnInit {
    isSaving: boolean;

    editForm = this.fb.group({
        id: [],
        countryName: [],
        countryCode: []
    });

    constructor(protected countryService: CountryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ country }) => {
            this.updateForm(country);
        });
    }

    updateForm(country: ICountry) {
        this.editForm.patchValue({
            id: country.id,
            countryName: country.countryName,
            countryCode: country.countryCode
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        const country = this.createFromForm();
        if (country.id !== undefined) {
            this.subscribeToSaveResponse(this.countryService.update(country));
        } else {
            this.subscribeToSaveResponse(this.countryService.create(country));
        }
    }

    private createFromForm(): ICountry {
        return {
            ...new Country(),
            id: this.editForm.get(['id']).value,
            countryName: this.editForm.get(['countryName']).value,
            countryCode: this.editForm.get(['countryCode']).value
        };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICountry>>) {
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
