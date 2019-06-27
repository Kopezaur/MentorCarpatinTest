import { IRoute } from 'app/shared/model/route.model';
import { IPerson } from 'app/shared/model/person.model';

export interface IRoutePerformance {
    id?: number;
    time?: number;
    pace?: number;
    speed?: number;
    performanceCoefficient?: number;
    route?: IRoute;
    person?: IPerson;
}

export class RoutePerformance implements IRoutePerformance {
    constructor(
        public id?: number,
        public time?: number,
        public pace?: number,
        public speed?: number,
        public performanceCoefficient?: number,
        public route?: IRoute,
        public person?: IPerson
    ) {}
}
