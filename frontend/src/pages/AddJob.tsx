import { FormRow, FormRowSelect, SubmitBtn } from "../components";
import Wrapper from "../assets/css/DashboardFormPage";
import { useOutletContext } from "react-router-dom";
import { JOB_STATUS, JOB_TYPE } from "../utils/constants";
import { Form, redirect } from "react-router-dom";
import { toast } from "react-toastify";
import customFetch from "../utils/customFetch";
import { CustomAxiosError, handleError } from "../utils/CustomError";
import { InvalidateQueryFilters, QueryClient } from "@tanstack/react-query";

interface User {
  location: string | undefined;
  name: string;
}

interface OutletContext {
  user: User;
}

export const action = (queryClient: QueryClient) => async ({ request }: { request: Request }) => {
    const formData = await request.formData();
    const data = Object.fromEntries(formData);
    try {
        await customFetch.post("/jobs", data);
        queryClient.invalidateQueries(['jobs'] as InvalidateQueryFilters);
        toast.success("Job added successfully");
        return redirect('all-jobs');
    } catch (error) {
        handleError(error as CustomAxiosError);
        return error;
    }
};

const AddJob = () => {
    const { user } = useOutletContext() as OutletContext;


    return (
        <Wrapper>
            <Form method="post" className="form">
                <h4 className="form-title">add job</h4>
                <div className="form-center">
                    <FormRow type="text" name="position" />
                    <FormRow type="text" name="company" />
                    <FormRow
                        type="text"
                        labelText="job location"
                        name="jobLocation"
                        defaultValue={user.location}
                    />
                    <FormRowSelect
                        labelText="job status"
                        name="jobStatus"
                        defaultValue={JOB_STATUS.PENDING}
                        list={Object.values(JOB_STATUS)}
                    />
                    <FormRowSelect
                        labelText="job type"
                        name="jobType"
                        defaultValue={JOB_TYPE.FULL_TIME}
                        list={Object.values(JOB_TYPE)}
                    />
                    <SubmitBtn formBtn />
                </div>
            </Form>
        </Wrapper>
    );
};

export default AddJob;
