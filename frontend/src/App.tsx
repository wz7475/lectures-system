import React from "react";
import LoginPage from "./containers/LoginPage/LoginPage";
import "./App.css";
import SidebarContainer from "./containers/SidebarContainer/SidebarContainer";
import ContentContainer from "./containers/ContentContainer/ContentContainer";
import {useLoginMutation} from "./store/services/api";
import {Route, Routes} from "react-router";
import RegisterPage from "./containers/RegisterPage/RegisterPage";
import {selectSessionKey} from "./store/features/authSlice";
import {useSelector} from "react-redux";

const App: React.FC = () => {
    const [, {isLoading}] = useLoginMutation();
    const sessionKey = useSelector(selectSessionKey);

    if (sessionKey === "") {
        return (
            <Routes>
                <Route path="/" element={<LoginPage/>}/>
                <Route path="/register" element={<RegisterPage/>}/>
            </Routes>
        );
    }

    if (isLoading) {
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