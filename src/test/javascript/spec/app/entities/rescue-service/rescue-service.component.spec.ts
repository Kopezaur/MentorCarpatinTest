/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MentorCarpatinTestModule } from '../../../test.module';
import { RescueServiceComponent } from 'app/entities/rescue-service/rescue-service.component';
import { RescueServiceService } from 'app/entities/rescue-service/rescue-service.service';
import { RescueService } from 'app/shared/model/rescue-service.model';

describe('Component Tests', () => {
    describe('RescueService Management Component', () => {
        let comp: RescueServiceComponent;
        let fixture: ComponentFixture<RescueServiceComponent>;
        let service: RescueServiceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MentorCarpatinTestModule],
                declarations: [RescueServiceComponent],
                providers: []
            })
                .overrideTemplate(RescueServiceComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RescueServiceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RescueServiceService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new RescueService(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.rescueServices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
