import { ChartsContainer, StatsContainer } from "../components";
import { StatsResponse } from "../utils/StatsResponse";
import customFetch from "../utils/customFetch";
import { useLoaderData } from "react-router-dom";

export const loader = async () => {
    try {
        const response = await customFetch.get("/users/stats");
        return response.data;
    } catch (error) {
        return error;
    }
};

const Stats = () => {
    const { defaultStats, monthlyApplications } =
        useLoaderData() as StatsResponse;

    return (
        <>
            <StatsContainer defaultStats={defaultStats} />
            {monthlyApplications?.length > 1 && (
                <ChartsContainer data={monthlyApplications} />
            )}
        </>
    );
};

export default Stats;
