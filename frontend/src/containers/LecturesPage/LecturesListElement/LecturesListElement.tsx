import React from "react";
import "../../../components/LectureElement/LectureElement.css";
import LecturesListElementControls from "../LecturesListElementControls/LecturesListElementControls";
import LectureElement from "../../../components/LectureElement/LectureElement";

interface ILecturesListElementProps {
    id: number;
}

const LecturesListElement: React.FC<ILecturesListElementProps> = (props) => {
    return (
        <LectureElement id={props.id}>
            <LecturesListElementControls id={props.id}/>
        </LectureElement>
    )
};

export default LecturesListElement;