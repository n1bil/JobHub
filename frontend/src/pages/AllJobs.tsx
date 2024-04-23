import { JobsContainer, SearchContainer } from "../components";
import customFetch from "../utils/customFetch";
import { ActionFunction, useLoaderData } from "react-router-dom";
import { useContext, createContext } from "react";
import { CustomAxiosError, handleError } from "../utils/CustomError";
import { AllJobsContextType, Job, Value } from "../utils/JobAbstract";

export const loader: ActionFunction = async ({ request }) => {
    const params = Object.fromEntries([
        ...new URL(request.url).searchParams.entries(),
    ]);

    try {
        const { data } = await customFetch.get("/jobs", { params });
        return { data, searchValues: { ...params } };
    } catch (error) {
        handleError(error as CustomAxiosError);
        return error;
    }
};

const AllJobsContext = createContext<AllJobsContextType>({
    data: { jobs: [], totalJobs: 0, numOfPages: 0, currentPage: 0 },
    searchValues: { search: "", jobStatus: "", jobType: "", sort: "" },
});

const AllJobs = () => {
    const { data, searchValues } = useLoaderData() as {
        data: {
            jobs: Job[];
            totalJobs: number;
            numOfPages: number;
            currentPage: number;
        };
        searchValues: Value;
    };

    return (
        <AllJobsContext.Provider value={{ data, searchValues }}>
            <SearchContainer />
            <JobsContainer />
        </AllJobsContext.Provider>
    );
};

export const useAllJobsContext = () => useContext(AllJobsContext);

export default AllJobs;
