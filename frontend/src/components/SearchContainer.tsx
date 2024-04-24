import { FormRow, FormRowSelect } from ".";
import Wrapper from "../assets/css/DashboardFormPage";
import { Form, useSubmit, Link } from "react-router-dom";
import { JOB_TYPE, JOB_STATUS, JOB_SORT_BY } from "../utils/constants";
import { useAllJobsContext } from "../pages/AllJobs";
import { ChangeEvent } from "react";

const SearchContainer = () => {
    const { searchValues: { search, jobStatus, jobType, sort } } = useAllJobsContext();
    const submit = useSubmit();

    const debounce = (onChange: (form: HTMLFormElement | null) => void) => {
        let timeout: number | undefined;
        return (e: ChangeEvent<HTMLInputElement>) => {
            const form = e.currentTarget.form;
            clearTimeout(timeout);
            timeout = setTimeout(() => {
                onChange(form);
            }, 2000);
        };
    };

    return (
        <Wrapper>
            <Form className="form">
                <h5 className="form-title">search form</h5>
                <div className="form-center">
                    <FormRow
                        type="search"
                        name="search"
                        defaultValue={search}
                        onChange={debounce((form) => {
                            submit(form);
                        })}
                    />

                    <FormRowSelect
                        labelText="job status"
                        name="jobStatus"
                        list={["all", ...Object.values(JOB_STATUS)]}
                        defaultValue={jobStatus}
                        onChange={(e: ChangeEvent<HTMLSelectElement>) => {
                            submit(e.currentTarget.form);
                        }}
                    />
                    <FormRowSelect
                        labelText="job type"
                        name="jobType"
                        list={["all", ...Object.values(JOB_TYPE)]}
                        defaultValue={jobType}
                        onChange={(e: ChangeEvent<HTMLSelectElement>) => {
                            return submit(e.currentTarget.form);
                        }}
                    />
                    <FormRowSelect
                        name="sort"
                        defaultValue={sort}
                        list={[...Object.values(JOB_SORT_BY)]}
                        onChange={(e: ChangeEvent<HTMLSelectElement>) => {
                            submit(e.currentTarget.form);
                        }}
                    />
                    <Link
                        to="/dashboard/all-jobs"
                        className="btn form-btn delete-btn"
                    >
                        Reset Search Values
                    </Link>
                </div>
            </Form>
        </Wrapper>
    );
};

export default SearchContainer;
