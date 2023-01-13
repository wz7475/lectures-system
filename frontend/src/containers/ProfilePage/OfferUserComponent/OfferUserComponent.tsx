import {useSelector} from "react-redux";
import {selectSessionKey} from "../../../store/features/authSlice";
import {useDeleteOfferMutation} from "../../../store/services/api";
import Loading from "../../../components/Loading/Loading";
import LectureElement from "../../../components/LectureElement/LectureElement";
import React from "react";
import {Offer} from "../../../common/Offer";

interface IOfferUserComponent {
    offer: Offer
}

const OfferUserComponent: React.FC<IOfferUserComponent> = (props) => {
    const sessionKey = useSelector(selectSessionKey);
    const [deleteOffer, {isLoading}] = useDeleteOfferMutation();

    const handleDelete = async () => {
        await deleteOffer({
            session: sessionKey,
            data: props.offer.id
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
                <button onClick={handleDelete} className="red">Delete offer</button>
            </LectureElement>
        </div>
    )
}

export default OfferUserComponent;