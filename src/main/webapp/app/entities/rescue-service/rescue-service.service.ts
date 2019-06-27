import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRescueService } from 'app/shared/model/rescue-service.model';

type EntityResponseType = HttpResponse<IRescueService>;
type EntityArrayResponseType = HttpResponse<IRescueService[]>;

@Injectable({ providedIn: 'root' })
export class RescueServiceService {
    public resourceUrl = SERVER_API_URL + 'api/rescue-services';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/rescue-services';

    constructor(protected http: HttpClient) {}

    create(rescueService: IRescueService): Observable<EntityResponseType> {
        return this.http.post<IRescueService>(this.resourceUrl, rescueService, { observe: 'response' });
    }

    update(rescueService: IRescueService): Observable<EntityResponseType> {
        return this.http.put<IRescueService>(this.resourceUrl, rescueService, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRescueService>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRescueService[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRescueService[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
