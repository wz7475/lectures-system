import React from "react";
import {NavLink} from "react-router-dom";
import "./LecturesListElement.css";
import {AppDispatch, AppState} from "../../../store/store";
import {useDispatch, useSelector} from "react-redux";
import {signUpLecture} from "../../../store/reducers/lecturesReducer";

interface LecturesListElementProps {
    id: string;
    name: string;
}

const LecturesListElement: React.FC<LecturesListElementProps> = (props) => {
    const dispatch: AppDispatch = useDispatch();

    const isAdmin = useSelector<AppState, boolean>((state) => state.auth.isAdmin);

    const handleSignupClick = () => {
        dispatch(signUpLecture({id: props.id}));
    }

    return (
        <div className="lectures-list-element">
            <NavLink to={"/lectures/" + props.id}>
                <div className="lectures-list-element-name">{props.name}</div>
            </NavLink>
            <div className="lectures-list-element-controls">
                <button onClick={handleSignupClick} disabled>Sign up</button>
                {isAdmin && <>
                    <NavLink to={"/lectures/modify/" + props.id}>
                        <button className="blue">Modify</button>
                    </NavLink>
                    <button className="red" disabled>Delete</button>
                </>}
            </div>
        </div>
    );
};

export default LecturesListElement;