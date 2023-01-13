import {Id} from "../store/services/api";

export interface Offer {
    id: Id;
    sellerId: Id;
    offeredLectureId: Id;
    returnedLectureId: Id;
}