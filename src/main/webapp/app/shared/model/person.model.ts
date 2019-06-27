import { Moment } from 'moment';
import { IRoutePerformance } from 'app/shared/model/route-performance.model';

export interface IPerson {
    id?: number;
    firstName?: string;
    lastName?: string;
    email?: string;
    phoneNumber?: string;
    birthDate?: Moment;
    weight?: number;
    height?: number;
    runningCoefficient?: number;
    performanceCoefficient?: number;
    routePerformances?: IRoutePerformance[];
}

export class Person implements IPerson {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public phoneNumber?: string,
        public birthDate?: Moment,
        public weight?: number,
        public height?: number,
        public runningCoefficient?: number,
        public performanceCoefficient?: number,
        public routePerformances?: IRoutePerformance[]
    ) {}
}
