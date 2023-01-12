import React from "react";
import {useParams} from "react-router";
import {useGetLectureQuery} from "../../store/services/api";
import Loading from "../../components/Loading/Loading";
import LecturesListElementControls from "../LecturesPage/LecturesListElementControls/LecturesListElementControls";
import "./LecturePage.css";
import Weekdays from "../../store/models/common/Weekdays";

const LecturePage: React.FC = () => {
    const {lectureId} = useParams();
    const {data: lecture, isFetching} = useGetLectureQuery(lectureId!);

    const formatTime = (hours: number, minutes: number): string => {
        let formattedMinutes = minutes > 9 ? minutes.toString() : `0${minutes}`;
        return `${hours}:${formattedMinutes}`;
    }

    if (isFetching) {
        return <Loading/>
    }

    if (lectureId === undefined || lecture === undefined) {
        return (
            <div>
                Lecture not found
            </div>
        );
    }

    return (
        <div className="page-content">
            <div className="lecture-box">
                <div className="lecture-name">{lecture.name}#{lecture.id}</div>
                <div className="lecture-information">
                    <div className="lecture-part">
                        <i className="icon-list"/> Day: {Weekdays.fromNumber(lecture.dayOfWeek)}
                    </div>
                    <div className="lecture-part">
                        <i className="icon-time"/> Begin
                        hour: {formatTime(lecture.beginTimeHour, lecture.beginTimeMinute)}
                    </div>
                    <div className="lecture-part">
                        <i className="icon-duration"/> Duration
                        time: {formatTime(lecture.durationHours, lecture.durationMinutes)}
                    </div>
                </div>
                <div className="lecture-controls">
                    <LecturesListElementControls id={parseInt(lectureId)}/>
                </div>
            </div>

            <div className="lecture-box">
                <div className="lecture-box-title">Signed users</div>
                @TODO Signed users list
            </div>

            <div className="lecture-box lecture-opinion-box">
                <div className="lecture-box-bar">
                    <div className="lecture-box-title">Opinions</div>
                    <button className="lecture-box-bar-button blue"><i className="icon-message"/>New</button>
                </div>

                <div className="lecture-opinion-add">
                    <textarea placeholder="Your opinion"></textarea>
                    <button className="blue">Send</button>
                    <button>Cancel</button>
                </div>

                @TODO Opinions
            </div>
        </div>
    );
};

export default LecturePage;