import { ActionFunction, redirect } from "react-router-dom";
import { CustomAxiosError, handleError } from "../utils/CustomError";
import customFetch from "../utils/customFetch";
import { toast } from "react-toastify";

export const action: ActionFunction = async ({ params }) => {
    try {
        const { id } = params;
        await customFetch.delete(`/jobs/${id}`);
        toast.success("Job deleted successfully");
    } catch (error) {
        handleError(error as CustomAxiosError);
        return error;
    }
    return redirect("/dashboard/all-jobs");
};
