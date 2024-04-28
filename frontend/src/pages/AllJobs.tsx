import { JobsContainer, SearchContainer } from "../components";
import customFetch from "../utils/customFetch";
import { useLoaderData } from "react-router-dom";
import { useContext, createContext } from "react";
import { AllJobsContextType, Job } from "../utils/JobAbstract";
import { QueryClient, useQuery } from "@tanstack/react-query";

type Props = {
    search: string, 
    jobStatus: string, 
    jobType: string, 
    sort: string, 
    page: number
}

const allJobsQuery = (params: Props) => {
    const { search, jobStatus, jobType, sort, page } = params

    return {
        queryKey: ['jobs', search ?? '', jobStatus?? 'all', jobType?? 'all', sort?? 'newest', page?? 1],
        queryFn: async () => {
            const { data } = await customFetch.get("/jobs", { params });
            return data;
        }
    }
}

export const loader = (queryClient: QueryClient) => async ({ request }: { request: Request }) => {
    const params = Object.fromEntries([
        ...new URL(request.url).searchParams.entries(),
    ]);

    const convertedParams: Props = {
        search: params.search ?? "",
        jobStatus: params.jobStatus ?? "",
        jobType: params.jobType ?? "",
        sort: params.sort ?? "",
        page: parseInt(params.page ?? "1", 10),
    };

    await queryClient.ensureQueryData(allJobsQuery(convertedParams))
    return { searchValues: { ...convertedParams } };
};


const AllJobsContext = createContext<AllJobsContextType>({
    data: { jobs: [], totalJobs: 0, numOfPages: 0, currentPage: 0 },
    searchValues: { search: "", jobStatus: "", jobType: "", sort: "" },
});

const AllJobs = () => {
    const { searchValues } = useLoaderData() as {
        data: {
            jobs: Job[];
            totalJobs: number;
            numOfPages: number;
            currentPage: number;
        };
        searchValues: Props;
    };

    const { data } = useQuery(allJobsQuery(searchValues));

    return (
        <AllJobsContext.Provider value={{ data, searchValues }}>
            <SearchContainer />
            <JobsContainer />
        </AllJobsContext.Provider>
    );
};

export const useAllJobsContext = () => useContext(AllJobsContext);

export default AllJobs;
