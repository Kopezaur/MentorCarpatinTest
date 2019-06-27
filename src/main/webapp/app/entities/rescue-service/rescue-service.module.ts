import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MentorCarpatinSharedModule } from 'app/shared';
import {
    RescueServiceComponent,
    RescueServiceDetailComponent,
    RescueServiceUpdateComponent,
    RescueServiceDeletePopupComponent,
    RescueServiceDeleteDialogComponent,
    rescueServiceRoute,
    rescueServicePopupRoute
} from './';

const ENTITY_STATES = [...rescueServiceRoute, ...rescueServicePopupRoute];

@NgModule({
    imports: [MentorCarpatinSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RescueServiceComponent,
        RescueServiceDetailComponent,
        RescueServiceUpdateComponent,
        RescueServiceDeleteDialogComponent,
        RescueServiceDeletePopupComponent
    ],
    entryComponents: [
        RescueServiceComponent,
        RescueServiceUpdateComponent,
        RescueServiceDeleteDialogComponent,
        RescueServiceDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MentorCarpatinRescueServiceModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
