import EState from "./common/state";
import APIError from "./common/apiError";
import Lecture from "./Lecture";

export default interface LecturesState {
    state: EState;
    error: APIError | null | undefined;
    lectures: Lecture[];
}