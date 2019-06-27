import { IRoutePerformance } from 'app/shared/model/route-performance.model';
import { IMountainRegion } from 'app/shared/model/mountain-region.model';

export interface IRoute {
    id?: number;
    name?: string;
    length?: number;
    difficulty?: string;
    routePerformances?: IRoutePerformance[];
    mountainRegions?: IMountainRegion[];
}

export class Route implements IRoute {
    constructor(
        public id?: number,
        public name?: string,
        public length?: number,
        public difficulty?: string,
        public routePerformances?: IRoutePerformance[],
        public mountainRegions?: IMountainRegion[]
    ) {}
}
