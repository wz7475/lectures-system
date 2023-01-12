import React from "react";
import {Id, useDeleteOpinionMutation, useGetUserOpinionsQuery} from "../../../store/services/api";
import {useSelector} from "react-redux";
import {selectIsAdmin, selectSessionKey} from "../../../store/features/authSlice";
import Loading from "../../../components/Loading/Loading";
import {NavLink} from "react-router-dom";

interface IOpinionListControls {
    id: Id;
    linkTo?: string;
}

const OpinionListControls: React.FC<IOpinionListControls> = (props) => {
    const isAdmin = useSelector(selectIsAdmin);
    const sessionKey = useSelector(selectSessionKey);
    const {data: userOpinions, isFetching: isFetchingUserOpinions} = useGetUserOpinionsQuery(sessionKey);
    const [deleteOpinion, {isLoading}] = useDeleteOpinionMutation();
    const userOpinion = userOpinions ? userOpinions.some(opinion => opinion.id === props.id) : false;

    const handleDelete = () => {
        deleteOpinion({
            session: sessionKey,
            data: props.id
        });
    }

    return (
        <>
            {(isLoading || isFetchingUserOpinions) ? (
                <Loading/>
            ) : (
                <>
                    {props.linkTo && (
                        <NavLink to={props.linkTo}>
                            <button className="blue">Go to</button>
                        </NavLink>
                    )}

                    {(isAdmin || userOpinion) && (
                        <button onClick={handleDelete} className="red">Delete</button>
                    )}
                </>
            )}
        </>
    );
}

export default OpinionListControls;