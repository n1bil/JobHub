import { Link } from "react-router-dom";
import styles from "./Login.module.css";
import Wrapper from "../../assets/wrappers/RegisterAndLoginPage";

import { FormRow, Logo } from "../../components";

const Login = () => {
    return (
        <Wrapper>
            <form className={`form ${styles.form}`}>
                <Logo />
                <h4>login</h4>
                <FormRow type="email" name="email" />
                <FormRow type="password" name="password" />
                <button type="submit" className={`btn btn-block ${styles.btn}`}>Submit</button>
                <p>
                    Not a member yet ?
                    <Link to="/register" className={styles.memberBtn}>
                        Register
                    </Link>
                </p>
            </form>
        </Wrapper>
    );
};

export default Login;
