import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IPerson, Person } from 'app/shared/model/person.model';
import { PersonService } from './person.service';

@Component({
    selector: 'jhi-person-update',
    templateUrl: './person-update.component.html'
})
export class PersonUpdateComponent implements OnInit {
    isSaving: boolean;

    editForm = this.fb.group({
        id: [],
        firstName: [],
        lastName: [],
        email: [],
        phoneNumber: [],
        birthDate: [],
        weight: [],
        height: [],
        runningCoefficient: [],
        performanceCoefficient: []
    });

    constructor(protected personService: PersonService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ person }) => {
            this.updateForm(person);
        });
    }

    updateForm(person: IPerson) {
        this.editForm.patchValue({
            id: person.id,
            firstName: person.firstName,
            lastName: person.lastName,
            email: person.email,
            phoneNumber: person.phoneNumber,
            birthDate: person.birthDate != null ? person.birthDate.format(DATE_TIME_FORMAT) : null,
            weight: person.weight,
            height: person.height,
            runningCoefficient: person.runningCoefficient,
            performanceCoefficient: person.performanceCoefficient
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        const person = this.createFromForm();
        if (person.id !== undefined) {
            this.subscribeToSaveResponse(this.personService.update(person));
        } else {
            this.subscribeToSaveResponse(this.personService.create(person));
        }
    }

    private createFromForm(): IPerson {
        return {
            ...new Person(),
            id: this.editForm.get(['id']).value,
            firstName: this.editForm.get(['firstName']).value,
            lastName: this.editForm.get(['lastName']).value,
            email: this.editForm.get(['email']).value,
            phoneNumber: this.editForm.get(['phoneNumber']).value,
            birthDate:
                this.editForm.get(['birthDate']).value != null
                    ? moment(this.editForm.get(['birthDate']).value, DATE_TIME_FORMAT)
                    : undefined,
            weight: this.editForm.get(['weight']).value,
            height: this.editForm.get(['height']).value,
            runningCoefficient: this.editForm.get(['runningCoefficient']).value,
            performanceCoefficient: this.editForm.get(['performanceCoefficient']).value
        };
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerson>>) {
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
