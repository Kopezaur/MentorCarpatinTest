import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MountainRegion } from 'app/shared/model/mountain-region.model';
import { MountainRegionService } from './mountain-region.service';
import { MountainRegionComponent } from './mountain-region.component';
import { MountainRegionDetailComponent } from './mountain-region-detail.component';
import { MountainRegionUpdateComponent } from './mountain-region-update.component';
import { MountainRegionDeletePopupComponent } from './mountain-region-delete-dialog.component';
import { IMountainRegion } from 'app/shared/model/mountain-region.model';

@Injectable({ providedIn: 'root' })
export class MountainRegionResolve implements Resolve<IMountainRegion> {
    constructor(private service: MountainRegionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMountainRegion> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MountainRegion>) => response.ok),
                map((mountainRegion: HttpResponse<MountainRegion>) => mountainRegion.body)
            );
        }
        return of(new MountainRegion());
    }
}

export const mountainRegionRoute: Routes = [
    {
        path: '',
        component: MountainRegionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mentorCarpatinApp.mountainRegion.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MountainRegionDetailComponent,
        resolve: {
            mountainRegion: MountainRegionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mentorCarpatinApp.mountainRegion.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MountainRegionUpdateComponent,
        resolve: {
            mountainRegion: MountainRegionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mentorCarpatinApp.mountainRegion.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MountainRegionUpdateComponent,
        resolve: {
            mountainRegion: MountainRegionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mentorCarpatinApp.mountainRegion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mountainRegionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MountainRegionDeletePopupComponent,
        resolve: {
            mountainRegion: MountainRegionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mentorCarpatinApp.mountainRegion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
