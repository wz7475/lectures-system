import React from "react";
import {NavLink} from "react-router-dom";
import "./LecturesListControls.css";
import {AppDispatch, AppState} from "../../../store/store";
import {useDispatch, useSelector} from "react-redux";
import {fetchLectures} from "../../../store/reducers/lecturesReducer";

const LecturesListControls: React.FC = () => {
    const dispatch: AppDispatch = useDispatch();
    const isAdmin = useSelector<AppState, boolean>((state) => state.auth.isAdmin);

    const handleClick = () => {
        dispatch(fetchLectures());
    }

    return (
        <div className="lectures-list-controls">
            <button onClick={handleClick}>Refresh</button>
            {isAdmin && <>
                <NavLink to="/lectures/new">
                    <button className="blue">Add new lecture</button>
                </NavLink>
            </>}
        </div>
    );
};

export default LecturesListControls;