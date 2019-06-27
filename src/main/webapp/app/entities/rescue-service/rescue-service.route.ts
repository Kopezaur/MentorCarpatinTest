import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RescueService } from 'app/shared/model/rescue-service.model';
import { RescueServiceService } from './rescue-service.service';
import { RescueServiceComponent } from './rescue-service.component';
import { RescueServiceDetailComponent } from './rescue-service-detail.component';
import { RescueServiceUpdateComponent } from './rescue-service-update.component';
import { RescueServiceDeletePopupComponent } from './rescue-service-delete-dialog.component';
import { IRescueService } from 'app/shared/model/rescue-service.model';

@Injectable({ providedIn: 'root' })
export class RescueServiceResolve implements Resolve<IRescueService> {
    constructor(private service: RescueServiceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRescueService> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RescueService>) => response.ok),
                map((rescueService: HttpResponse<RescueService>) => rescueService.body)
            );
        }
        return of(new RescueService());
    }
}

export const rescueServiceRoute: Routes = [
    {
        path: '',
        component: RescueServiceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mentorCarpatinApp.rescueService.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RescueServiceDetailComponent,
        resolve: {
            rescueService: RescueServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mentorCarpatinApp.rescueService.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RescueServiceUpdateComponent,
        resolve: {
            rescueService: RescueServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mentorCarpatinApp.rescueService.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RescueServiceUpdateComponent,
        resolve: {
            rescueService: RescueServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mentorCarpatinApp.rescueService.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rescueServicePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RescueServiceDeletePopupComponent,
        resolve: {
            rescueService: RescueServiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mentorCarpatinApp.rescueService.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
