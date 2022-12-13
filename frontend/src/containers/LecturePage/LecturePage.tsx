import React from "react";
import {useParams} from "react-router";
import {useSelector} from "react-redux";
import Lecture from "../../store/models/lectures/Lecture";
import {AppState} from "../../store/store";
import Weekdays from "../../store/models/common/Weekdays";

const LecturePage: React.FC = () => {
    const {lectureId} = useParams();
    const lectures = useSelector<AppState, Lecture[]>(state => state.lectures.lectures);
    const lecture = lectures.find(lecture => lecture.id.toString() == lectureId);

    if (lecture === undefined) {
        return (
            <div>Error when fetching lecture data</div>
        );
    }

    return (
        <>
            <div>Lecture Id: {lecture.id} </div>
            <div>Lecture Name: {lecture.name}</div>
            <div>Lecture Day of the Week: {Weekdays.fromNumber(lecture.dayOfWeek)}</div>
            <div>Lecture begin time: {lecture.beginTimeHour}:{lecture.beginTimeMinutes}</div>
            <div>Lecture duration: {lecture.durationHours}:{lecture.durationMinutes}</div>
        </>
    );
};

export default LecturePage;