import Wrapper from "../assets/css/Navbar";
import { FaAlignLeft } from "react-icons/fa";
import { useDashboardContext } from "../pages/DashboardLayout";
import Logout from "./Logout";
import ThemeToggle from "./ThemeToggle";

const Navbar = () => {
    const { toggleSideBar } = useDashboardContext();

    return (
        <Wrapper>
            <div className="nav-center">
                <button
                    type="button"
                    className="toggle-btn"
                    onClick={toggleSideBar}
                >
                    <FaAlignLeft />
                </button>
                <div>
                    <div className="logo">
                        <h3>
                            JOB<span>HUB</span>
                        </h3>
                    </div>
                    <h4 className="logo-text">dashboard</h4>
                </div>
                <div className="btn-container">
                    <ThemeToggle />
                    <Logout />
                </div>
            </div>
        </Wrapper>
    );
};

export default Navbar;
