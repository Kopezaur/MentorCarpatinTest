import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRoutePerformance } from 'app/shared/model/route-performance.model';

type EntityResponseType = HttpResponse<IRoutePerformance>;
type EntityArrayResponseType = HttpResponse<IRoutePerformance[]>;

@Injectable({ providedIn: 'root' })
export class RoutePerformanceService {
    public resourceUrl = SERVER_API_URL + 'api/route-performances';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/route-performances';

    constructor(protected http: HttpClient) {}

    create(routePerformance: IRoutePerformance): Observable<EntityResponseType> {
        return this.http.post<IRoutePerformance>(this.resourceUrl, routePerformance, { observe: 'response' });
    }

    update(routePerformance: IRoutePerformance): Observable<EntityResponseType> {
        return this.http.put<IRoutePerformance>(this.resourceUrl, routePerformance, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRoutePerformance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRoutePerformance[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRoutePerformance[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
