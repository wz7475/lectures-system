import React from "react";
import LoginPage from "./containers/LoginPage/LoginPage";
import "./App.css";
import SidebarContainer from "./containers/SidebarContainer/SidebarContainer";
import ContentContainer from "./containers/ContentContainer/ContentContainer";
import {useLogoutMutation} from "./store/services/api";
import {Route, Routes} from "react-router";
import RegisterPage from "./containers/RegisterPage/RegisterPage";
import {selectSessionKey} from "./store/features/authSlice";
import {useSelector} from "react-redux";
import {LoadingOverlay} from "./components/LoadingOverlay/LoadingOverlay";

const App: React.FC = () => {
    const [, { isLoading }] = useLogoutMutation({
        fixedCacheKey: "shared-logout"
    });
    const sessionKey = useSelector(selectSessionKey);

    if (sessionKey === "") {
        return (
            <>
                {isLoading && <LoadingOverlay/>}
                <Routes>
                    <Route path="/" element={<LoginPage/>}/>
                    <Route path="/register" element={<RegisterPage/>}/>
                </Routes>
            </>
        );
    }

    return (
        <>
            {isLoading && <LoadingOverlay/>}
            <SidebarContainer/>
            <ContentContainer/>
        </>
    );
}

export default App;