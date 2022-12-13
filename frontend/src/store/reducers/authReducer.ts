import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import AuthState from "../models/authState";
import EState from "../models/common/state";
import APIError from "../models/common/apiError";

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
                state.isAdmin = action.payload.isAdmin;
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
    isAdmin: boolean
}

export type loginParams = {
    name: string,
    password: string
}

export const loginAsync = createAsyncThunk<loginReturns, loginParams, { rejectValue: APIError }>(
    "auth/loginAsync",
    async (credentials: loginParams, thunkAPI) => {
        try {
            // @TODO: Change API url
            const response = await fetch("/fakeApi/login.json", {
                method: "POST",
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
            const state = thunkAPI.getState() as AuthState;

            // @TODO: Change API url
            const response = await fetch("./fakeApi/logout.json", {
                method: "DELETE",
                body: JSON.stringify({ sessionKey: state.sessionKey})
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