import {combineReducers} from "redux";
import authReducer from "./authReducer";
import lecturesReducer from "./lecturesReducer";

const rootReducer = combineReducers({
    auth: authReducer,
    lectures: lecturesReducer
});

export default rootReducer;