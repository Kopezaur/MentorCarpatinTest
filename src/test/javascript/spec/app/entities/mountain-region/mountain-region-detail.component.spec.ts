/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MentorCarpatinTestModule } from '../../../test.module';
import { MountainRegionDetailComponent } from 'app/entities/mountain-region/mountain-region-detail.component';
import { MountainRegion } from 'app/shared/model/mountain-region.model';

describe('Component Tests', () => {
    describe('MountainRegion Management Detail Component', () => {
        let comp: MountainRegionDetailComponent;
        let fixture: ComponentFixture<MountainRegionDetailComponent>;
        const route = ({ data: of({ mountainRegion: new MountainRegion(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MentorCarpatinTestModule],
                declarations: [MountainRegionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MountainRegionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MountainRegionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.mountainRegion).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
