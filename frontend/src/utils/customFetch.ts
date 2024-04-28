import axios from "axios";

const customFetch = axios.create({
    baseURL: 'https://backend-6hkg.onrender.com/api/v1',
    withCredentials: true
})

export default customFetch;