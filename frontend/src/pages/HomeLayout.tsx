import { Outlet } from "react-router-dom";

const HomeLayout = () => {
    return (
        <div>
            <nav>HomeLayout</nav>
            <Outlet />
        </div>
    );
};

export default HomeLayout;
