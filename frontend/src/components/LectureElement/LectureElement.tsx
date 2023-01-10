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

    if (isFetchingLecture) {
        return <div className="lectures-list-element">
            <Loading/>
        </div>;
    }

    return (
        <div className="lectures-list-element">
            <NavLink to={"/lectures/" + props.id}>
                <div className="lectures-list-element-name">
                    {lecture ? lecture.name : "Cannot fetch lecture data"} {lecture ? lecture.id : ""}
                </div>
            </NavLink>
            <div className="lectures-list-element-controls">
                {props.children}
            </div>
        </div>
    );
}

export default LectureElement;