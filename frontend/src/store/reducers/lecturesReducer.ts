import LecturesState from "../models/lecturesState";
import EState from "../models/common/state";
import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import APIError from "../models/common/apiError";
import Lecture from "../models/lectures/Lecture";

const initialState: LecturesState = {
    state: EState.Idle,
    error: null,
    lectures: []
};

const lecturesSlice = createSlice({
    name: "lectures",
    initialState,
    reducers: {},
    extraReducers: builder =>
        builder
            .addCase(fetchLectures.pending, (state) => {
                state.state = EState.Pending;
                state.error = null;
            })
            .addCase(fetchLectures.fulfilled, (state, action) => {
                state.state = EState.Complete;
                state.error = null;
                state.lectures = action.payload;
            })
            .addCase(fetchLectures.rejected, (state, action) => {
                state.state = EState.Failed;
                state.error = action.payload;
            })
            .addCase(signUpLecture.pending, (state) => {
                state.state = EState.Pending;
                state.error = null;
            })
            .addCase(signUpLecture.fulfilled, (state) => {
                state.state = EState.Complete;
            })
            .addCase(signUpLecture.rejected, (state, action) => {
                state.state = EState.Failed;
                state.error = action.payload;
            })
            .addCase(addLecture.pending, (state) => {
                state.state = EState.Pending;
                state.error = null;
            })
            .addCase(addLecture.fulfilled, (state, action) => {
                state.state = EState.Complete;
                state.error = null;
                state.lectures.push(action.payload);
            })
            .addCase(addLecture.rejected, (state, action) => {
                state.state = EState.Failed;
                state.error = action.payload;
            })
});

export const fetchLectures = createAsyncThunk<Lecture[], void, { rejectValue: APIError }>(
    "lectures/fetchLectures",
    async (_, thunkAPI) => {
        try {
            const response = await fetch("/api/lectures");

            if (response.status !== 200) {
                return thunkAPI.rejectWithValue({
                    code: response.status,
                    message: await response.text()
                });
            }

            return await response.json();
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

export type singUpLectureParams = {
    id: string;
};

export const signUpLecture = createAsyncThunk<{ success: boolean }, singUpLectureParams, { rejectValue: APIError }>(
    "lectures/signUpLecture",
    async ({id}, thunkAPI) => {
        try {
            // @TODO: Change API url
            const response = await fetch("/fakeApi/signUp.json");

            if (response.status !== 200) {
                return thunkAPI.rejectWithValue({
                    code: response.status,
                    message: await response.text()
                });
            }

            return await response.json();
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

export const addLecture = createAsyncThunk<Lecture, Lecture, { rejectValue: APIError }>(
    "lectures/addLecture",
    async (lecture, thunkAPI) => {
        const state = thunkAPI.getState() as { auth: { sessionKey: string }};

        try {
            // @TODO: Change API url
            const response = await fetch("/api/lectures?sessionKey=" + state.auth.sessionKey, {
                method: "POST",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(lecture)
            });

            if (response.status !== 200) {
                return thunkAPI.rejectWithValue({
                    code: response.status,
                    message: await response.text()
                });
            }

            return await response.json();
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

        return lecture;
    }
)

export default lecturesSlice.reducer;