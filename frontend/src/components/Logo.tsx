import { checkDefaultTheme } from "../App";
import logo from "../assets/images/logo.png";
import logoBack from "../assets/images/logo-color.png";

const Logo = () => {
    const theme = checkDefaultTheme();    

    return <img src={theme ? logoBack : logo} alt='jobhub' className='logo' />;
  };

export default Logo;