import logo from "../assets/images/logo-land.png";
import logoBack from "../assets/images/logo-color.png";
import { checkDefaultTheme } from "../App";

const LogoCopy = () => {
  const theme = checkDefaultTheme(); 
  
    return <img src={theme ? logoBack : logo} alt='jobhub' className='logo' />;
  };

export default LogoCopy;