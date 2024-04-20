import { FormRow } from "../components";
import Wrapper from "../assets/wrappers/DashboardFormPage";
import { ActionFunction, useOutletContext } from "react-router-dom";
import { useNavigation, Form } from "react-router-dom";
import customFetch from "../utils/customFetch";
import { toast } from "react-toastify";
import { CustomAxiosError, handleError } from "../utils/CustomError";

interface User {
    user: {
        id: string;
        name: string;
        lastName: string;
        role: string;
        email: string;
        location: string;
    };
}

export const action: ActionFunction = async ({ request }) => {
    const formData = await request.formData();
    const file = formData.get("avatar") as File;
    if (file && file.size > 500000) {
        toast.error("Image size too large");
        return null;
    }
    try {
        await customFetch.put("/users/update-user", formData);
        toast.success("Profile updated successfully");
    } catch (error) {
        handleError(error as CustomAxiosError);
    }
    return null;
};

const Profile = () => {
    const {
        user: { name, lastName, location },
    } = useOutletContext() as User;
    const navigation = useNavigation();
    const isSubmitting = navigation.state === "submitting";

    return (
        <Wrapper>
            <Form method="post" className="form" encType="multipart/form-data">
                <h4 className="form-title">profile</h4>
                <div className="form-center">
                    <div className="form-row">
                        <label htmlFor="avatar" className="form-label">
                            Select an image file (max 0.5 MB)
                        </label>
                        <input
                            type="file"
                            id="avatar"
                            name="avatar"
                            className="form-input"
                            accept="image/*"
                        />
                    </div>
                    <FormRow type="text" name="name" defaultValue={name} />
                    <FormRow
                        type="text"
                        name="lastName"
                        labelText="last name"
                        defaultValue={lastName}
                    />
                    <FormRow type="password" name="password" />
                    <FormRow
                        type="text"
                        name="location"
                        defaultValue={location}
                    />
                    <button
                        className="btn btn-block form-btn"
                        type="submit"
                        disabled={isSubmitting}
                    >
                        {isSubmitting ? "submitting..." : "submit"}
                    </button>
                </div>
            </Form>
        </Wrapper>
    );
};

export default Profile;
