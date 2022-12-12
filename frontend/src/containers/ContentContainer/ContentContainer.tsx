import React from "react";
import "./ContentContainer.css"
import {Navigate, Route, Routes} from "react-router";
import LecturesPage from "../LecturesPage/LecturesPage";

const ContentContainer: React.FC = () => {
    return (
        <div className="content-container">
            <Routes>
                <Route path="/*" element={<Navigate to="/lectures"/>}/>
                <Route index path="/lectures" element={<LecturesPage/>}/>
            </Routes>
        </div>
    );
};

export default ContentContainer;