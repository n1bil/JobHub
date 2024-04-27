import {
    Outlet,
    redirect,
    useLoaderData,
    useNavigate,
    useNavigation,
} from "react-router-dom";
import Wrapper from "../assets/css/Dashboard";
import { BigSidebar, Navbar, SmallSidebar, Loading } from "../components";
import { createContext, useContext, useState } from "react";
import { checkDefaultTheme } from "../App";
import customFetch from "../utils/customFetch";
import { toast } from "react-toastify";
import { User } from "../utils/UserAbstract";

export const loader = async () => {
    try {
        const { data } = await customFetch.get("/users/current-user");
        return data;
    } catch (error) {
        return redirect("/");
    }
};

const DashboardContext = createContext({
    user: {
        id: "",
        name: "",
        lastName: "",
        role: "",
        email: "",
        location: "",
        avatar: "",
    },
    showSidebar: false,
    isDarkTheme: false,
    toggleDarkTheme: () => {},
    toggleSideBar: () => {},
    logoutUser: async () => {},
});

const DashboardLayout = () => {
    const loaderData = useLoaderData();
    const user = loaderData as User;
    const navigate = useNavigate();
    const navigation = useNavigation();
    const isPageLoading = navigation.state === "loading";
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
        navigate("/");
        await customFetch.post("/auth/logout");
        toast.success("Logging out...");
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
                            {isPageLoading ? (
                                <Loading />
                            ) : (
                                <Outlet context={{ user }} />
                            )}
                        </div>
                    </div>
                </main>
            </Wrapper>
        </DashboardContext.Provider>
    );
};

export const useDashboardContext = () => useContext(DashboardContext);

export default DashboardLayout;
