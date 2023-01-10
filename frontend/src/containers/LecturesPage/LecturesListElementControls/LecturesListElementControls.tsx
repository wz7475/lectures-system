import React from "react";
import {
    useDeleteLectureMutation,
    useGetSignupLecturesQuery,
    useOptoutLectureMutation,
    useSignupLectureMutation
} from "../../../store/services/api";
import {useSelector} from "react-redux";
import {selectIsAdmin, selectSessionKey} from "../../../store/features/authSlice";
import Loading from "../../../components/Loading/Loading";
import {NavLink} from "react-router-dom";

interface ILectureListElementControlsProps {
    id: number;
}

const LecturesListElementControls: React.FC<ILectureListElementControlsProps> = (props) => {
    const isAdmin = useSelector(selectIsAdmin);
    const sessionKey = useSelector(selectSessionKey);

    const {data: signupLectures, isFetching: isFetchingSignupLectures} = useGetSignupLecturesQuery(sessionKey);
    const [signup, {isLoading: isLoadingSignup}] = useSignupLectureMutation();
    const [optout, {isLoading: isLoadingOptout}] = useOptoutLectureMutation();
    const [deleteLecture, {isLoading: isLoadingDelete}] = useDeleteLectureMutation();

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

    const handleDeleteClick = () => {
        deleteLecture({
            session: sessionKey,
            data: props.id
        })
    }


    return (
        <>
            {isAdmin ? (
                (isLoadingDelete) ? (
                    <Loading/>
                ) : (
                    <>
                        <NavLink to={"/lectures/modify/" + props.id}>
                            <button className="blue"><i className="icon-edit"/>Modify</button>
                        </NavLink>
                        <button className="red" onClick={handleDeleteClick}><i className="icon-delete"/>Delete</button>
                    </>
                )
            ) : (
                (isFetchingSignupLectures || isLoadingSignup || isLoadingOptout) ? (
                    <Loading/>
                ) : (
                    signupLectures !== undefined && props.id !== undefined && signupLectures.some(lec => lec.id === props.id) ? (
                        <button className="red" onClick={handleOptoutClick}><i className="icon-cancel"/>Opt out</button>
                    ) : (
                        <button onClick={handleSignupClick}><i className="icon-accept"/>Sign up</button>
                    )
                )
            )}
        </>
    );
}

export default LecturesListElementControls;