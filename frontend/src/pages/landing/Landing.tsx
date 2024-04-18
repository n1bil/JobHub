import { Link } from "react-router-dom";
import main from "../../assets/images/main.svg";
import styles from "./Landing.module.css";
import Wrapper from "../../assets/wrappers/LandingPage";
import { Logo } from "../../components";

const Landing = () => {
    return (
        <Wrapper>
            <nav className={styles.nav}>
                <Logo />
            </nav>
            <div className={`container ${styles.page}`}>
                <div className={styles.info}>
                    <h1 className={styles.h1}>
                        job
                        <span className={styles.span}> tracking </span>App
                    </h1>
                    <p className={styles.p}>
                        Lorem ipsum dolor sit amet consectetur adipisicing elit.
                        Soluta vitae quas voluptas pariatur blanditiis laborum
                        eveniet ? Eveniet dolor quasi est necessitatibus
                        quibusdam, molestias veniam. Odit earum aspernatur ipsum
                        cumque iusto!
                    </p>
                    <Link
                        to="/register"
                        className={`btn ${styles.registerLink}`}
                        style={{ padding: "0.75rem 1rem" }}
                    >
                        Register
                    </Link>
                    <Link to="/login" className="btn">
                        Login / Demo User
                    </Link>
                </div>
                <img
                    src={main}
                    alt="job hunt"
                    className={`img ${styles.mainImg}`}
                />
            </div>
        </Wrapper>
    );
};

export default Landing;
