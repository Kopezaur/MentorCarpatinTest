/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MentorCarpatinTestModule } from '../../../test.module';
import { MountainRegionComponent } from 'app/entities/mountain-region/mountain-region.component';
import { MountainRegionService } from 'app/entities/mountain-region/mountain-region.service';
import { MountainRegion } from 'app/shared/model/mountain-region.model';

describe('Component Tests', () => {
    describe('MountainRegion Management Component', () => {
        let comp: MountainRegionComponent;
        let fixture: ComponentFixture<MountainRegionComponent>;
        let service: MountainRegionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MentorCarpatinTestModule],
                declarations: [MountainRegionComponent],
                providers: []
            })
                .overrideTemplate(MountainRegionComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MountainRegionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MountainRegionService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new MountainRegion(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.mountainRegions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
