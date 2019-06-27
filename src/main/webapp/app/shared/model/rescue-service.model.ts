import { IReport } from 'app/shared/model/report.model';

export interface IRescueService {
    id?: number;
    description?: string;
    noOfEmployees?: number;
    reports?: IReport[];
}

export class RescueService implements IRescueService {
    constructor(public id?: number, public description?: string, public noOfEmployees?: number, public reports?: IReport[]) {}
}
