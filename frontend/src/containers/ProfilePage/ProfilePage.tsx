import React from "react";
import {useSelector} from "react-redux";
import {selectSessionKey} from "../../store/features/authSlice";
import {useGetSignupLecturesQuery, useGetUserOpinionsQuery} from "../../store/services/api";
import OpinionComponent from "../../components/Opinion/OpinionComponent";
import OpinionListControls from "../LecturePage/OpinionListControls/OpinionListControls";
import Loading from "../../components/Loading/Loading";
import FetchError from "../../components/FetchError/FetchError";
import LecturesListElement from "../LecturesPage/LecturesListElement/LecturesListElement";
import UserOffers from "./UserOffers/UserOffers";

const ProfilePage: React.FC = () => {
    const sessionKey = useSelector(selectSessionKey);
    const {data: userLectures, isFetching: isFetchingUserLectures} = useGetSignupLecturesQuery(sessionKey);
    const {data: userOpinions, isFetching: isFetchingUserOpinions} = useGetUserOpinionsQuery(sessionKey);

    return (
        <div className="page-content profile-page">
            <div className="lecture-box">
                <div className="lecture-box-bar">
                    <div className="lecture-box-title">Your lectures</div>
                </div>

                {isFetchingUserLectures ? (
                    <Loading/>
                ) : (
                    (userLectures !== undefined) ? (
                        userLectures.map(lecture => <LecturesListElement key={lecture.id} id={lecture.id}/>)
                    ) : (
                        <FetchError>
                            <span>Cannot fetch lectures</span>
                        </FetchError>
                    )
                )}

            </div>

            <div className="lecture-box lecture-opinion-box">
                <div className="lecture-box-bar">
                    <div className="lecture-box-title">Your opinions</div>
                </div>

                {isFetchingUserOpinions ? (
                    <Loading/>
                ) : (
                    (userOpinions !== undefined) ? (
                        <div className="opinions-list">
                            {userOpinions.map(opinion => <OpinionComponent id={opinion.id}>
                                <OpinionListControls id={opinion.id} linkTo={`/lectures/${opinion.lectureId}`}/>
                            </OpinionComponent>)}
                        </div>
                    ) : (
                        <FetchError>
                            <span>Cannot fetch opinions</span>
                        </FetchError>
                    )
                )}
            </div>

            <div className="lecture-box lecture-opinion-box">
                <div className="lecture-box-bar">
                    <div className="lecture-box-title">Your offers</div>
                </div>

                <div className="lecture-box-content">
                    <UserOffers/>
                </div>
            </div>
        </div>
    );
}

export default ProfilePage;