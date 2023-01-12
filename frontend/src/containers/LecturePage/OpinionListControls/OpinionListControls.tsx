import React from "react";
import {Id, useDeleteOpinionMutation, useGetUserOpinionsQuery} from "../../../store/services/api";
import {useSelector} from "react-redux";
import {selectIsAdmin, selectSessionKey} from "../../../store/features/authSlice";
import Loading from "../../../components/Loading/Loading";

interface IOpinionListControls {
    id: Id;
}

const OpinionListControls: React.FC<IOpinionListControls> = (props) => {
    const isAdmin = useSelector(selectIsAdmin);
    const sessionKey = useSelector(selectSessionKey);
    const {data: userOpinions, isFetching: isFetchingUserOpinions} = useGetUserOpinionsQuery(1);
    const [deleteOpinion, {isLoading}] = useDeleteOpinionMutation();
    // @TODO: change to real userId
    const userOpinion = userOpinions ? userOpinions.some(opinion => opinion.id === props.id) : false;

    const handleDelete = () => {
        deleteOpinion({
            session: sessionKey,
            data: props.id
        });
    }

    return (
        <>
            {(isAdmin || userOpinion) ? (
                (isLoading || isFetchingUserOpinions) ? (
                    <Loading/>
                ) : (
                    <button onClick={handleDelete} className="red">Delete</button>
                )
            ) : (
                <></>
            )}
        </>
    );
}

export default OpinionListControls;