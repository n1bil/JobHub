import { checkDefaultTheme } from "../App";
import logo from "../assets/images/logo.svg";
import logoBack from "../assets/images/logo-color.svg";

const Logo = () => {
    const theme = checkDefaultTheme();    

    return <img src={theme ? logo : logoBack} alt='jobhub' className='logo' />;
  };

export default Logo;