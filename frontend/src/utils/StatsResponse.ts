export interface DefaultStats {
    declined: number;
    pending: number;
    interview: number;
}

export interface MonthlyApplication {
    count: number;
    date: string;
}

export interface StatsResponse {
    defaultStats: DefaultStats;
    monthlyApplications: MonthlyApplication[];
}
