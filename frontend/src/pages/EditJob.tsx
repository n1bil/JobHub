import { FormRow, FormRowSelect } from "../components";
import Wrapper from "../assets/wrappers/DashboardFormPage";
import { useLoaderData } from "react-router-dom";
import { JOB_STATUS, JOB_TYPE } from "../utils/constants";
import { Form, useNavigation, redirect } from "react-router-dom";
import { toast } from "react-toastify";
import customFetch from "../utils/customFetch";
import { CustomAxiosError, handleError } from "../utils/CustomError";
import { Job } from "../utils/JobAbstract";

type Props = {
    request: Request;
    params: {
        id: string;
    };
};

export const loader = async ({ params }: Props) => {
    try {
        const { data } = await customFetch.get(`/jobs/${params.id}`);
        return data;
    } catch (error) {
        handleError(error as CustomAxiosError);
        return redirect("/dashboard/all-jobs");
    }
};

export const action = async ({ request, params }: Props) => {
    console.log(params);
    const formData = await request.formData();
    const data = Object.fromEntries(formData);
    try {
        await customFetch.put(`/jobs/${params.id}`, data);
        toast.success("Job edited successfully");
        return redirect("/dashboard/all-jobs");
    } catch (error) {
        handleError(error as CustomAxiosError);
        return redirect("/dashboard/all-jobs");
    }
};

const EditJob = () => {
    const data = useLoaderData() as Job;
    const navigation = useNavigation();
    const isSubmitting = navigation.state === "submitting";

    return (
        <Wrapper>
            <Form method="post" className="form">
                <h4 className="form-title">edit job</h4>
                <div className="form-center">
                    <FormRow
                        type="text"
                        name="position"
                        defaultValue={data.position}
                    />
                    <FormRow
                        type="text"
                        name="company"
                        defaultValue={data.company}
                    />
                    <FormRow
                        type="text"
                        name="jobLocation"
                        defaultValue={data.jobLocation}
                        labelText="job location"
                    />

                    <FormRowSelect
                        name="jobStatus"
                        labelText="job status"
                        defaultValue={data.jobStatus}
                        list={Object.values(JOB_STATUS)}
                    />
                    <FormRowSelect
                        name="jobType"
                        labelText="job type"
                        defaultValue={data.jobType}
                        list={Object.values(JOB_TYPE)}
                    />
                    <button
                        type="submit"
                        className="btn btn-block form-btn"
                        disabled={isSubmitting}
                    >
                        {isSubmitting ? "submitting..." : "submit"}
                    </button>
                </div>
            </Form>
        </Wrapper>
    );
};

export default EditJob;
