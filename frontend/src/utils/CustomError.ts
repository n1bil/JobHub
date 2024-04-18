import { AxiosError, AxiosResponse } from "axios";
import { toast } from "react-toastify";

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export interface CustomAxiosError<T = any, U = any> extends AxiosError<T, U> {
    response?: AxiosResponse<T, U>;
}

export const handleError = (error: CustomAxiosError) => {
    if (error.response?.data?.message) {
        toast.error(error.response.data.message);
    } else {
        console.error("An error occurred:", error);
    }
};