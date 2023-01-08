import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {AppState} from "../store";

type AuthState = {
    sessionKey: string;
    admin: boolean;
}

const slice = createSlice({
    name: "auth",
    initialState: {
        sessionKey: "",
        admin: false
    },
    reducers: {
        setCredentials: (state, {payload: {sessionKey, admin}}: PayloadAction<AuthState>) => {
            state.sessionKey = sessionKey;
            state.admin = admin;
        },
        removeCredentials: (state) => {
            state.sessionKey = "";
            state.admin = false;
        }
    }
});

export const {setCredentials, removeCredentials} = slice.actions;
export default slice.reducer;
export const selectSessionKey = (state: AppState) => state.auth.sessionKey;
export const selectIsAdmin = (state: AppState) => state.auth.admin;