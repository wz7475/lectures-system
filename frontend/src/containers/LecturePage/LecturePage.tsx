import React, {useState} from "react";
import {useParams} from "react-router";
import {useAddOpinionMutation, useGetLectureQuery, useGetSignedForLectureQuery} from "../../store/services/api";
import Loading from "../../components/Loading/Loading";
import LecturesListElementControls from "../LecturesPage/LecturesListElementControls/LecturesListElementControls";
import "./LecturePage.css";
import Weekdays from "../../common/Weekdays";
import {useSelector} from "react-redux";
import {selectIsAdmin, selectSessionKey} from "../../store/features/authSlice";
import OpinionList from "./OpinionsList/OpinionList";
import FetchError from "../../components/FetchError/FetchError";
import SignedUser from "./SignedUser/SignedUser";

const LecturePage: React.FC = () => {
    const {lectureId} = useParams();
    const [dialogOpen, setDialogOpen] = useState<boolean>(false);
    const [opinionContent, setOpinionContent] = useState<string>("");

    const isAdmin = useSelector(selectIsAdmin);
    const sessionKey = useSelector(selectSessionKey);

    const {data: lecture, isFetching} = useGetLectureQuery(lectureId!);
    const [addOpinion, {isLoading: isLoadingAddOpinion}] = useAddOpinionMutation();
    const {data: signed, isFetching: isFetchingSigned} = useGetSignedForLectureQuery(lectureId!);

    const formatTime = (hours: number, minutes: number): string => {
        let formattedMinutes = minutes > 9 ? minutes.toString() : `0${minutes}`;
        return `${hours}:${formattedMinutes}`;
    }

    const handleSendOpinion = async () => {
        if (lectureId === undefined)
            return;

        await addOpinion({
            session: sessionKey,
            data: {
                id: 0,
                lectureId: parseInt(lectureId),
                userId: 0,
                createdAt: 0,
                content: opinionContent
            }
        });
        setDialogOpen(false);
    }

    if (isFetching) {
        return <Loading/>
    }

    if (lectureId === undefined || lecture === undefined) {
        return (
            <div>
                Sorry, we cannot find this lecture
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
                <div className="lecture-signed">
                {signed ? (
                    signed.map(user => <SignedUser user={user}/>)
                ) : (
                    isFetchingSigned ? (
                        <Loading/>
                    ) : (
                        <FetchError>
                            <span>Cannot fetch information about signed users. Try again later</span>
                        </FetchError>
                    )
                )}
                </div>
            </div>

            <div className="lecture-box lecture-opinion-box">
                <div className="lecture-box-bar">
                    <div className="lecture-box-title">Opinions</div>
                    {!isAdmin && (
                        <button className="lecture-box-bar-button blue" onClick={() => setDialogOpen(true)}
                                disabled={dialogOpen}><i className="icon-message"/>New</button>
                    )}
                </div>

                {!isAdmin && dialogOpen && (
                    isLoadingAddOpinion ? (
                        <Loading/>
                    ) : (
                        <div className="lecture-opinion-add">
                    <textarea placeholder="Your opinion"
                              onChange={(event) => setOpinionContent(event.target.value)}/>
                            <div className="lecture-opinion-add-controls">
                                <button className="blue" onClick={handleSendOpinion}>Send</button>
                                <button onClick={() => setDialogOpen(false)}>Cancel</button>
                            </div>
                        </div>
                    )
                )}

                <OpinionList id={parseInt(lectureId)}/>
            </div>
        </div>
    );
};

export default LecturePage;