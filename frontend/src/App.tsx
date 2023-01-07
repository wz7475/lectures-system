import React from "react";
import LoginPage from "./containers/LoginPage/LoginPage";
import "./App.css";
import SidebarContainer from "./containers/SidebarContainer/SidebarContainer";
import ContentContainer from "./containers/ContentContainer/ContentContainer";
import {useLoginMutation} from "./store/services/api";

const App: React.FC = () => {
    const [, {data, isLoading, isSuccess}] = useLoginMutation();

    if (!isSuccess) {
        return <LoginPage/>;
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