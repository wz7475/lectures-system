import React, {useState} from "react";
import "./../LectureAddPage/LectureAddPage.css";
import {useGetLectureQuery, useModifyLectureMutation} from "../../store/services/api";
import {useSelector} from "react-redux";
import {selectSessionKey} from "../../store/features/authSlice";
import Loading from "../../components/Loading/Loading";
import {useParams} from "react-router";
import FetchError from "../../components/FetchError/FetchError";
import LectureContainer from "../LectureContainer/LectureContainer";
import Lecture from "../../common/Lecture";
import {useNavigate} from "react-router-dom";

const LectureModifyPage: React.FC = () => {
    const {lectureId} = useParams();
    const navigate = useNavigate();
    const sessionKey = useSelector(selectSessionKey);
    const [newLecture, setNewLecture] = useState<Lecture>({
        id: 0,
        name: "",
        dayOfWeek: 1,
        beginTimeHour: 0,
        beginTimeMinute: 0,
        durationHours: 0,
        durationMinutes: 0
    })
    const {data: lecture, isFetching} = useGetLectureQuery(lectureId!);
    const [modifyLecture, {isLoading: isLoadingAddLecture}] = useModifyLectureMutation();

    const handleModify = async () => {
        await modifyLecture({
            session: sessionKey,
            data: newLecture
        });
        navigate(`/lectures/${lectureId}`);
    }

    if (isLoadingAddLecture || isFetching) {
        return <Loading/>;
    }

    if (lecture === undefined) {
        return <FetchError>
            <span>Cannot find lecture</span>
        </FetchError>
    }

    return (
        <div className="page-content">
            <div className="page-title">Modify lecture</div>
            <LectureContainer lecture={lecture} onChange={(lec) => setNewLecture(lec)}/>
            <button onClick={handleModify} className="blue">Modify</button>
        </div>
    );
}

export default LectureModifyPage;