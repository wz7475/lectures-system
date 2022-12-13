import EState from "./common/state";
import APIError from "./common/apiError";
import Lecture from "./lectures/Lecture";

export default interface LecturesState {
    state: EState;
    error: APIError | null | undefined;
    lectures: Lecture[];
}