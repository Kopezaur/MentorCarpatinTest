import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MentorCarpatinSharedModule } from 'app/shared';
import {
    MountainRegionComponent,
    MountainRegionDetailComponent,
    MountainRegionUpdateComponent,
    MountainRegionDeletePopupComponent,
    MountainRegionDeleteDialogComponent,
    mountainRegionRoute,
    mountainRegionPopupRoute
} from './';

const ENTITY_STATES = [...mountainRegionRoute, ...mountainRegionPopupRoute];

@NgModule({
    imports: [MentorCarpatinSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MountainRegionComponent,
        MountainRegionDetailComponent,
        MountainRegionUpdateComponent,
        MountainRegionDeleteDialogComponent,
        MountainRegionDeletePopupComponent
    ],
    entryComponents: [
        MountainRegionComponent,
        MountainRegionUpdateComponent,
        MountainRegionDeleteDialogComponent,
        MountainRegionDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MentorCarpatinMountainRegionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
