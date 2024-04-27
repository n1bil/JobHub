import { FaTimes } from "react-icons/fa";
import Wrapper from "../assets/css/SmallSidebar";
import { useDashboardContext } from "../pages/DashboardLayout";
import NavLinks from "./NavLinks";

const SmallSidebar = () => {
    const { showSidebar, toggleSideBar } = useDashboardContext();

    return (
        <Wrapper>
            <div
                className={
                    showSidebar
                        ? "sidebar-container show-sidebar"
                        : "sidebar-container"
                }
            >
                <div className="content">
                    <button
                        type="button"
                        className="close-btn"
                        onClick={toggleSideBar}
                    >
                        <FaTimes />
                    </button>
                    <header>
                        <div className="logo">
                            <h3>
                                JOB<span>HUB</span>
                            </h3>
                        </div>
                    </header>
                    <NavLinks />
                </div>
            </div>
        </Wrapper>
    );
};

export default SmallSidebar;
