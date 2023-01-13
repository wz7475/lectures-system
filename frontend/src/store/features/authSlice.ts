import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {AppState} from "../store";
import {Id} from "../services/api";

type AuthState = {
    sessionKey: string;
    admin: boolean;
    userId: Id;
}

const initialState: AuthState = {
    sessionKey: "",
    admin: false,
    userId: 0
}

const slice = createSlice({
    name: "auth",
    initialState,
    reducers: {
        setCredentials: (state, {payload: {sessionKey, admin, userId}}: PayloadAction<AuthState>) => {
            state.sessionKey = sessionKey;
            state.admin = admin;
            state.userId = userId;
        },
        removeCredentials: (state) => {
            state.sessionKey = "";
            state.admin = false;
            state.userId = 0;
        }
    }
});

export const {setCredentials, removeCredentials} = slice.actions;
export default slice.reducer;
export const selectSessionKey = (state: AppState) => state.auth.sessionKey;
export const selectIsAdmin = (state: AppState) => state.auth.admin;

export const selectUserId = (state: AppState) => state.auth.userId;