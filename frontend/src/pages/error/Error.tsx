import { Link, useRouteError } from "react-router-dom";
import img from "../../assets/images/not-found.svg";
import styles from "./Error.module.css";

interface RouteError {
    status: number;
}

const Error = () => {
    const error = useRouteError() as RouteError;

    if (error.status === 404) {
        return (
            <div className={styles.wrapper}>
                <div>
                    <img src={img} alt="not found" />
                    <h3>Ohh! Page not found</h3>
                    <p>We can not seem to find the page you are looking for</p>
                    <Link to="/dashboard">back home</Link>
                </div>
            </div>
        );
    }

    return (
        <div className={styles.wrapper}>
            <h3>Error page</h3>
        </div>
    );
};

export default Error;
