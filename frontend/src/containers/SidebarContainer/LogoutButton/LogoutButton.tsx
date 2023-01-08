import React from "react";
import "./LogoutButton.css";
import {AppDispatch} from "../../../store/store";
import {useDispatch, useSelector} from "react-redux";
import {useLogoutMutation} from "../../../store/services/api";
import {removeCredentials, selectSessionKey} from "../../../store/features/authSlice";
import {useNavigate} from "react-router-dom";

const LogoutButton: React.FC = () => {
    const dispatch: AppDispatch = useDispatch();
    const navigate = useNavigate();
    const [logout] = useLogoutMutation();
    const sessionKey = useSelector(selectSessionKey);

    const handleLogout = async () => {
        await logout({sessionKey: sessionKey}).unwrap();
        dispatch(removeCredentials());
        navigate("/");
    }

    return (
        <div className="logout-button-container">
            <button className="red" onClick={handleLogout}>Logout</button>
        </div>
    );
};

export default LogoutButton;