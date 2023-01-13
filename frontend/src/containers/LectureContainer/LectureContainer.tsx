import React, {useState} from "react";
import Lecture from "../../common/Lecture";
import "./LectureContainer.css";

interface ILectureContainer {
    lecture: Lecture;
    onChange: (lecture: Lecture) => void;
}

const LectureContainer: React.FC<ILectureContainer> = (props) => {
    const lecture = props.lecture;

    const [name, setName] = useState(lecture.name);
    const [dayOfWeek, setDayOfWeek] = useState(lecture.dayOfWeek);
    const [beginTimeHour, setBeginTimeHour] = useState(lecture.beginTimeHour);
    const [beginTimeMinute, setBeginTimeMinute] = useState(lecture.beginTimeMinute);
    const [durationHours, setDurationHours] = useState(lecture.durationHours);
    const [durationMinutes, setDurationMinutes] = useState(lecture.durationMinutes);

    const update = (obj: Object) => {
        const lec = {
            id: lecture.id,
            name,
            dayOfWeek,
            beginTimeHour,
            beginTimeMinute,
            durationHours,
            durationMinutes
        };

        props.onChange({...lec, ...obj});
    }

    return (
        <div className="lecture-add-inputs">
            <div className="input-container">
                <div className="input-label">
                    Name:
                </div>
                <input type="text" placeholder="Name" value={name}
                       onChange={(event) => {
                           setName(event.target.value);
                           update({name: event.target.value});
                       }}/>
            </div>

            <div className="input-container">
                <div className="input-label">
                    Day of Week
                </div>
                <select onChange={(event) => {
                    setDayOfWeek(parseInt(event.target.value));
                    update({dayOfWeek: event.target.value});
                }} value={dayOfWeek}>
                    <option value="1">Monday</option>
                    <option value="2">Tuesday</option>
                    <option value="3">Wednesday</option>
                    <option value="4">Thursday</option>
                    <option value="5">Friday</option>
                    <option value="6">Saturday</option>
                    <option value="7">Sunday</option>
                </select>
            </div>

            <div className="input-container">
                <div className="input-label">
                    Begin Time:
                </div>
                <div className="input-container-content">
                    <input type="number" min="0" max="24" step="1" placeholder="Hour" value={beginTimeHour}
                           onChange={(event) => {
                               setBeginTimeHour(parseInt(event.target.value));
                               update({beginTimeHour: event.target.value});
                           }}/>
                    <input type="number" min="0" max="60" step="1" placeholder="Minute" value={beginTimeMinute}
                           onChange={(event) => {
                               setBeginTimeMinute(parseInt(event.target.value));
                               update({beginTimeMinute: event.target.value});
                           }}/>
                </div>
            </div>

            <div className="input-container">
                <div className="input-label">
                    Duration:
                </div>
                <div className="input-container-content">
                    <input type="number" min="0" max="24" step="1" placeholder="Hour" value={durationHours}
                           onChange={(event) => {
                               setDurationHours(parseInt(event.target.value));
                               update({durationHours: event.target.value});
                           }}/>
                    <input type="number" min="0" max="60" step="1" placeholder="Minute" value={durationMinutes}
                           onChange={(event) => {
                               setDurationMinutes(parseInt(event.target.value));
                               update({durationMinutes: event.target.value});
                           }}/>

                </div>
            </div>
        </div>
    );
}

export default LectureContainer;