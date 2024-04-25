import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import "./index.css";
import "react-toastify/dist/ReactToastify.css";
import { ToastContainer } from "react-toastify";

// fetch("/api/v1/jobs")
//     .then((response) => response.json())
//     .then((data) => console.log(data))
//     .catch((error) => console.error("Error fetching data:", error));

fetch("https://backend-6hkg.onrender.com/api/v1/jobs")
    .then((response) => response.json())
    .then((data) => console.log(data))
    .catch((error) => console.error("Error fetching data:", error));

ReactDOM.createRoot(document.getElementById("root")!).render(
    <React.StrictMode>
        <App />
        <ToastContainer position="top-center" />
    </React.StrictMode>
);
