import React from "react";
import {NavLink} from "react-router-dom";
import "./LecturesListElement.css";
import {useSelector} from "react-redux";
import {selectIsAdmin, selectSessionKey} from "../../../store/features/authSlice";
import {
    useGetLectureQuery,
    useGetSignupLecturesQuery,
    useOptoutLectureMutation,
    useSignupLectureMutation
} from "../../../store/services/api";
import Loading from "../../../components/Loading/Loading";

interface LecturesListElementProps {
    id: number;
}

const LecturesListElement: React.FC<LecturesListElementProps> = (props) => {
    const isAdmin = useSelector(selectIsAdmin);
    const sessionKey = useSelector(selectSessionKey);

    const {data: signupLectures, isFetching: isFetchingSignupLectures} = useGetSignupLecturesQuery(sessionKey);
    const {data: lecture, isFetching: isFetchingLecture} = useGetLectureQuery(props.id);
    const [signup, {isLoading: isLoadingSignup}] = useSignupLectureMutation();
    const [optout, {isLoading: isLoadingOptout}] = useOptoutLectureMutation();

    const handleSignupClick = () => {
        signup({
            session: sessionKey,
            data: props.id
        })
    }
    const handleOptoutClick = () => {
        optout({
            session: sessionKey,
            data: props.id
        })
    }

    if (isFetchingLecture) {
        return <div className="lectures-list-element">
            <Loading/>
        </div>;
    }

    return (
        <div className="lectures-list-element">
            <NavLink to={"/lectures/" + props.id}>
                <div className="lectures-list-element-name">
                    {lecture ? lecture.name : "Cannot fetch lecture data"} {lecture ? lecture.id : ""}
                </div>
            </NavLink>
            <div className="lectures-list-element-controls">
                {isAdmin ? (
                    <>
                        <NavLink to={"/lectures/modify/" + props.id}>
                            <button className="blue">Modify</button>
                        </NavLink>
                        <button className="red">Delete</button>
                    </>
                ) : (
                    (isFetchingLecture || isFetchingSignupLectures || isLoadingSignup || isLoadingOptout) ? (
                        <Loading/>
                    ) : (
                        signupLectures !== undefined && lecture !== undefined && signupLectures.some(lec => lec.id === lecture!.id) ? (
                            <button className="red" onClick={handleOptoutClick}>Opt out</button>
                        ) : (
                            <button onClick={handleSignupClick}>Sign up</button>
                        )
                    )
                )}
            </div>
        </div>
    );
};

export default LecturesListElement;