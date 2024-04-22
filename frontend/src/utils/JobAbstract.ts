export interface Job {
    id: string;
    company: string;
    position: string;
    jobStatus: string;
    jobType: string;
    jobLocation: string;
    createdBy: string;
    createdAt: string;
    updatedAt: string;
}

export interface Value {
    search: string;
    jobStatus: string;
    jobType: string;
    sort: string;
}

export interface AllJobsContextType {
    data: {
        jobs: Job[];
    };
    searchValues: Value;
}
