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

export interface AllJobsContextType {
    jobs: Job[];
}