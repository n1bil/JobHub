import { JobsContainer, SearchContainer } from "../components";
import customFetch from "../utils/customFetch";
import { useLoaderData } from "react-router-dom";
import { useContext, createContext } from "react";
import { CustomAxiosError, handleError } from "../utils/CustomError";

interface Job {
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

interface AllJobsContextType {
    jobs: Job[];
}

export const loader = async () => {
    try {
        const { data } = await customFetch.get("/jobs");
        return { data };
    } catch (error) {
        handleError(error as CustomAxiosError);
        return error;
    }
};

const AllJobsContext = createContext<AllJobsContextType>({
    jobs: []
});

const AllJobs = () => {
    const { data } = useLoaderData() as { data: Job[] };
    
    return (
        <AllJobsContext.Provider value={{ jobs: data }}>
            <SearchContainer />
            <JobsContainer />
        </AllJobsContext.Provider>
    );
};

export const useAllJobsContext = () => useContext(AllJobsContext);

export default AllJobs;
