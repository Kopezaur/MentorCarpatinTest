import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MentorCarpatinSharedModule } from 'app/shared';
import {
    RoutePerformanceComponent,
    RoutePerformanceDetailComponent,
    RoutePerformanceUpdateComponent,
    RoutePerformanceDeletePopupComponent,
    RoutePerformanceDeleteDialogComponent,
    routePerformanceRoute,
    routePerformancePopupRoute
} from './';

const ENTITY_STATES = [...routePerformanceRoute, ...routePerformancePopupRoute];

@NgModule({
    imports: [MentorCarpatinSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RoutePerformanceComponent,
        RoutePerformanceDetailComponent,
        RoutePerformanceUpdateComponent,
        RoutePerformanceDeleteDialogComponent,
        RoutePerformanceDeletePopupComponent
    ],
    entryComponents: [
        RoutePerformanceComponent,
        RoutePerformanceUpdateComponent,
        RoutePerformanceDeleteDialogComponent,
        RoutePerformanceDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MentorCarpatinRoutePerformanceModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
