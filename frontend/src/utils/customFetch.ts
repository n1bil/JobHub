import axios from "axios";

const customFetch = axios.create({
    baseURL: 'http://localhost:8081/api/v1',
    withCredentials: true
})

export default customFetch;