import React from "react";
import "./LogoutButton.css";
import {AppDispatch} from "../../../store/store";
import {useDispatch} from "react-redux";
import {logoutAsync} from "../../../store/reducers/authReducer";

const LogoutButton: React.FC = () => {
    const dispatch: AppDispatch = useDispatch();

    const handleLogout = () => {
        dispatch(logoutAsync());
    }

    return (
        <div className="logout-button-container">
            <button className="red" onClick={handleLogout}>Logout</button>
        </div>
    );
};

export default LogoutButton;