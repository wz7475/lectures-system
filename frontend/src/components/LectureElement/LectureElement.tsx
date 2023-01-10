import React from "react";
import {IParentComponentProps} from "../../common/parentComponentProps";
import {useGetLectureQuery} from "../../store/services/api";
import Loading from "../Loading/Loading";
import {NavLink} from "react-router-dom";

interface ILectureElement extends IParentComponentProps {
    id: number;
}

const LectureElement: React.FC<ILectureElement> = (props) => {
    const {data: lecture, isFetching: isFetchingLecture} = useGetLectureQuery(props.id);

    const formatTime = (hours: number, minutes: number): string => {
        let formattedMinutes = minutes > 9 ? minutes.toString() : `0${minutes}`;
        return `${hours}:${formattedMinutes}`;
    }

    if (isFetchingLecture) {
        return <div className="lecture-element">
            <Loading/>
        </div>;
    }

    return (
        <div className="lecture-element">
            <NavLink to={"/lectures/" + props.id}>
                {!lecture ? (
                    "Cannot fetch lecture data"
                ) : (
                    <>
                        <div className="lecture-element-part lecture-element-id">{lecture.id}</div>
                        <div className="lecture-element-part lecture-element-name">{lecture.name}</div>
                        <div className="lecture-element-part"><i className="icon-time"/>{formatTime(lecture.beginTimeHour, lecture.beginTimeMinute)}</div>
                        <div className="lecture-element-part"><i className="icon-duration"/>{formatTime(lecture.durationHours, lecture.durationMinutes)}</div>
                    </>
                )}
            </NavLink>
            <div className="lecture-element-controls">
                {props.children}
            </div>
        </div>
    );
}

export default LectureElement;