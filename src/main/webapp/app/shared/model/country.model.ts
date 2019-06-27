export interface ICountry {
    id?: number;
    countryName?: string;
    countryCode?: string;
}

export class Country implements ICountry {
    constructor(public id?: number, public countryName?: string, public countryCode?: string) {}
}
