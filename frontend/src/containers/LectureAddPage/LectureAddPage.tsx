import React, {useState} from "react";
import "./LectureAddPage.css";
import {useDispatch, useSelector} from "react-redux";
import {AppDispatch, AppState} from "../../store/store";
import Loading from "../../components/Loading/Loading";
import Lecture from "../../store/models/Lecture";
import {Navigate} from "react-router";

const LectureAddPage: React.FC = () => {
    return (
        <div>T</div>
    );
    // const dispatch: AppDispatch = useDispatch();
    // const [sent, setSent] = useState<boolean>(false);
    // const [name, setName] = useState('');
    // const [dayOfWeek, setDayOfWeek] = useState(0);
    // const [beginTimeHour, setBeginTimeHour] = useState(0);
    // const [beginTimeMinutes, setBeginTimeMinutes] = useState(0);
    // const [durationHours, setDurationHours] = useState(0);
    // const [durationMinutes, setDurationMinutes] = useState(0);
    // const state = useSelector<AppState, EState>((state) => state.lectures.state);
    // const lectures = useSelector<AppState, Lecture[]>((state) => state.lectures.lectures);
    //
    // const handleClick = () => {
    //     dispatch(addLecture({id: 0, name, dayOfWeek, beginTimeHour, beginTimeMinutes, durationHours, durationMinutes}));
    //     setSent(true);
    // }
    //
    // if (state == EState.Pending) {
    //     return <Loading/>;
    // }
    //
    // if (state === EState.Complete && sent) {
    //     return <Navigate to={"/lectures/" + lectures[lectures.length - 1].id.toString()} />
    // }
    //
    // return (
    //     <div className="lecture-add-page">
    //         <div>
    //             <input type="text" placeholder="Name" value={name} onChange={(event) => setName(event.target.value)}/>
    //             <input type="number" min="0" max="6" step="1" placeholder="DayOfWeek" value={dayOfWeek} onChange={(event) => setDayOfWeek(parseInt(event.target.value))}/>
    //
    //         </div>
    //         <div>
    //             <span>Begin Time:</span>
    //             <input type="number" min="0" max="24" step="1" placeholder="Hour" value={beginTimeHour} onChange={(event) => setBeginTimeHour(parseInt(event.target.value))}/>
    //             <input type="number" min="0" max="60" step="1" placeholder="Minute" value={beginTimeMinutes} onChange={(event) => setBeginTimeMinutes(parseInt(event.target.value))}/>
    //         </div>
    //         <div>
    //             <span>Duration Time:</span>
    //             <input type="number" min="0" max="24" step="1" placeholder="Hour" value={durationHours} onChange={(event) => setDurationHours(parseInt(event.target.value))}/>
    //             <input type="number" min="0" max="60" step="1" placeholder="Minute" value={durationMinutes} onChange={(event) => setDurationMinutes(parseInt(event.target.value))}/>
    //         </div>
    //         <button onClick={handleClick}>Add lecture</button>
    //     </div>
    // );
}

export default LectureAddPage;