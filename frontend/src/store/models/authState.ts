import EState from "./common/state";
// @ts-ignore
import APIError from "./common/apiError";

export default interface AuthState {
    state: EState;
    error: APIError | null | undefined;
    isAuthenticated: boolean;
    sessionKey: string | null;
}