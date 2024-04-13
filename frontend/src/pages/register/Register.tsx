import { Link } from "react-router-dom";
import Wrapper from "../../assets/wrappers/RegisterAndLoginPage";
import styles from "./Register.module.css";
import { SmallSidebar, Logo } from "../../components";

const Register = () => {
    return (
        <Wrapper>
            <form className={`form ${styles.form}`}>
                <Logo classname={styles.logo} />
                <h4>Register</h4>
                <SmallSidebar type="text" name="name" labelText="name"/>
                <SmallSidebar type="text" name="lastName" labelText="Last Name"/>
                <SmallSidebar type="text" name="location" labelText="Location" />
                <SmallSidebar type="email" name="email" labelText="Email" />
                <SmallSidebar type="password" name="password" labelText="Password" />
                <button type="submit" className={`btn btn-block ${styles.btn}`}>
                    submit
                </button>
                <p>
                    Already a member ?
                    <Link to="/login" className="member-btn">
                        Login
                    </Link>
                </p>
            </form>
        </Wrapper>
    );
};

export default Register;
