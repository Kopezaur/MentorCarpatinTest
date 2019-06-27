/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MentorCarpatinTestModule } from '../../../test.module';
import { RescueServiceDetailComponent } from 'app/entities/rescue-service/rescue-service-detail.component';
import { RescueService } from 'app/shared/model/rescue-service.model';

describe('Component Tests', () => {
    describe('RescueService Management Detail Component', () => {
        let comp: RescueServiceDetailComponent;
        let fixture: ComponentFixture<RescueServiceDetailComponent>;
        const route = ({ data: of({ rescueService: new RescueService(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MentorCarpatinTestModule],
                declarations: [RescueServiceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RescueServiceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RescueServiceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.rescueService).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
