import {Id} from "../store/services/api";

export interface Offer {
    id: Id;
    sellerId: number;
    offeredLectureId: Id;
    returnedLectureId: Id;
}