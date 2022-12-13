import {Action, configureStore} from "@reduxjs/toolkit";
import { ThunkAction } from "redux-thunk";
import rootReducer from "./reducers/rootReducer";

const store = configureStore({
   reducer: rootReducer,
});

export type AppState = ReturnType<typeof store.getState>;
export type AppThunk = ThunkAction<void, AppState, null, Action<string>>;
export type AppDispatch = typeof store.dispatch;
export default store;