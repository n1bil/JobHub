import { FormRow, FormRowSelect, SubmitBtn } from "../components";
import Wrapper from "../assets/css/DashboardFormPage";
import { useLoaderData } from "react-router-dom";
import { JOB_STATUS, JOB_TYPE } from "../utils/constants";
import { Form, redirect } from "react-router-dom";
import { toast } from "react-toastify";
import customFetch from "../utils/customFetch";
import { CustomAxiosError, handleError } from "../utils/CustomError";
import { InvalidateQueryFilters, QueryClient, useQuery } from "@tanstack/react-query";

interface Params {
    id: string;
}

const singleJobQuery = (id: string) => {
    return {
        queryKey:['job', id],
        queryFn: async () => {
            const { data } = await customFetch.get(`/jobs/${id}`);
            return data;
        }
    }
}

export const loader = (queryClient: QueryClient) => async ({ params }: { params: unknown }) => {
    try {
        const prop = params as Params;
        await queryClient.ensureQueryData(singleJobQuery(prop.id));
        return prop.id;
    } catch (error) {
        handleError(error as CustomAxiosError);
        return redirect("/dashboard/all-jobs");
    }
};

export const action = (queryClient: QueryClient) => async ({ request, params }: { request: unknown, params: unknown }) => {
    const propRequest = request as Request;
    const propParams = params as Params;
    const formData = await propRequest.formData();
    const data = Object.fromEntries(formData);
    try {
        await customFetch.put(`/jobs/${propParams.id}`, data);
        toast.success("Job edited successfully");
        queryClient.invalidateQueries(['jobs'] as InvalidateQueryFilters);
        return redirect("/dashboard/all-jobs");
    } catch (error) {
        handleError(error as CustomAxiosError);
        return redirect("/dashboard/all-jobs");
    }
};

const EditJob = () => {
    // const data = useLoaderData() as Job;
    const id = useLoaderData() as string;
    const { data } = useQuery(singleJobQuery(id));

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
                    <SubmitBtn formBtn />
                </div>
            </Form>
        </Wrapper>
    );
};

export default EditJob;
