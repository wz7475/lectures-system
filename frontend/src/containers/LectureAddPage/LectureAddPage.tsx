import React, {useState} from "react";
import "./LectureAddPage.css";
import {useAddLectureMutation} from "../../store/services/api";
import {useSelector} from "react-redux";
import {selectSessionKey} from "../../store/features/authSlice";
import Loading from "../../components/Loading/Loading";
import LectureContainer from "../LectureContainer/LectureContainer";
import Lecture from "../../common/Lecture";
import {useNavigate} from "react-router-dom";

const defaultLecture = {
    id: 0,
    name: "Lecture",
    dayOfWeek: 1,
    beginTimeHour: 12,
    beginTimeMinute: 0,
    durationHours: 1,
    durationMinutes: 30
}

const LectureAddPage: React.FC = () => {
    const sessionKey = useSelector(selectSessionKey);
    const navigate = useNavigate();
    const [newLecture, setNewLecture] = useState<Lecture>(defaultLecture);

    const [addLecture, {isLoading: isLoadingAddLecture}] = useAddLectureMutation();

    const handleAdd = async () => {
        await addLecture({
            session: sessionKey,
            data: newLecture
        });
        navigate(`/lectures`);
    }

    if (isLoadingAddLecture) {
        return <Loading/>;
    }

    return (
        <div className="page-content">
            <div className="page-title">Add lecture</div>
            <LectureContainer lecture={defaultLecture} onChange={(lec) => setNewLecture(lec)}/>
            <button onClick={handleAdd} className="blue">Add</button>
        </div>
    );
}

export default LectureAddPage;