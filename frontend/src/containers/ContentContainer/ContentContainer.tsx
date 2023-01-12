import React from "react";
import "./ContentContainer.css"
import {Navigate, Route, Routes} from "react-router";
import LecturesPage from "../LecturesPage/LecturesPage";
import LecturePage from "../LecturePage/LecturePage";
import LectureAddPage from "../LectureAddPage/LectureAddPage";

const ContentContainer: React.FC = () => {
    return (
        <div className="content-container">
            <Routes>
                <Route path="/" element={<Navigate to="/lectures"/>}/>
                <Route path="/lectures" element={<LecturesPage/>}>
                    <Route index element={<div>3</div>}/>
                </Route>
                <Route path="/lectures/:lectureId" element={<LecturePage/>} />
                <Route path="/lectures/new/*" element={<LectureAddPage/>} />
                <Route path="/lectures/modify/" element={<Navigate to="/lectures/new" replace/>}/>
                <Route path="/lectures/modify/:lectureId" element={<div className="temporary-disabled">This function is temporary disabled</div>}/>
                <Route path="/offers" element={<div>Offers</div>}/>
                <Route path="/profile" element={<div>Profile</div>}/>
                <Route path="/profile/:profileId" element={<div>Profile with ID</div>}/>
                <Route path="*" element={<div>Page not found</div>}/>
            </Routes>
        </div>
    );
};

export default ContentContainer;