import Wrapper from "../assets/wrappers/Navbar";
import { FaAlignLeft } from "react-icons/fa";
import Logo from "./Logo";
import { useDashboardContext } from "../pages/DashboardLayout";
import Logout from "./Logout";

const Navbar = () => {
    const { toggleSideBar } = useDashboardContext();

    return (
        <Wrapper>
            <div className="nav-center">
                <button type="button" className="toggle-btn" onClick={toggleSideBar}>
                    <FaAlignLeft />
                </button>
                <div>
                    <Logo />
                    <h4 className="logo-text">dashboard</h4>
                </div>
                <div className="btn-container">
                    <Logout />
                </div>
            </div>
        </Wrapper>
    );
};

export default Navbar;
