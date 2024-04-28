import { Params, redirect } from "react-router-dom";
import { CustomAxiosError, handleError } from "../utils/CustomError";
import customFetch from "../utils/customFetch";
import { toast } from "react-toastify";
import { InvalidateQueryFilters, QueryClient } from "@tanstack/react-query";

export const action = (queryClient: QueryClient) => async ({ params }: { params: unknown }) => {
    try {
        const propParams = params as Params;
        await customFetch.delete(`/jobs/${propParams.id}`);
        queryClient.invalidateQueries(['jobs'] as InvalidateQueryFilters);

      toast.success('Job deleted successfully');
    } catch (error) {
        handleError(error as CustomAxiosError);
        return error;
    }
    return redirect("/dashboard/all-jobs");
};
