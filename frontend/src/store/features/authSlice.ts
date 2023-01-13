import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {AppState} from "../store";
import {Id} from "../services/api";

type AuthState = {
    sessionKey: string;
    admin: boolean;
    id: Id;
}

const initialState: AuthState = {
    sessionKey: "",
    admin: false,
    id: 0
}

const slice = createSlice({
    name: "auth",
    initialState,
    reducers: {
        setCredentials: (state, {payload: {sessionKey, admin, id}}: PayloadAction<AuthState>) => {
            state.sessionKey = sessionKey;
            state.admin = admin;
            state.id = id;
        },
        removeCredentials: (state) => {
            state.sessionKey = "";
            state.admin = false;
            state.id = 0;
        }
    }
});

export const {setCredentials, removeCredentials} = slice.actions;
export default slice.reducer;
export const selectSessionKey = (state: AppState) => state.auth.sessionKey;
export const selectIsAdmin = (state: AppState) => state.auth.admin;

export const selectUserId = (state: AppState) => state.auth.id;