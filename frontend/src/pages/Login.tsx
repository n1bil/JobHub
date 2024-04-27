import { Link, Form, redirect } from "react-router-dom";
import Wrapper from "../assets/css/RegisterAndLoginPage";
import { FormRow, SubmitBtn } from "../components";
import customFetch from "../utils/customFetch";
import { toast } from "react-toastify";
import { CustomAxiosError, handleError } from "../utils/CustomError";

export const action = async ({ request }: { request: Request }) => {
    const formData = await request.formData();
    const data = Object.fromEntries(formData);
    try {
        await customFetch.post("/auth/login", data);
        toast.success("Login successful");
        return redirect("/dashboard");
    } catch (error) {
        handleError(error as CustomAxiosError);
        return error;
    }
};

const Login = () => {
    return (
        <Wrapper>
            <Form method="post" className="form">
                <div className="logo">
                    <h3>
                        JOB<span>HUB</span>
                    </h3>
                </div>
                <h4>login</h4>
                <FormRow type="email" name="email" />
                <FormRow type="password" name="password" />
                <SubmitBtn formBtn />
                <p>
                    Not a member yet ?
                    <Link to="/register" className="member-btn">
                        Register
                    </Link>
                </p>
            </Form>
        </Wrapper>
    );
};

export default Login;
