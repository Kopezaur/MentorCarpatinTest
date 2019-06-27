/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MentorCarpatinTestModule } from '../../../test.module';
import { MountainRegionDeleteDialogComponent } from 'app/entities/mountain-region/mountain-region-delete-dialog.component';
import { MountainRegionService } from 'app/entities/mountain-region/mountain-region.service';

describe('Component Tests', () => {
    describe('MountainRegion Management Delete Component', () => {
        let comp: MountainRegionDeleteDialogComponent;
        let fixture: ComponentFixture<MountainRegionDeleteDialogComponent>;
        let service: MountainRegionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MentorCarpatinTestModule],
                declarations: [MountainRegionDeleteDialogComponent]
            })
                .overrideTemplate(MountainRegionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MountainRegionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MountainRegionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
