import React, {useEffect} from "react";
import LecturesListElement from "../LecturesListElement/LecturesListElement";
import "./LecturesList.css";
import {AppDispatch, AppState} from "../../../store/store";
import {useDispatch, useSelector} from "react-redux";
import EState from "../../../store/models/common/state";
import Lecture from "../../../store/models/lectures/Lecture";
import APIError from "../../../store/models/common/apiError";
import {fetchLectures} from "../../../store/reducers/lecturesReducer";
import Loading from "../../../components/Loading/Loading";

const LecturesList: React.FC = () => {
    const dispatch: AppDispatch = useDispatch();
    const state = useSelector<AppState, EState>((state) => state.lectures.state);
    const error = useSelector<AppState, APIError | null | undefined >((state) => state.lectures.error);
    const lectures = useSelector<AppState, Lecture[]>((state) => state.lectures.lectures);

    useEffect(() => {
        if (state == EState.Idle) {
            dispatch(fetchLectures());
        }
    }, [state, dispatch]);

    if (state == EState.Pending) {
        return (
            <Loading/>
        );
    }

    if (state == EState.Failed && error !== null && error != undefined) {
        return (
            <div>
                Error occurred {error.code} | {error.message}
            </div>
        )
    }

    let list = lectures.map(lecture => {
        return <LecturesListElement key={lecture.id} id={lecture.id.toString()} name={lecture.name}/>
    });

    return (
        <div className="lectures-list">{list}</div>
    );
};

export default LecturesList;