import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMountainRegion } from 'app/shared/model/mountain-region.model';

type EntityResponseType = HttpResponse<IMountainRegion>;
type EntityArrayResponseType = HttpResponse<IMountainRegion[]>;

@Injectable({ providedIn: 'root' })
export class MountainRegionService {
    public resourceUrl = SERVER_API_URL + 'api/mountain-regions';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/mountain-regions';

    constructor(protected http: HttpClient) {}

    create(mountainRegion: IMountainRegion): Observable<EntityResponseType> {
        return this.http.post<IMountainRegion>(this.resourceUrl, mountainRegion, { observe: 'response' });
    }

    update(mountainRegion: IMountainRegion): Observable<EntityResponseType> {
        return this.http.put<IMountainRegion>(this.resourceUrl, mountainRegion, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMountainRegion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMountainRegion[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMountainRegion[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
