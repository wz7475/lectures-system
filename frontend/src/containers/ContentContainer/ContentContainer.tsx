import React from "react";
import "./ContentContainer.css"
import {Navigate, Route, Routes} from "react-router";
import LecturesPage from "../LecturesPage/LecturesPage";
import LecturePage from "../LecturePage/LecturePage";
import LectureAddPage from "../LectureAddPage/LectureAddPage";
import LectureModifyPage from "../LectureModifyPage/LectureModifyPage";
import ProfilePage from "../ProfilePage/ProfilePage";
import OffersPage from "../OffersPage/OffersPage";

const ContentContainer: React.FC = () => {
    return (
        <div className="content-container">
            <Routes>
                <Route path="/" element={<Navigate to="/lectures"/>}/>
                <Route path="/lectures" element={<LecturesPage/>}>
                    <Route index element={<div>3</div>}/>
                </Route>
                <Route path="/lectures/:lectureId" element={<LecturePage/>}/>
                <Route path="/lectures/new/*" element={<LectureAddPage/>}/>
                <Route path="/lectures/modify/" element={<Navigate to="/lectures/new" replace/>}/>
                <Route path="/lectures/modify/:lectureId" element={<LectureModifyPage/>}/>
                <Route path="/offers" element={<OffersPage/>}/>
                <Route path="/profile" element={<ProfilePage/>}/>
                <Route path="/profile/:profileId" element={<div>This function is not implemented yet. Sorry :(</div>}/>
                <Route path="*" element={<div>Page not found</div>}/>
            </Routes>
        </div>
    );
};

export default ContentContainer;