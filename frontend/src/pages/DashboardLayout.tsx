import { Outlet, redirect, useNavigate, useNavigation } from "react-router-dom";
import Wrapper from "../assets/css/Dashboard";
import { BigSidebar, Navbar, SmallSidebar, Loading } from "../components";
import { createContext, useContext, useEffect, useState } from "react";
import { checkDefaultTheme } from "../App";
import customFetch from "../utils/customFetch";
import { toast } from "react-toastify";
import { User } from "../utils/UserAbstract";
import { QueryClient, useQuery } from "@tanstack/react-query";

const userQuery = {
    queryKey: ["user"],
    queryFn: async () => {
        const { data } = await customFetch.get("/users/current-user");
        return data;
    },
};

export const loader = (queryClient: QueryClient) => async () => {
    try {
        return await queryClient.ensureQueryData(userQuery);
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

const DashboardLayout: React.FC<{ queryClient: QueryClient }> = ({
    queryClient,
}) => {
    const loaderData = useQuery(userQuery).data;
    const user = loaderData as User;
    const navigate = useNavigate();
    const navigation = useNavigation();
    const isPageLoading = navigation.state === "loading";
    const [showSidebar, setShowSidebar] = useState(false);
    const [isDarkTheme, setIsDarkTheme] = useState(checkDefaultTheme());
    const [isAuthError, setIsAuthError] = useState(false);

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
        queryClient.invalidateQueries();
        toast.success("Logging out...");
    };

    customFetch.interceptors.response.use(
        (response) => {
            return response;
        }, (error) => {
            if (error?.response?.status === 401) {
                setIsAuthError(true);
            }
            return Promise.reject(error);
        }
    );

    useEffect(() => {
        if (!isAuthError) return;
        logoutUser();
    }, [isAuthError]);

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
