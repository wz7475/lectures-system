import React from "react";
import {useGetOffersQuery} from "../../../store/services/api";
import Loading from "../../../components/Loading/Loading";
import {useSelector} from "react-redux";
import {selectSessionKey, selectUserId} from "../../../store/features/authSlice";
import OfferUserComponent from "../OfferUserComponent/OfferUserComponent";
import FetchError from "../../../components/FetchError/FetchError";

const UserOffers: React.FC = () => {
    const userId = useSelector(selectUserId);
    const sessionKey = useSelector(selectSessionKey);
    const {data: offers, isFetching: isFetchingOffers} = useGetOffersQuery(sessionKey);

    if (offers === undefined) {
        return <FetchError>
            <span>Error when fetching offers</span>
        </FetchError>
    }

    const userOffers = offers.filter(offer => offer.sellerId === userId);

    return (
        <>
            {(isFetchingOffers) ? (
                <Loading/>
            ) : (
                (userOffers && userOffers.length !== 0) ? (
                    userOffers.map(o => <OfferUserComponent offer={o}/>)
                ) : (
                    <div>You don't have active offers</div>
                )
            )}
        </>
    )
}

export default UserOffers;