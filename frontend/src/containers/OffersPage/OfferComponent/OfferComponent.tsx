import React from "react";
import {Offer} from "../../../common/Offer";
import LectureElement from "../../../components/LectureElement/LectureElement";
import Loading from "../../../components/Loading/Loading";
import {useAcceptOfferMutation} from "../../../store/services/api";
import {useSelector} from "react-redux";
import {selectSessionKey, selectUserId} from "../../../store/features/authSlice";

interface IOfferComponent {
    offer: Offer
}

const OfferComponent: React.FC<IOfferComponent> = (props) => {
    const userId = useSelector(selectUserId);
    const sessionKey = useSelector(selectSessionKey);
    const [acceptOffer, {isLoading}] = useAcceptOfferMutation();

    const handleAccept = async () => {
        await acceptOffer({
            session: sessionKey,
            data: {
                userId: userId,
                offerId: props.offer.id
            }
        })
    }

    if (isLoading) {
        return (
            <div className="offer-element">
                <Loading/>
            </div>
        );
    }

    return (
        <div className="offer-element">
            <LectureElement id={props.offer.offeredLectureId} disableLink={true}>
                <button onClick={handleAccept} className="blue">Accept</button>
            </LectureElement>
        </div>
    )
}

export default OfferComponent;