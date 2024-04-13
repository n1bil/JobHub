import { Outlet } from "react-router-dom";
import Wrapper from "../assets/wrappers/Dashboard";
import { BigSidebar, Navbar, SmallSidebar } from "../components";
import { createContext, useContext, useState } from "react";

const DashboardContext = createContext({
    user: { name: '' },
    showSidebar: false,
    isDarkTheme: false,
    toggleDarkTheme: () => {},
    toggleSideBar: () => {},
    logoutUser: async () => {},
});

const DashboardLayout = () => {
    // temp
    const user = { name: "John" };
    const [showSidebar, setShowSidebar] = useState(false);
    const [isDarkTheme, setIsDarkTheme] = useState(false);

    const toggleDarkTheme = () => {
        console.log("Toggle dark theme");
    };

    const toggleSideBar = () => {
        setShowSidebar(!showSidebar);
    };

    const logoutUser = async () => {
        console.log("logout user");
    };

    return (
        <DashboardContext.Provider value={{user, showSidebar, isDarkTheme, toggleDarkTheme, toggleSideBar, logoutUser}}>
            <Wrapper>
                <main className="dashboard">
                    <SmallSidebar />
                    <BigSidebar />
                    <div>
                        <Navbar />
                        <div className="dashboard-page">
                            <Outlet />
                        </div>
                    </div>
                </main>
            </Wrapper>
        </DashboardContext.Provider>
    );
};

export const useDashboardContext = () => useContext(DashboardContext);

export default DashboardLayout;
