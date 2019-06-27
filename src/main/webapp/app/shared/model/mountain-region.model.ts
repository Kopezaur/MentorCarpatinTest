import { ICountry } from 'app/shared/model/country.model';
import { IRescueService } from 'app/shared/model/rescue-service.model';
import { IRoute } from 'app/shared/model/route.model';

export interface IMountainRegion {
    id?: number;
    regionName?: string;
    surface?: number;
    topPeakName?: string;
    topPeakHeight?: number;
    country?: ICountry;
    rescueService?: IRescueService;
    routes?: IRoute[];
}

export class MountainRegion implements IMountainRegion {
    constructor(
        public id?: number,
        public regionName?: string,
        public surface?: number,
        public topPeakName?: string,
        public topPeakHeight?: number,
        public country?: ICountry,
        public rescueService?: IRescueService,
        public routes?: IRoute[]
    ) {}
}
