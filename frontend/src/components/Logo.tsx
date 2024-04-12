import logo from "../assets/images/logo.svg";

type Props = {
    classname: string;
}

const Logo = ({ classname }: Props) => {
    return (
        <img src={logo} alt="jobhub" className={classname} />
    )
};

export default Logo;