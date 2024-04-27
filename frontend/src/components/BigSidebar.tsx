import Wrapper from "../assets/css/BigSidebar";
import NavLinks from "./NavLinks";
import { useDashboardContext } from "../pages/DashboardLayout";

const BigSidebar = () => {
    const { showSidebar } = useDashboardContext();

    return (
        <Wrapper>
            <div
                className={
                    showSidebar
                        ? "sidebar-container "
                        : "sidebar-container show-sidebar"
                }
            >
                <div className="content">
                    <header>
                        <div className="logo">
                            <h3>
                                JOB<span>HUB</span>
                            </h3>
                        </div>
                    </header>
                    <NavLinks isBigSidebar />
                </div>
            </div>
        </Wrapper>
    );
};

export default BigSidebar;
