/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { MentorCarpatinTestModule } from '../../../test.module';
import { RescueServiceUpdateComponent } from 'app/entities/rescue-service/rescue-service-update.component';
import { RescueServiceService } from 'app/entities/rescue-service/rescue-service.service';
import { RescueService } from 'app/shared/model/rescue-service.model';

describe('Component Tests', () => {
    describe('RescueService Management Update Component', () => {
        let comp: RescueServiceUpdateComponent;
        let fixture: ComponentFixture<RescueServiceUpdateComponent>;
        let service: RescueServiceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MentorCarpatinTestModule],
                declarations: [RescueServiceUpdateComponent],
                providers: [FormBuilder]
            })
                .overrideTemplate(RescueServiceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RescueServiceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RescueServiceService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new RescueService(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.updateForm(entity);
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new RescueService();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.updateForm(entity);
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
