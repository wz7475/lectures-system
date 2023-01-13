import React, {useState} from "react";
import {
    Id,
    useAddOfferMutation,
    useGetLecturesQuery,
    useGetOffersQuery,
    useGetSignupLecturesQuery
} from "../../store/services/api";
import {useSelector} from "react-redux";
import {selectSessionKey} from "../../store/features/authSlice";
import Loading from "../../components/Loading/Loading";
import FetchError from "../../components/FetchError/FetchError";
import OfferComponent from "./OfferComponent/OfferComponent";
import Lecture from "../../common/Lecture";
import Weekdays from "../../common/Weekdays";
import {Offer} from "../../common/Offer";

const OffersPage: React.FC = () => {
    const sessionKey = useSelector(selectSessionKey);
    const [offeredLecture, setOfferedLecture] = useState<Id>(0);
    const [wantedLecture, setWantedLecture] = useState<Id>(0);
    const [addOffer, {isLoading: isLoadingAddOffer}] = useAddOfferMutation();
    const {data: lectures, isFetching: isFetchingLectures} = useGetLecturesQuery();
    const {data: userLectures, isFetching: isFetchingUserLectures} = useGetSignupLecturesQuery(sessionKey);
    const {data: offers, isFetching: isFetchingOffers} = useGetOffersQuery(sessionKey);

    const formatTime = (hours: number, minutes: number): string => {
        let formattedMinutes = minutes > 9 ? minutes.toString() : `0${minutes}`;
        return `${hours}:${formattedMinutes}`;
    }

    const formatLecture = (lecture: Lecture): string => {
        return `#${lecture.id} | ${lecture.name} - ${Weekdays.shortNameFromNumber(lecture.dayOfWeek)} ${formatTime(lecture.beginTimeHour, lecture.beginTimeMinute)}`
    }

    const handleAdd = async () => {
        console.log(offeredLecture, wantedLecture);

        await addOffer({
            session: sessionKey,
            data: {
                id: 0,
                sellerId: 1,
                offeredLectureId: parseInt(offeredLecture.toString()),
                returnedLectureId: parseInt(wantedLecture.toString())
            }
        });
    }

    if (isFetchingOffers || isFetchingLectures || isFetchingUserLectures) {
        return (
            <div className="page-content offers-page">
                <Loading/>
            </div>
        );
    }

    if (offers === undefined || userLectures === undefined || lectures === undefined) {
        return <FetchError>
            <span>Error when fetching information from server</span>
        </FetchError>
    }

    // const matchingOffers = offers.filter(offer => {
    //     userLectures.some(lecture => lecture.id === offer.returnedLectureId)
    // });
    const matchingOffers: Offer[] = [
        {
            id: 1,
            sellerId: 1,
            offeredLectureId: 36,
            returnedLectureId: 44
        }
    ]

    return (
        <div className="page-content offers-page">
            <div className="page-title">Offers</div>

            <div className="lecture-box">
                <div className="lecture-box-title">
                    Make new offer
                </div>

                <div className="lecture-box-content">
                    <div className="input-container">
                        <div className="input-label">
                            Lecture to offer
                        </div>
                        <select onChange={(event) => setOfferedLecture(event.target.value)}>
                            {userLectures.map(lecture => <option key={lecture.id}
                                                                 value={lecture.id}>
                                {formatLecture(lecture)}
                            </option>)}
                        </select>
                    </div>

                    <div className="input-container">
                        <div className="input-label">
                            Wanted lecture
                        </div>
                        <select onChange={(event) => setWantedLecture(event.target.value)}>
                            {lectures.map(lecture => <option key={lecture.id}
                                                             value={lecture.id}>
                                {formatLecture(lecture)}
                            </option>)}
                        </select>
                    </div>

                    <button className="blue" onClick={handleAdd} disabled={isLoadingAddOffer}>
                        { isLoadingAddOffer ? (
                            <Loading/>
                        ) : (
                            <>Add</>
                        )}
                    </button>
                </div>
            </div>

            <div className="lecture-box">
                <div className="lecture-box-title">
                    Offers for You
                </div>

                <div className="lecture-box-content">
                    {(isFetchingOffers || isFetchingUserLectures) ? (
                        <Loading/>
                    ) : (
                        (matchingOffers && matchingOffers.length !== 0) ? (
                            matchingOffers.map(o => <OfferComponent offer={o}/>)
                        ) : (
                            <div>We don't have any offers for you</div>
                        )
                    )}
                </div>
            </div>
        </div>
    )
}

export default OffersPage;