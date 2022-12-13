import EState from "./common/state";
import APIError from "./common/apiError";

export default interface AuthState {
    state: EState;
    error: APIError | null | undefined;
    isAuthenticated: boolean;
    sessionKey: string | null;
    isAdmin: boolean;
}