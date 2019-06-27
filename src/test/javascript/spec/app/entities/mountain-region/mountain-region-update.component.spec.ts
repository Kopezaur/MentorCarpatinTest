/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { MentorCarpatinTestModule } from '../../../test.module';
import { MountainRegionUpdateComponent } from 'app/entities/mountain-region/mountain-region-update.component';
import { MountainRegionService } from 'app/entities/mountain-region/mountain-region.service';
import { MountainRegion } from 'app/shared/model/mountain-region.model';

describe('Component Tests', () => {
    describe('MountainRegion Management Update Component', () => {
        let comp: MountainRegionUpdateComponent;
        let fixture: ComponentFixture<MountainRegionUpdateComponent>;
        let service: MountainRegionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MentorCarpatinTestModule],
                declarations: [MountainRegionUpdateComponent],
                providers: [FormBuilder]
            })
                .overrideTemplate(MountainRegionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MountainRegionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MountainRegionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MountainRegion(123);
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
                const entity = new MountainRegion();
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
