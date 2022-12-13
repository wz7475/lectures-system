import React from "react";
import LecturesList from "./LecturesList/LecturesList";
import "./LecturesPage.css";
import LecturesListControls from "./LecturesListControls/LecturesListControls";

const LecturesPage: React.FC = () => {
    return (
        <>
            <div className="page-content">
                <LecturesListControls/>
                <LecturesList/>
            </div>
        </>
    );
}

export default LecturesPage;