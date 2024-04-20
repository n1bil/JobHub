import { Form, redirect, useNavigation, Link } from "react-router-dom";
import Wrapper from "../../assets/wrappers/RegisterAndLoginPage";
// import styles from "./Register.module.css";
import { Logo, FormRow } from "../../components";
import customFetch from "../../utils/customFetch";
import { toast } from "react-toastify";
import { CustomAxiosError, handleError } from "../../utils/CustomError";

export const action = async ({ request }: { request: Request }) => {
    const formData = await request.formData();
    const data = Object.fromEntries(formData);

    try {
        await customFetch.post("/auth/register", data);
        toast.success('Registration successful');
        return redirect('/login');
    } catch (error) {
        handleError(error as CustomAxiosError);
        return error;
    }
};

const Register = () => {
    const navigation = useNavigation();
    const isSumitting = navigation.state === 'submitting'

    return (
        <Wrapper>
            <Form method="post" className="form">
                <Logo />
                <h4>Register</h4>
                <FormRow type="text" name="name" labelText="name" />
                <FormRow type="text" name="lastName" labelText="Last Name" />
                <FormRow type="text" name="location" labelText="Location" />
                <FormRow type="email" name="email" labelText="Email" />
                <FormRow type="password" name="password" labelText="Password" />
                <button type="submit" className="btn btn-block" disabled={isSumitting}>
                    {isSumitting ? 'submitting...' : 'submit'}
                </button>
                <p>
                    Already a member ?
                    <Link to="/login" className="member-btn">
                        Login
                    </Link>
                </p>
            </Form>
        </Wrapper>
    );
};

export default Register;
