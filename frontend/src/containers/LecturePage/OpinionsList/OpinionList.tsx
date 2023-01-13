import React from "react";
import {useGetLectureOpinionsQuery} from "../../../store/services/api";
import Loading from "../../../components/Loading/Loading";
import FetchError from "../../../components/FetchError/FetchError";
import OpinionComponent from "../../../components/Opinion/OpinionComponent";
import OpinionListControls from "../OpinionListControls/OpinionListControls";

interface OpinionListProps {
    id: number;
}

const OpinionList: React.FC<OpinionListProps> = (props) => {
    const {data: opinions, isFetching} = useGetLectureOpinionsQuery(props.id);

    if (isFetching) {
        return (
            <Loading/>
        );
    }

    if (opinions === undefined) {
        return <FetchError>
            <span>Cannot fetch opinions</span>
        </FetchError>;
    }

    return (
        <div className="opinions-list">
            {opinions.map(opinion => <OpinionComponent id={opinion.id}>
                <OpinionListControls id={opinion.id}/>
            </OpinionComponent>)}
        </div>
    );
}

export default OpinionList;