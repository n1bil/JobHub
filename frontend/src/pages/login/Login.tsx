import { Link, Form, redirect, useNavigation } from "react-router-dom";
import styles from "./Login.module.css";
import Wrapper from "../../assets/wrappers/RegisterAndLoginPage";
import { FormRow, Logo } from "../../components";
import customFetch from "../../utils/customFetch";
import { toast } from "react-toastify";
import { CustomAxiosError, handleError } from "../../utils/CustomError";

export const action = async ({ request }: { request: Request }) => {
    const formData = await request.formData();
    const data = Object.fromEntries(formData);
    // const errors = { message: '' };
    // if (data.password.length < 3) {
    //     errors.message = 'password too short';
    //     return errors;
    // }
    try {
        await customFetch.post("/auth/login", data);
        toast.success("Login successful");
        return redirect("/dashboard");
    } catch (error) {
        handleError(error as CustomAxiosError);
        // errors.message = handleError(error as CustomAxiosError);
        return error;
    }
};

const Login = () => {
    const navigation = useNavigation();
    const isSumitting = navigation.state === "submitting";
    // const errors = useActionData();

    return (
        <Wrapper>
            <Form method="post" className={`form ${styles.form}`}>
                <Logo />
                <h4>login</h4>
                {/* {errors?.message && <p style={{color: 'red'}}>{errors.message}</p>} */}
                <p></p>
                <FormRow type="email" name="email" />
                <FormRow type="password" name="password" />
                <button
                    type="submit"
                    className={`btn btn-block ${styles.btn}`}
                    disabled={isSumitting}
                >
                    {isSumitting ? "submitting" : "submit"}
                </button>
                <p>
                    Not a member yet ?
                    <Link to="/register" className={styles.memberBtn}>
                        Register
                    </Link>
                </p>
            </Form>
        </Wrapper>
    );
};

export default Login;
