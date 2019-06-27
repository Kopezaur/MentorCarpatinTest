/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MentorCarpatinTestModule } from '../../../test.module';
import { RoutePerformanceComponent } from 'app/entities/route-performance/route-performance.component';
import { RoutePerformanceService } from 'app/entities/route-performance/route-performance.service';
import { RoutePerformance } from 'app/shared/model/route-performance.model';

describe('Component Tests', () => {
    describe('RoutePerformance Management Component', () => {
        let comp: RoutePerformanceComponent;
        let fixture: ComponentFixture<RoutePerformanceComponent>;
        let service: RoutePerformanceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MentorCarpatinTestModule],
                declarations: [RoutePerformanceComponent],
                providers: []
            })
                .overrideTemplate(RoutePerformanceComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RoutePerformanceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoutePerformanceService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new RoutePerformance(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.routePerformances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
