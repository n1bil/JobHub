import { Link } from "react-router-dom";
import main from "../../assets/images/main.svg";
import LogoCopy from "../../components/LogoCopy";
import styles from './Landing.module.css';

const Landing = () => {
    return (
        <div>
            <nav className={styles.nav}>
                <LogoCopy />
            </nav>
            <div className={`container ${styles.page}`}>
                <div className="info">
                    <h1 className={styles.h1}>
                        job <span>tracking</span> app
                    </h1>
                    <p className={styles.p}>
                        I'm baby wayfarers hoodie next level taiyaki brooklyn
                        cliche blue bottle single-origin coffee chia. Aesthetic
                        post-ironic venmo, quinoa lo-fi tote bag adaptogen
                        everyday carry meggings +1 brunch narwhal.
                    </p>
                    <Link to="/register" className={`btn ${styles.btn} ${styles.registerLink}`}>
                        Register
                    </Link>
                    <Link to="/login" className={`btn ${styles.btn}`}>
                        Login
                    </Link>
                </div>
                <img src={main} alt="job hunt" className="img main-img" />
            </div>
        </div>
    );
};

export default Landing;
