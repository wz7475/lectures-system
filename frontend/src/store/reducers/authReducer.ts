import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import AuthState from "../models/authState";
import EState from "../models/common/state";
import APIError from "../models/common/apiError";
import {AppState} from "../store";

const initialState: AuthState = {
    state: EState.Idle,
    error: null,
    isAuthenticated: false,
    sessionKey: null,
    isAdmin: false
};

const authSlice = createSlice({
    name: "auth",
    initialState,
    reducers: {},
    extraReducers: builder =>
        builder
            .addCase(loginAsync.pending, (state) => {
                state.state = EState.Pending;
                state.error = null;
            })
            .addCase(loginAsync.fulfilled, (state, action) => {
                state.state = EState.Complete;
                state.error = null;
                state.isAuthenticated = true;
                state.sessionKey = action.payload.sessionKey;
                state.isAdmin = action.payload.admin;
            })
            .addCase(loginAsync.rejected, (state, action) => {
                state.state = EState.Failed;
                state.error = action.payload
            })
            .addCase(logoutAsync.pending, (state) => {
                state.state = EState.Pending;
                state.error = null;
            })
            .addCase(logoutAsync.fulfilled, (state) => {
                state.state = EState.Complete;
                state.error = null;
                state.isAuthenticated = false;
                state.sessionKey = null;
            })
            .addCase(logoutAsync.rejected, (state, action) => {
                state.state = EState.Failed;
                state.error = action.payload
            })
});

export type loginReturns = {
    sessionKey: string,
    admin: boolean
}

export type loginParams = {
    name: string,
    password: string
}

export const loginAsync = createAsyncThunk<loginReturns, loginParams, { rejectValue: APIError }>(
    "auth/loginAsync",
    async (credentials: loginParams, thunkAPI) => {
        try {
            const response = await fetch("/api/user/login", {
                method: "POST",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(credentials)
            });

            if (response.status !== 200) {
                return thunkAPI.rejectWithValue({
                    code: response.status,
                    message: await response.text()
                });
            }

            const json = await response.json();

            if (!json.sessionKey) {
                return thunkAPI.rejectWithValue({
                    code: -1,
                    message: "broken data"
                });
            }

            return json;
        } catch (error) {
            if (error instanceof Error) {
                return thunkAPI.rejectWithValue({
                    code: -1,
                    message: error.message,
                    data: {
                        stack: error.stack
                    }
                })
            }
        }
    }
);

export const logoutAsync = createAsyncThunk<void, void, { rejectValue: APIError }>(
    "auth/logoutAsync",
    async (arg, thunkAPI) => {
        try {
            const state = thunkAPI.getState() as { auth: { sessionKey: string }};

            const response = await fetch("./api/user/logout?sessionKey=" + state.auth.sessionKey, {
                method: "DELETE",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
            });

            if (response.status !== 200) {
                return thunkAPI.rejectWithValue({
                    code: response.status,
                    message: await response.text()
                })
            }
        } catch (error) {
            if (error instanceof Error) {
                return thunkAPI.rejectWithValue({
                    code: -1,
                    message: error.message,
                    data: {
                        stack: error.stack
                    }
                })
            }
        }
    }
)

export default authSlice.reducer;