import React from "react";
import {useDispatch, useSelector} from "react-redux";
import {AppDispatch, AppState} from "./store/store";
import LoginPage from "./containers/LoginPage";
import {logoutAsync} from "./store/reducers/authReducer";
import EState from "./store/models/common/state";

const App: React.FC = () => {
    const dispatch: AppDispatch = useDispatch();
    const isAuthenticated = useSelector<AppState, boolean>((state) => state.auth.isAuthenticated);
    const authState = useSelector<AppState, EState>((state) => state.auth.state);
    const sessionKey = useSelector<AppState, string | null>((state) => state.auth.sessionKey);

    const handleLogout = () => {
        dispatch(logoutAsync());
    }

    if (!isAuthenticated) {
        return <LoginPage/>;
    }

    if (authState === EState.Pending) {
        return (
            <div>Logout...</div>
        );
    }

    return (
        <>
            <div>Session key: {sessionKey}</div>
            <button onClick={handleLogout}>Logout</button>
        </>
    );
}

export default App;