import { Link } from "react-router-dom";
import main from "../assets/images/main.svg";
import Wrapper from "../assets/css/LandingPage";

const Landing = () => {
    return (
        <Wrapper>
            <nav>
                <div className="logo">
                    <h3>
                        JOB<span>HUB</span>
                    </h3>
                </div>
            </nav>
            <div className="container page">
                <div className="info">
                    <h2>
                        job <span>tracking</span> app
                    </h2>
                    <p>
                        A simple job tracking software that allows you to filter
                        jobs by there status, role, etc allowing you to edit
                        your profile, view stats page and many more. Implemented
                        server side pagination for smoother user experience. A
                        full-stack application.
                    </p>
                    <Link to="/register" className="btn register-link">
                        Register
                    </Link>
                    <Link to="/login" className="btn ">
                        Login
                    </Link>
                </div>
                <img src={main} alt="job hunt" className="img main-img" />
            </div>
        </Wrapper>
    );
};

export default Landing;
