import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RoutePerformance } from 'app/shared/model/route-performance.model';
import { RoutePerformanceService } from './route-performance.service';
import { RoutePerformanceComponent } from './route-performance.component';
import { RoutePerformanceDetailComponent } from './route-performance-detail.component';
import { RoutePerformanceUpdateComponent } from './route-performance-update.component';
import { RoutePerformanceDeletePopupComponent } from './route-performance-delete-dialog.component';
import { IRoutePerformance } from 'app/shared/model/route-performance.model';

@Injectable({ providedIn: 'root' })
export class RoutePerformanceResolve implements Resolve<IRoutePerformance> {
    constructor(private service: RoutePerformanceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRoutePerformance> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RoutePerformance>) => response.ok),
                map((routePerformance: HttpResponse<RoutePerformance>) => routePerformance.body)
            );
        }
        return of(new RoutePerformance());
    }
}

export const routePerformanceRoute: Routes = [
    {
        path: '',
        component: RoutePerformanceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mentorCarpatinApp.routePerformance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RoutePerformanceDetailComponent,
        resolve: {
            routePerformance: RoutePerformanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mentorCarpatinApp.routePerformance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RoutePerformanceUpdateComponent,
        resolve: {
            routePerformance: RoutePerformanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mentorCarpatinApp.routePerformance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RoutePerformanceUpdateComponent,
        resolve: {
            routePerformance: RoutePerformanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mentorCarpatinApp.routePerformance.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const routePerformancePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RoutePerformanceDeletePopupComponent,
        resolve: {
            routePerformance: RoutePerformanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mentorCarpatinApp.routePerformance.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
