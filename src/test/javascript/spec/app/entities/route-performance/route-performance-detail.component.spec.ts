/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MentorCarpatinTestModule } from '../../../test.module';
import { RoutePerformanceDetailComponent } from 'app/entities/route-performance/route-performance-detail.component';
import { RoutePerformance } from 'app/shared/model/route-performance.model';

describe('Component Tests', () => {
    describe('RoutePerformance Management Detail Component', () => {
        let comp: RoutePerformanceDetailComponent;
        let fixture: ComponentFixture<RoutePerformanceDetailComponent>;
        const route = ({ data: of({ routePerformance: new RoutePerformance(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MentorCarpatinTestModule],
                declarations: [RoutePerformanceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RoutePerformanceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RoutePerformanceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.routePerformance).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
