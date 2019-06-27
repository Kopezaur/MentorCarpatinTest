/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { MentorCarpatinTestModule } from '../../../test.module';
import { RoutePerformanceUpdateComponent } from 'app/entities/route-performance/route-performance-update.component';
import { RoutePerformanceService } from 'app/entities/route-performance/route-performance.service';
import { RoutePerformance } from 'app/shared/model/route-performance.model';

describe('Component Tests', () => {
    describe('RoutePerformance Management Update Component', () => {
        let comp: RoutePerformanceUpdateComponent;
        let fixture: ComponentFixture<RoutePerformanceUpdateComponent>;
        let service: RoutePerformanceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MentorCarpatinTestModule],
                declarations: [RoutePerformanceUpdateComponent],
                providers: [FormBuilder]
            })
                .overrideTemplate(RoutePerformanceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RoutePerformanceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoutePerformanceService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new RoutePerformance(123);
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
                const entity = new RoutePerformance();
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
