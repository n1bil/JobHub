import { redirect } from "react-router-dom";
import { CustomAxiosError, handleError } from "../utils/CustomError";
import customFetch from "../utils/customFetch";
import { toast } from "react-toastify";

type Props = {
    params: {
        id: string;
    };
};

export const action = async ({ params }: Props) => {
    try {
        await customFetch.delete(`/jobs/${params.id}`);
        toast.success("Job deleted successfully");
    } catch (error) {
        handleError(error as CustomAxiosError);
        return error;
    }
    return redirect("/dashboard/all-jobs");
};
