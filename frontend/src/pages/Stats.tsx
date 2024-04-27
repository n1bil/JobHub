import { QueryClient, useQuery } from "@tanstack/react-query";
import { ChartsContainer, StatsContainer } from "../components";
import customFetch from "../utils/customFetch";

const statsQuery = {
    queryKey: ["stats"],
    queryFn: async () => {
        const response = await customFetch.get("/users/stats");
        return response.data;
    },
};

export const loader = (queryClient: QueryClient) => async () => {
    const data = await queryClient.ensureQueryData(statsQuery);
    return data;
};

const Stats = () => {
    const { data } = useQuery(statsQuery);
    const { defaultStats, monthlyApplications } = data;

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
