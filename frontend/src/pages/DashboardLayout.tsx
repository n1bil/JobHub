import { Outlet, redirect, useLoaderData, useNavigate } from "react-router-dom";
import Wrapper from "../assets/wrappers/Dashboard";
import { BigSidebar, Navbar, SmallSidebar } from "../components";
import { createContext, useContext, useState } from "react";
import { checkDefaultTheme } from "../App";
import customFetch from "../utils/customFetch";
import { toast } from "react-toastify";

export const loader = async () => {
    try {
        const { data } = await customFetch.get('/users/current-user');
        return data;
    } catch (error) {
        return redirect('/');
    }
} 

const DashboardContext = createContext({
    user: { id: "", name: "", lastName: "", role: "", email: "", location: "" },
    showSidebar: false,
    isDarkTheme: false,
    toggleDarkTheme: () => {},
    toggleSideBar: () => {},
    logoutUser: async () => {},
});

const DashboardLayout = () => {
    const loaderData = useLoaderData();
    const id = typeof loaderData === 'object' && loaderData !== null ? (loaderData as { id: string }).id : '';
    const name = typeof loaderData === 'object' && loaderData !== null ? (loaderData as { name: string }).name : '';
    const lastName = typeof loaderData === 'object' && loaderData !== null ? (loaderData as { lastName: string }).lastName : '';
    const role = typeof loaderData === 'object' && loaderData !== null ? (loaderData as { role: string }).role : '';
    const email = typeof loaderData === 'object' && loaderData !== null ? (loaderData as { email: string }).email : '';
    const location = typeof loaderData === 'object' && loaderData !== null ? (loaderData as { location: string }).location : '';
    const user = { id, name, lastName, role, email, location };
    const navigate = useNavigate();
    const [showSidebar, setShowSidebar] = useState(false);
    const [isDarkTheme, setIsDarkTheme] = useState(checkDefaultTheme());

    const toggleDarkTheme = () => {
        const newDarkTheme = !isDarkTheme;
        setIsDarkTheme(newDarkTheme);
        document.body.classList.toggle("dark-theme", newDarkTheme);
        localStorage.setItem("darkTheme", JSON.stringify(newDarkTheme));
    };

    const toggleSideBar = () => {
        setShowSidebar(!showSidebar);
    };

    const logoutUser = async () => {
        navigate('/');
        await customFetch.post('/auth/logout');
        toast.success("Logging out...")
    };

    return (
        <DashboardContext.Provider
            value={{
                user,
                showSidebar,
                isDarkTheme,
                toggleDarkTheme,
                toggleSideBar,
                logoutUser,
            }}
        >
            <Wrapper>
                <main className="dashboard">
                    <SmallSidebar />
                    <BigSidebar />
                    <div>
                        <Navbar />
                        <div className="dashboard-page">
                            <Outlet context={{ user }} />
                        </div>
                    </div>
                </main>
            </Wrapper>
        </DashboardContext.Provider>
    );
};

export const useDashboardContext = () => useContext(DashboardContext);

export default DashboardLayout;
