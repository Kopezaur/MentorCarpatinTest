import { IMountainRegion } from 'app/shared/model/mountain-region.model';
import { IRescueService } from 'app/shared/model/rescue-service.model';

export interface IReport {
    id?: number;
    description?: string;
    noOfVictims?: number;
    severity?: string;
    day?: string;
    month?: string;
    year?: string;
    mountainRegion?: IMountainRegion;
    author?: IRescueService;
}

export class Report implements IReport {
    constructor(
        public id?: number,
        public description?: string,
        public noOfVictims?: number,
        public severity?: string,
        public day?: string,
        public month?: string,
        public year?: string,
        public mountainRegion?: IMountainRegion,
        public author?: IRescueService
    ) {}
}
