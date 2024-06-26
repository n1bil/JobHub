import { FaSuitcaseRolling, FaCalendarCheck } from "react-icons/fa";
import { useLoaderData, redirect } from "react-router-dom";
import customFetch from "../utils/customFetch";
import Wrapper from "../assets/css/StatsContainer";
import { toast } from "react-toastify";
import { StatItem } from "../components";

interface StatData {
    users: number;
    jobs: number;
}

export const loader = async () => {
    try {
        const { data } = await customFetch.get(`/admin/app-stats`);

        return data;
    } catch (error) {
        toast.error("You are not authorized to view this page");
        return redirect("/dashboard");
    }
};

const Admin = () => {
    const { users, jobs } = useLoaderData() as StatData;

    return (
        <Wrapper>
            <StatItem
                title="current users"
                count={users}
                color="#e9b949"
                bcg="#fcefc7"
                icon={<FaSuitcaseRolling />}
            />
            <StatItem
                title="total jobs"
                count={jobs}
                color="#647acb"
                bcg="#e0e8f9"
                icon={<FaCalendarCheck />}
            />
        </Wrapper>
    );
};

export default Admin;
