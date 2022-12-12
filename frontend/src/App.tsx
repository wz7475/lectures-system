import React from "react";
import {useSelector} from "react-redux";
import {AppState} from "./store/store";
import LoginPage from "./containers/LoginPage/LoginPage";
import EState from "./store/models/common/state";
import "./App.css";
import SidebarContainer from "./containers/SidebarContainer/SidebarContainer";
import ContentContainer from "./containers/ContentContainer/ContentContainer";

const App: React.FC = () => {
    const isAuthenticated = useSelector<AppState, boolean>((state) => state.auth.isAuthenticated);
    const authState = useSelector<AppState, EState>((state) => state.auth.state);

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
            <SidebarContainer/>
            <ContentContainer/>
        </>
    );
}

export default App;