import "./LoadingOverlay.css";
import React from "react";
import Loading from "../Loading/Loading";

export const LoadingOverlay: React.FC = () => {
    return (
        <div className="loading-overlay">
            <Loading/>
        </div>
    );
}