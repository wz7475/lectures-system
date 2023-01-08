import React from "react";
import {NavLink} from "react-router-dom";
import "./LecturesListControls.css";
import {AppDispatch} from "../../../store/store";
import {useDispatch, useSelector} from "react-redux";
import {selectIsAdmin} from "../../../store/features/authSlice";
import {api} from "../../../store/services/api";

const LecturesListControls: React.FC = () => {
    const dispatch: AppDispatch = useDispatch();
    const isAdmin = useSelector(selectIsAdmin);

    const handleClick = () => {
        dispatch(api.util.invalidateTags(["Lectures"]));
    }

    return (
        <div className="lectures-list-controls">
            {isAdmin ? (
                <NavLink to="/lectures/new">
                    <button className="blue">New</button>
                </NavLink>
            ) : (
                <button onClick={handleClick}>Refresh</button>
            )}
        </div>
    );
};

export default LecturesListControls;