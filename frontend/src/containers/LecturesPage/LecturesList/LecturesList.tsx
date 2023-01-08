import React, {useEffect} from "react";
import LecturesListElement from "../LecturesListElement/LecturesListElement";
import "./LecturesList.css";
import Loading from "../../../components/Loading/Loading";
import {useGetLecturesQuery} from "../../../store/services/api";
import lodash from "lodash";
import weekdays from "../../../store/models/common/Weekdays";

const LecturesList: React.FC = () => {
    const {data: lectures, isLoading} = useGetLecturesQuery();

    if (isLoading) {
        return <Loading/>;
    }

    if (!lectures) {
        return <div>No posts</div>
    }

    const groups = lodash.chain(lectures).groupBy('dayOfWeek').toPairs().map(group => {
        const list = group[1].map(lecture => {
            return <LecturesListElement key={lecture.id} id={lecture.id}/>;
        });

        return (
            <>
                <div key={group[0]} className="lectures-list-group">{weekdays.fromNumber(parseInt(group[0]))}</div>
                <div>
                    {list}
                </div>
            </>
        );
    }).value();

    return (
        <div className="lectures-list">{groups}</div>
    );
};

export default LecturesList;